package it.polimi.se2018.view.gui;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCEndTurnMessage;
import it.polimi.se2018.controller.VCSetUpMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawRequestedMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawUnrequestedMessage;
import it.polimi.se2018.exceptions.GUIErrorException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.Cell;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.*;
import it.polimi.se2018.view.cli.ModelRepresentation;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for the javaFX controller, implements ViewInterface thus providing the methods called by VCGUIMessageCreator.
 * When VCGUIMessageCreator calls a method, the corresponding state is setted in GUIController and latch.await() is called,
 * putting the corresponding thread in waiting state. When the requested parameter is notified to VCGUIMessageCreator, latch.countdown() is called,
 * awaking the thread, thus allowing the next call from VCGUIMessageCreator to GUIcontroller.
 *
 * In order to provide the same instance of GUIController to every window (Game window, draftpool, roundtrack ecc), we used the method
 * loader.setController(this), therefore the fxcontroller is not specified in the corresponding fxml files, and that's the reson why many attributes are marked
 * as "not assigned" by sonarqube.
 *
 * GUIController doesn't extend AbstractView because every accept() method of MVAbstractMessage needs to call Platform.runlater(),
 * and this leads to another implementation of the accept() method.
 *
 * @author Pietro Ghiglio
 * @author Andrea Galuzzi
 */
public class GUIcontroller implements ViewInterface, Observer<MVAbstractMessage> {

    @FXML
    private RadioButton choice1;
    @FXML
    private RadioButton choice2;
    @FXML
    private RadioButton choice3;
    @FXML
    private RadioButton choice4;

    @FXML
    private GridPane myWindow;
    @FXML
    private GridPane draftpoolPane;
    @FXML
    private GridPane grid1;
    @FXML
    private GridPane grid2;
    @FXML
    private GridPane grid3;
    @FXML
    private GridPane grid4;
    @FXML
    private GridPane mainWindow;
    @FXML
    private GridPane mainWindow1;
    @FXML
    private GridPane mainWindow2;
    @FXML
    private ImageView TCV0;
    @FXML
    private ImageView TCV1;
    @FXML
    private ImageView TCV2;
    @FXML
    private Label displayMessage;
    @FXML
    private Button value1;
    @FXML
    private Button value2;
    @FXML
    private Button value3;
    @FXML
    private Button value4;
    @FXML
    private Button value5;
    @FXML
    private Button value6;
    @FXML
    private ImageView image;
    @FXML
    private Button yes;
    @FXML
    private Button no;









    private ModelRepresentation modelRepresentation;
    private Map<Integer, GridPane> playerPanes; //map that associates every playerID with the corresponding gridpane
    private Map<String, Integer> fromTCnameToID;
    private List<RawInputObserver> rawObservers;
    private List<Observer<VCAbstractMessage>> observers;

    private String playerName;
    private State state;
    private Latch latch;
    private int playerID;
    private boolean isShowingBoards;
    private boolean isShowingDP;
    private static final Logger LOGGER = Logger.getLogger(GUIcontroller.class.getName());

    private static final String SELECT_DP = "Select a die from the draftpool.";
    private static final String SELECT_CELL = "Select a cell.";
    private static final String NOT_TURN = "Not your turn";

    /**
     * Method that initiates the attributes, called once (when the loading screen is showed)
     * @param modelRep the ModelRepresentation instantiated while connecting
     */
    public void init(ModelRepresentation modelRep) {
        isShowingBoards = false;
        isShowingDP = false;
        latch = new Latch();
        rawObservers = new ArrayList<>();
        state = State.NOT_YOUR_TURN;
        modelRepresentation = modelRep;
        observers = new ArrayList<>();
        setTCname();
    }

    private void setTCname(){
        fromTCnameToID = new HashMap<>();
        fromTCnameToID.put("GrozingPliers", 1);
        fromTCnameToID.put("EglomiseBrush", 2);
        fromTCnameToID.put("CopperFoilBurnisher", 3);
        fromTCnameToID.put("Lathekin", 4);
        fromTCnameToID.put("LensCutter", 5);
        fromTCnameToID.put("FluxBrush",6);
        fromTCnameToID.put("GlazingHammer", 7);
        fromTCnameToID.put("RunningPliers", 8);
        fromTCnameToID.put("CorkBackedStraightedge", 9);
        fromTCnameToID.put("GrindingStone", 10);
        fromTCnameToID.put("FluxRemover", 11);
        fromTCnameToID.put("TapWheel", 12);
    }

    public void update(MVAbstractMessage message) {
        message.accept(this);
    }

    /**
     * Method called when a player confirms the chosen board at the beginning of the game.
     * Notifies the appropriate VCSetUpMessage
     * @param e the event corresponding to the pression of the Confirm button, the reference is used to close the choice window
     */
    @FXML
    public void handleChoice(Event e) {
        ToggleGroup choice = new ToggleGroup();
        Button b = (Button) e.getSource();
        choice1.setToggleGroup(choice);
        choice2.setToggleGroup(choice);
        choice3.setToggleGroup(choice);
        choice4.setToggleGroup(choice);
        choice1.setUserData("1");
        choice2.setUserData("2");
        choice3.setUserData("3");
        choice4.setUserData("4");
        Toggle selected = choice.getSelectedToggle();
        Integer i = Integer.valueOf(selected.getUserData().toString());
        Map<Integer, WPC> extractedWPCs = modelRepresentation.getSelected();
        WPC extracted = extractedWPCs.get(i);

        Stage s = (Stage) b.getScene().getWindow();
        s.close();
        notifyController(new VCSetUpMessage(playerID, extracted.getId()));

        loadMainWindow();

    }

    private void loadMainWindow(){
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("/fxml/gameWindow.fxml"));
        try {
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Game");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
            Stage st = (Stage)image.getScene().getWindow();
            st.close();
            stage.setOnCloseRequest(confirmCloseEventHandler );


        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/exitConfirmation.fxml"));
            Scene window = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Exit");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            event.consume();
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

    };


    public void showToolCards() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("/fxml/toolCards.fxml"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("ToolCards");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
        stage.setResizable(false);
        List<String> tcInUse = modelRepresentation.getToolCards();
        Image tci0 = new Image(getClass().getResourceAsStream("/ToolCards/" + tcInUse.get(0) + ".jpg"));
        Image tci1 = new Image(getClass().getResourceAsStream("/ToolCards/" + tcInUse.get(1) + ".jpg"));
        Image tci2 = new Image(getClass().getResourceAsStream("/ToolCards/" + tcInUse.get(2) + ".jpg"));

        TCV0.setImage(tci0);
        TCV1.setImage(tci1);
        TCV2.setImage(tci2);
        stage.show();

    }

    public void endTurn(){
        if(state == State.WAIT_MOVE)
            notify(new VCEndTurnMessage(playerID));
    }

    public void showDraftPool() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/fxml/draftPool.fxml"));
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("DraftPool");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            isShowingDP = true;
            stage.setOnCloseRequest(event -> isShowingDP = false);
            stage.show();
            fillerDraftPool();


        } catch (IOException e) {
            String m = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, m);
        }
    }

    private void fillerDraftPool(){
        List<Die> draftPool = modelRepresentation.getDraftPool();
        List<Node> children = draftpoolPane.getChildren();


        int i = 0;
        for(Node n: children) {
            try {
                Button b = (Button) n;
                b.setBackground(null);
                if (i < draftPool.size()) {
                    Die d = draftPool.get(i);
                    int val = d.getDieValue();
                    Colour c = d.getDieColour();
                    String path = "/dice/" + c.toString().toLowerCase() + "/" + val + ".jpg";

                    BackgroundSize bgs = new BackgroundSize(100, 100, true, true, true, false);
                    BackgroundImage backgroundImage = new BackgroundImage(
                            new Image(getClass().getResource(path).toExternalForm()),
                            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, bgs);
                    Background background = new Background(backgroundImage);
                    b.setBackground(background);
                } else {
                    b.setBackground(null);

                }
                i++;
            }
            catch(RuntimeException e){
                //exception thrown when trying to cast a Node to Button, or setting background to null
            }
        }

    }

    public void showPuCards() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("/fxml/puCards.fxml"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("PuCards");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
        stage.setResizable(false);
        stage.show();


    }

    /**
     * Method called when the button Dice Move is pressed.
     * Notifies the "dicemove" string to VCGUIMessageCreator, initiating the appropriate parameters-getting sequence
     */
    public void diceMove() {
        if(state == State.WAIT_MOVE)
        rawNotify(new RawUnrequestedMessage("dicemove"));

    }

    /**
     * Method called when a player selects a toolcard, notifies the appropriate "toolcard ID" string
     */
    @FXML
    public void toolCard(Event event) {
        List<String> tcInUse = modelRepresentation.getToolCards();
        String tcName;
        Button b = (Button)event.getSource();
        String label = b.getId();
        switch(label){
            case("TC0"): tcName = tcInUse.get(0); break;
            case("TC1"): tcName = tcInUse.get(1); break;
            case("TC2"): tcName = tcInUse.get(2); break;
            default: tcName = "";
        }
        int id = fromTCnameToID.get(tcName);
        Stage stage = (Stage) b.getScene().getWindow();
        stage.close();

        if(state == State.WAIT_MOVE)
            rawNotify(new RawUnrequestedMessage("toolcard " + id));


    }

    public int getPlayerID() {
        return playerID;
    }

    /**
     * Method called when a button corresponding to a die is pressed.
     * Dice are identified by the button's label.
     * @param e the button-pressed event
     */
    @FXML
    public void selectCell(Event e) {
        Button b = (Button) e.getSource();
        String s = b.getId();
        switch (s) {
            case ("Button00"):
                cellPressed(0, 0);
                break;
            case ("Button01"):
                cellPressed(0, 1);
                break;
            case ("Button02"):
                cellPressed(0, 2);
                break;
            case ("Button03"):
                cellPressed(0, 3);
                break;
            case ("Button04"):
                cellPressed(0, 4);
                break;
            case ("Button10"):
                cellPressed(1, 0);
                break;
            case ("Button11"):
                cellPressed(1, 1);
                break;
            case ("Button12"):
                cellPressed(1, 2);
                break;
            case ("Button13"):
                cellPressed(1, 3);
                break;
            case ("Button14"):
                cellPressed(1, 4);
                break;
            case ("Button20"):
                cellPressed(2, 0);
                break;
            case ("Button21"):
                cellPressed(2, 1);
                break;
            case ("Button22"):
                cellPressed(2, 2);
                break;
            case ("Button23"):
                cellPressed(2, 3);
                break;
            case ("Button24"):
                cellPressed(2, 4);
                break;
            case ("Button30"):
                cellPressed(3, 0);
                break;
            case ("Button31"):
                cellPressed(3, 1);
                break;
            case ("Button32"):
                cellPressed(3, 2);
                break;
            case ("Button33"):
                cellPressed(3, 3);
                break;
            case ("Button34"):
                cellPressed(3, 4);
                break;


            case ("DP0"):
                dpSelected(0);
                break;
            case ("DP1"):
                dpSelected(1);
                break;
            case ("DP2"):
                dpSelected(2);
                break;
            case ("DP3"):
                dpSelected(3);
                break;
            case ("DP4"):
                dpSelected(4);
                break;
            case ("DP5"):
                dpSelected(5);
                break;
            case ("DP6"):
                dpSelected(6);
                break;
            case ("DP7"):
                dpSelected(7);
                break;
            case ("DP8"):
                dpSelected(8);
                break;

            default:
                displayMessage("Error");
        }


    }

    /**
     * Method that, according to the state, either notifies the corresponding draftpool index or displays an error message
     * @param id draftpool index
     */
    private void dpSelected(int id) {
        switch (state) {
            case NOT_YOUR_TURN:
                displayMessage(NOT_TURN);
                break;
            case COORDINATES_REQUEST:
                displayMessage(SELECT_CELL);
                break;
            case DP_INDEX_REQUEST:
                rawNotify(new RawRequestedMessage(id));
                latch.countDown();
                break;
            case RT_POSITION_REQUEST:
                displayMessage("Select draftpool die.");
                break;
            default:
                displayMessage("Error.");
                break;
        }
    }

    /**
     * Method that, according to the state, either notifies the cell coordinates or displays an error message
     * @param row cell coordinate
     * @param col cell coordinate
     */
    private void cellPressed(int row, int col) {
        switch (state) {
            case NOT_YOUR_TURN:
                displayMessage(NOT_TURN);
                break;
            case COORDINATES_REQUEST:
                rawNotify(new RawRequestedMessage(row));
                rawNotify(new RawRequestedMessage(col));
                latch.countDown();
                break;
            case VALID_REQUEST:
                rawNotify(new RawRequestedMessage(row));
                rawNotify(new RawRequestedMessage(col));
                reEnableBoard();
                latch.countDown();
                break;
            case DP_INDEX_REQUEST:
                displayMessage(SELECT_DP);
                break;
            case RT_POSITION_REQUEST:
                displayMessage("Insert coordinates");
                break;
            default:
                displayMessage("Error.");
                break;
        }
    }

    /**
     * Method that sets the COORDINATE_REQUEST state and puts the parameter-getting thread in wait
     */
    public void getCoordinates(String m) {
        setState(State.COORDINATES_REQUEST);
        displayMessage(SELECT_CELL);
        latch.reset();
        latch.await();
    }


    public void getCoordinates2() {
        //TODO: richiede coordinate, mostra richiesta per eventuale altro inserimento, richiede ancora coordinate
        //sets GET_COORD_2 state, in switch-case displayWindow(), onAction getCoordinates(). Mettere latch(2) ??
        int column = 0;
        rawNotify(new RawRequestedMessage(column));
    }

    /**
     * Method that, given a list of valid coordinates, activates only the appropriate buttons
     * @param validCoordinates the list
     */
    public void getValidCoordinates(List<int[]> validCoordinates) {
        setState(State.VALID_REQUEST);
        if (validCoordinates.isEmpty()) {
            displayMessage("Die not placeable.");
        } else {
            disableBoard(validCoordinates);
        }
        latch.reset();
        latch.await();
    }

    /**
     * Method that, given a list of valid coordinates, disables the non valid buttons
     * @param validCoordinates the list of valid coordinates
     */
    private void disableBoard(List<int[]> validCoordinates){
        for(int row = 0; row < WPC.NUMROW; row++){
            for(int col = 0; col < WPC.NUMCOL; col++){
                int[] temp = new int[2];
                temp[0] = row;
                temp[1] = col;
                if(!isValid(temp, validCoordinates)){
                    //if the button is not valid, disables the button
                    try{
                        Button b = (Button)getCellByCoordinates(row, col);
                        b.setDisable(true);
                    }
                    catch(GUIErrorException e){
                        LOGGER.log(Level.SEVERE, "Error while retrieving button from player board.");
                    }
                }
            }
        }
    }

    private void reEnableBoard(){
        for(int row = 0; row < WPC.NUMROW; row++){
            for(int col = 0; col < WPC.NUMCOL; col++){
                try{
                    Button b = (Button) getCellByCoordinates(row, col);
                    b.setDisable(false);
                }
                catch(GUIErrorException e){
                    LOGGER.log(Level.SEVERE, "Error while re-enabling buttons.");
                }
            }
        }
    }

    private boolean isValid(int[] couple, List<int[]> validList){
        for(int[] temp: validList){
            if(temp[0] == couple[0] && temp[1]==couple[1])return true;
        }
        return false;
    }

    /**
     * Sets the INCREMENT-REQUESTED state
     */
    public void getIncrement(){
        state = State.INCREMENT_REQUEST;
        Platform.runLater(this::openIncrement);
        latch.reset();
        latch.await();
    }

    private void openIncrement(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/fxml/increment.fxml"));
            Scene window = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Increment");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();}
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @FXML
    public void increment(Event event){
        Button b = (Button) event.getSource();
        String label = b.getId();
        if(label.equals("increment")){
            rawNotify(new RawRequestedMessage(1));
            latch.countDown();
        }
        else{
            rawNotify(new RawRequestedMessage(-1));
            latch.countDown();
        }
    }

    /**
     * Sets the DP_INDEX_REQUEST state
     */
    public void getDraftPoolIndex() {
        setState(State.DP_INDEX_REQUEST);
        displayMessage(SELECT_DP);
        latch.reset();
        latch.await();
    }

    /**
     * Sets the RT_POS_REQUEST
     */
    public void getRoundTrackPosition(String s) {

    }

    /**
     * Sets the NEW_DIE_VALUE state
     */
    public void newDieValue() {
            state = State.NEW_VALUE_REQUEST;
            Platform.runLater(this::openNewValue);
            latch.reset();
            latch.await();


    }

    private void openNewValue(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/fxml/newValue.fxml"));
            Scene window = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("NewDieValue");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
    @FXML
    public void chosenValue(ActionEvent event){
        Button button = (Button) event.getSource();
        String s = button.getText();
        int value = Integer.parseInt(s);
        rawNotify(new RawRequestedMessage(value));
        latch.countDown();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

    }

    public void displayMessage(String message) {
        Platform.runLater(() -> displayMessage.setText(message));
    }

    public void showRoundTrack() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/fxml/roundTrack.fxml"));
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("RoundTrack");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            String s = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, s);
        }

    }


    public void showMyBoard() {
        //empty method since the window is always shown
    }

    public void showBoards() {
        if(!isShowingBoards) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setController(this);
                loader.setLocation(getClass().getResource("/fxml/windows.fxml"));
                Scene window = new Scene(loader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setScene(window);
                stage.setTitle("Windows");
                stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
                stage.setResizable(false);
                stage.setOnCloseRequest(event -> isShowingBoards = false );
                stage.show();
                isShowingBoards = true;
                updateBoards();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
        else{
            updateBoards();
        }
    }

    private void updateBoards(){
        setGridPanes(modelRepresentation.getNumPlayers());
        Map<Integer, WPC> wpcs = modelRepresentation.getWpcs();
        for(Map.Entry<Integer, WPC> entry: wpcs.entrySet()){
            WPC wpc = entry.getValue();
            int i = entry.getKey();
            if(i != playerID){
                fillerWPC(wpc, playerPanes.get(i));
            }
        }
    }

    public void notifyController(VCAbstractMessage message) {
        notify(message);
    }

    public void rawRegister(RawInputObserver observer) {
        rawObservers.add(observer);

    }

    public void rawNotify(RawInputMessage message) {
        for (RawInputObserver ob : rawObservers) {
            ob.rawUpdate(message);
        }
    }

    /**
     * Method that updates the ModelRepresentation and the player's board
     * @param message the game message containing the data
     */
    private void updateMR(MVGameMessage message) {
        modelRepresentation.setRoundTrack(message.getRoundTrack());
        modelRepresentation.setDraftPool(message.getDraftPool());
        modelRepresentation.setWpcs(message.getWpcs());
        modelRepresentation.setDiceBag(message.getDiceBag());
        modelRepresentation.setCurrPlayer(message.getCurrPlayer());

        if(isShowingDP) Platform.runLater(this::fillerDraftPool);
        Platform.runLater(this::fillerMainBoard);
    }

    private void fillerMainBoard(){
        WPC wpc = modelRepresentation.getWpc(playerID);
        List<Node> children = myWindow.getChildren();
        for(Node node: children){
                if(node instanceof Button){
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                Cell cell = wpc.getCell(row, col);
                setBackGround(node, cell);
            }
        }
    }

    private void setBackGround(Node node, Cell cell){
        Button b = (Button)node;
        String path = "";

        if (cell.isEmpty()) {
            if (cell.getColourR() != null) {
                path = "/dice/" + cell.getColourR().toString().toLowerCase() + "/0.jpg";
            }

            else if (cell.getValueR() != null) {
                path = "/dice/grey/" + cell.getValueR().toString().toLowerCase() + ".jpg";
            }
        }

        else {
            Die d = cell.getDie();
            int val = d.getDieValue();
            Colour c = d.getDieColour();
            path = "/dice/" + c.toString().toLowerCase() + "/" + val+ ".jpg";
        }

        if(!path.equals("")) {
            BackgroundSize bgs = new BackgroundSize(100,100,true,true,true,false);
            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image(getClass().getResource(path).toExternalForm()),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, bgs);
            Background background = new Background(backgroundImage);
            b.setBackground(background);
        }



    }

    /**
     * Method used to get the button corresponding to the die in position (row, col) in the player's board
     * @param row coordinates
     * @param col coordinates
     * @return the button
     */
    private Node getCellByCoordinates(int row, int col) throws GUIErrorException {
        ObservableList<Node> children = myWindow.getChildren();
        for (Node node : children) {
            int buttonRow = GridPane.getRowIndex(node);
            int buttonCol = GridPane.getColumnIndex(node);
            if (buttonRow == row && buttonCol == col) {
                return node;
            }
        }
        throw new GUIErrorException();
    }

    /**
     * Setter for the player name, called after the connection
     * @param pn the playername
     */
    public void setPlayerName(String pn) {
        playerName = pn;
    }

    /**
     * Visit method for MVGameMessage, displays the message, updates the ModelRepresentation
     * and show the other's players boards if the move was succesfull
     * @param message the message
     */
    public void visit(MVGameMessage message) {
        updateMR(message);
        if (playerID == message.getPlayerID()) {
            setState(State.WAIT_MOVE);
            displayMessage(message.getMessage());
        }
        else if(playerID != message.getPlayerID() && message.getMessage().equalsIgnoreCase("Success.")) {
            showBoards();
            setState(State.NOT_YOUR_TURN);
        }
    }

    /**
     * Loads the choose-wpc window
     * @param message the MVSetUpMessage containing the extracted wpcs
     */
    public void visit(MVSetUpMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            modelRepresentation.setSelected(message.getExtracted());
            modelRepresentation.setPrCard(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            modelRepresentation.setToolCards(message.getTcInUse());
            playerID = message.getPlayerID();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choice.fxml"));
                loader.setController(this);
                Scene w = new Scene(loader.load(), 600, 400);
                Stage stage = new Stage();
                stage.setScene(w);
                stage.setTitle("Choice");
                stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
                stage.setOnCloseRequest(event-> System.exit(0));
                stage.setResizable(false);
                for (int i = 1; i <= 4; i++){
                    switch (i) {
                        case 1:
                            fillerWPC(modelRepresentation.getSelected().get(1), grid1);
                            break;
                        case 2:
                            fillerWPC(modelRepresentation.getSelected().get(2), grid2);
                            break;
                        case 3:
                            fillerWPC(modelRepresentation.getSelected().get(3), grid3);
                            break;
                        case 4:
                            fillerWPC(modelRepresentation.getSelected().get(4), grid4);
                            break;
                        default:
                            LOGGER.log(Level.SEVERE, "Error while displaying choice.");
                    }

                    }
                stage.show();

            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    /**
     * Notifies who won
     * @param message the MVWinnerMessage
     */
    public void visit(MVWinnerMessage message) {
        if (message.getPlayerID() == playerID) {
            displayMessage("Congratulations, you won!");
        } else {
            displayMessage(message.getMessage());
        }
    }

    /**
     * Notifies a player disconnection
     * @param message the MVDiscMessage
     */
    public void visit(MVDiscMessage message) {
        displayMessage(message.getMessage());
    }

    /**
     * Notifies a player reconnection and or (in case of the reconnected player) resets the cards in the ModelRep
     * @param message the welcome back message
     */
    public void visit(MVWelcomeBackMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            modelRepresentation.setToolCards(message.getTcInUse());
            modelRepresentation.setPrCard(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            loadMainWindow();
            updateMR(message);
        } else {
            displayMessage(message.getMessage());
        }
    }


    public void visit(MVStartGameMessage message) {
        if (playerID == message.getPlayerID()) {
            displayMessage("It's your turn!");
            setState(State.WAIT_MOVE);
        }
        else{
            displayMessage(NOT_TURN);
            setState(State.NOT_YOUR_TURN);
        }
        updateMR(message);
    }

    public void visit(MVNewTurnMessage message) {
        if (playerID == message.getPlayerID()) {
            displayMessage("It's your turn!");
            setState(State.WAIT_MOVE);
        }
        else {
            setState(State.NOT_YOUR_TURN);
            displayMessage(NOT_TURN);
        }
        updateMR(message);
    }

    public void visit(MVTimesUpMessage message) {
        if (message.getPlayerID() == playerID) {
            displayMessage("Time's up. End of your turn.");
        }
    }

    private synchronized void setState(State s) {
        state = s;
    }

    public void register(Observer<VCAbstractMessage> o) {
        observers.add(o);
    }

    private void notify(VCAbstractMessage message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }

    /**
     * Method that fills a GridPane with a wpc
     * @param wpc the wpc
     * @param grid the gridpane
     */
    private void fillerWPC(WPC wpc, GridPane grid) {
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 5; col++) {
                Cell cell = wpc.getCell(row, col);
                if (cell.isEmpty()) {
                    if (cell.getColourR() != null) {
                        String path = "/dice/" + cell.getColourR().toString().toLowerCase() + "/0.jpg";
                        Image imageCell = new Image(getClass().getResourceAsStream(path), 30, 30, false, false);
                        grid.add(new ImageView(imageCell), col, row);
                    }

                    if (cell.getValueR() != null) {
                        String path = "/dice/grey/" + cell.getValueR().toString().toLowerCase() + ".jpg";
                        Image imageCell = new Image(getClass().getResourceAsStream(path), 30, 30, false, false);
                        grid.add(new ImageView(imageCell), col, row);
                    }
                }

                else {
                    Die d = cell.getDie();
                    int val = d.getDieValue();
                    Colour c = d.getDieColour();
                    String path = "/dice/" + c + "/" + val + ".jpg";
                    Image imageCell = new Image(getClass().getResourceAsStream(path), 30, 30, false, false);
                    grid.add(new ImageView(imageCell), col, row);
                }
            }
        }
    }

    private void setGridPanes(int nPlayers){
        List<GridPane> panes = new ArrayList<>();
        panes.add(mainWindow);
        panes.add(mainWindow1);
        panes.add(mainWindow2);
        playerPanes = new HashMap<>();

        for(int i = 1; i <= nPlayers; i++){
            if(i != playerID){
                playerPanes.put(i, panes.get(i));
            }
        }
    }
}






