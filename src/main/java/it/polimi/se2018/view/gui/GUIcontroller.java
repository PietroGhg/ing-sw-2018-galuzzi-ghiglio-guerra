package it.polimi.se2018.view.gui;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCSetUpMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawRequestedMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawUnrequestedMessage;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.Cell;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.*;
import it.polimi.se2018.view.cli.ModelRepresentation;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GUIcontroller implements ViewInterface, Observer<MVAbstractMessage>{

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
    private  ModelRepresentation modelRepresentation;
    private  List<RawInputObserver> rawObservers;
    private  String playerName;
    private  State state;
    private  Latch latch;
    private  List<Observer<VCAbstractMessage>> observers;
    private  int playerID;
    private static final Logger LOGGER = Logger.getLogger(GUIcontroller.class.getName());

    private static final String SELECT_DP = "Select a die from the draftpool.";
    private static final String SELECT_CELL = "Select a cell.";
    private static final String NOT_TURN = "Not your turn";

    public void init(ModelRepresentation modelRep){
        latch = new Latch();
        rawObservers = new ArrayList<>();
        state = State.NOT_YOUR_TURN;
        modelRepresentation = modelRep;
        observers = new ArrayList<>();
    }

    public void update(MVAbstractMessage message){
        message.accept(this);
    }


    @FXML
    public void handleChoice(Event e){
        ToggleGroup choice = new ToggleGroup();
        Button b = (Button)e.getSource();
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
        Map<Integer,WPC> extractedWPCs = modelRepresentation.getSelected();
        WPC extracted = extractedWPCs.get(i);

        Stage s = (Stage)b.getScene().getWindow();
        s.close();
        notifyController(new VCSetUpMessage(playerID, extracted.getId()));

        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("/fxml/gameWindow.fxml"));
        try {
            Scene window = new Scene(loader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Game");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        }
        catch(IOException ex){
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }

    public void showToolCards() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("/fxml/toolCards.fxml"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("ToolCards");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();

    }

    public void showDraftPool(){
       try { FXMLLoader loader = new FXMLLoader();
           loader.setController(this);
           loader.setLocation(getClass().getResource("/fxml/draftPool.fxml"));
           Scene window = new Scene(loader.load(), 600, 400);
           Stage stage = new Stage();
           stage.setScene(window);
           stage.setTitle("DraftPool");
           stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
           stage.setResizable(false);
           stage.show();
       }
       catch (IOException e) {
           String m = Arrays.toString(e.getStackTrace());
           LOGGER.log(Level.SEVERE, m);
       }

    }

    public void showPuCards() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setController(this);
        loader.setLocation(getClass().getResource("/fxml/puCards.fxml"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("PuCards");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();


    }

    public void diceMove(){
        rawNotify(new RawUnrequestedMessage("dicemove"));

    }

    public void toolCard(){

        rawNotify(new RawUnrequestedMessage("toolcard 1"));

    }

    public int getPlayerID(){
        return playerID;
    }

    @FXML
    public void selectCell(Event e){
        Button b = (Button)e.getSource();
        String s = b.getId();
        switch (s){
            case("Button00"): cellPressed(0,0); break;
            case("Button01"): cellPressed(0,1); break;
            case("Button02"): cellPressed(0,2); break;
            case("Button03"): cellPressed(0,3); break;
            case("Button04"): cellPressed(0,4); break;
            case("Button10"): cellPressed(1,0); break;
            case("Button11"): cellPressed(1,1); break;
            case("Button12"): cellPressed(1,2); break;
            case("Button13"): cellPressed(1,3); break;
            case("Button14"): cellPressed(1,4); break;
            case("Button20"): cellPressed(2,0); break;
            case("Button21"): cellPressed(2,1); break;
            case("Button22"): cellPressed(2,2); break;
            case("Button23"): cellPressed(2,3); break;
            case("Button24"): cellPressed(2,4); break;
            case("Button30"): cellPressed(3,0); break;
            case("Button31"): cellPressed(3,1); break;
            case("Button32"): cellPressed(3,2); break;
            case("Button33"): cellPressed(3,3); break;
            case("Button34"): cellPressed(3,4); break;


            case("DP0"): dpSelected(0); break;
            case("DP1"): dpSelected(1); break;
            case("DP2"): dpSelected(2); break;
            case("DP3"): dpSelected(3); break;
            case("DP4"): dpSelected(4); break;
            case("DP5"): dpSelected(5); break;
            case("DP6"): dpSelected(6); break;
            case("DP7"): dpSelected(7); break;
            case("DP8"): dpSelected(8); break;

            default: displayMessage("Error");
        }





    }

    private void dpSelected(int id){
        switch(state){
            case NOT_YOUR_TURN :
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

    private void cellPressed(int row, int col){
       switch(state){
            case NOT_YOUR_TURN :
                displayMessage(NOT_TURN);
                break;
           case COORDINATES_REQUEST:
                rawNotify(new RawRequestedMessage(row));
                rawNotify(new RawRequestedMessage(col));
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

    public void getCoordinates(String m){
        setState(State.COORDINATES_REQUEST);
        displayMessage(SELECT_CELL);
        latch.reset();
        latch.await();
    }

    public void getCoordinates2(){
        int column = 0;
        rawNotify(new RawRequestedMessage(column));
    }

    public void getValidCoordinates(List<int[]> validCoordinates){

        if(validCoordinates.isEmpty()){
            displayMessage("Die not placeable.");
        }
        else {
            //TODO: attivare solo i bottoni con coordinate valide
        }

    }

    public void getIncrement(){
        //TODO: mostrare finestra per scelta (incrementare o diminuire)
    }

    public void getDraftPoolIndex(){
        setState(State.DP_INDEX_REQUEST);
        displayMessage(SELECT_DP);
        latch.reset();
        latch.await();
    }

    public void getRoundTrackPosition(String s){

    }

    public void newDieValue(){
        //TODO: mostrare finestra per inserimento nuovo valore
    }

    public void displayMessage(String message){
        //TODO: Provvisorio, sostituire con finestra che mostra message o con label in finestra principale
        System.out.println(message);
    }

    public void showRoundTrack(){
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
        }
        catch (IOException e){
            String s = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, s);
        }

    }



    public void showMyBoard(){
        //empty method since the window is always shown
    }

    public void showBoards(){
        try {
            //TODO: aggiornare grafica
            FXMLLoader loader = new FXMLLoader();
            loader.setController(this);
            loader.setLocation(getClass().getResource("/fxml/windows.fxml"));
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Windows");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){
            String m = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, m);
        }

    }

    public void notifyController(VCAbstractMessage message){
        notify(message);
    }

    public void rawRegister(RawInputObserver observer){
        rawObservers.add(observer);

    }

    public void rawNotify(RawInputMessage message){
        for (RawInputObserver ob : rawObservers) {
            ob.rawUpdate(message);
        }
    }

    private void updateMR(MVGameMessage message) {
        modelRepresentation.setRoundTrack(message.getRoundTrack());
        modelRepresentation.setDraftPool(message.getDraftPool());
        modelRepresentation.setWpcs(message.getWpcs());
        modelRepresentation.setDiceBag(message.getDiceBag());
        modelRepresentation.setCurrPlayer(message.getCurrPlayer());

        WPC wpc = modelRepresentation.getWpc(playerID);
        for (int row=0; row<4; row++){
            for(int col=0; col<5; col++){
                Cell cell = wpc.getCell(row, col);
                if(cell.isEmpty()){
                    if(cell.getColourR() != null)
                        ;//TODO: mettici una cosa del colore giusto
                    if(cell.getValueR() != null)
                        ;//TODO: mettici il numero giusto
                }
                else{
                    Die d = cell.getDie();
                    int val = d.getDieValue();
                    Colour c = d.getDieColour();
                    Button b = (Button)getCellByCoordinates(row, col);
                    //TODO: aprire percorso giusto
                    BackgroundImage backgroundImage = new BackgroundImage( new Image(getClass().getResource("/pic3525224.jpg").toExternalForm())
                    , BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
                    Background background = new Background(backgroundImage);
                    b.setBackground(background);
                }
            }
        }
    }

    private Node getCellByCoordinates(int row, int col){
        Node result = null;
        ObservableList<Node> children = myWindow.getChildren();
        for (Node node : children){
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col){
                result = node;
                break;
            }
        }
        return result;
    }

    public void setPlayerName(String pn){
        setPN(pn);
    }

    private  synchronized void setPN(String pn){
        playerName = pn;
    }

    public void visit(MVGameMessage message){
        if (playerID == message.getPlayerID()) {
            displayMessage(message.getMessage());
            updateMR(message);
        } else {
            updateMR(message);
        }
    }

    public void visit(MVSetUpMessage message){
        modelRepresentation.setSelected(message.getExtracted());
        playerID = message.getPlayerID();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choice.fxml"));
            loader.setController(this);
            Scene w = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(w);
            stage.setTitle("Choice");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void visit(MVWinnerMessage message){
        if(message.getPlayerID() == playerID){
            displayMessage("Congratulations, you won!");
        }
        else{
            displayMessage(message.getMessage());
        }
    }

    public void visit(MVDiscMessage message){
        displayMessage(message.getMessage());
    }

    public void visit(MVWelcomeBackMessage message){
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            modelRepresentation.setToolCards(message.getTcInUse());
            modelRepresentation.setPrCard(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            updateMR(message);
        } else {
            displayMessage(message.getMessage());
        }
    }

    public void visit(MVStartGameMessage message){
        if (playerID == message.getPlayerID())
            displayMessage("It's your turn!");
        updateMR(message);
    }

    public void visit(MVNewTurnMessage message){
        if (playerID == message.getPlayerID())
            displayMessage("It's your turn!");
        else
            setState(State.NOT_YOUR_TURN);
        updateMR(message);
    }

    public void visit(MVTimesUpMessage message){
        if(message.getPlayerID() == playerID){
            displayMessage("Time's up. End of your turn.");
        }
    }

    private synchronized void setState(State s){
        state = s;
    }

    public void register(Observer<VCAbstractMessage> o){
        observers.add(o);
    }

    private void notify(VCAbstractMessage message){
        for(Observer o: observers){
            o.update(message);
        }
    }
}


