package it.polimi.se2018.view.gui;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawRequestedMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawUnrequestedMessage;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.Cell;
import it.polimi.se2018.model.wpc.WPC;
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
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;


public class GUIcontroller extends AbstractView implements ViewInterface  {

    public RadioButton connection1;
    public RadioButton connection2;
    public TextField user;
    public RadioButton choice1;
    public RadioButton choice2;
    public RadioButton choice3;
    public RadioButton choice4;
    public Button Button00;
    public Button Button01;
    public GridPane myWindow;
    public ModelRepresentation modelRepresentation;
    private List<RawInputObserver> rawObservers;
    private State state;
    private CountDownLatch latch;





    public void handleChoice() throws IOException{
        ToggleGroup choice = new ToggleGroup();
        choice1.setToggleGroup(choice);
        choice2.setToggleGroup(choice);
        choice3.setToggleGroup(choice);
        choice4.setToggleGroup(choice);
        choice1.setUserData("1");
        choice2.setUserData("2");
        choice3.setUserData("3");
        choice4.setUserData("4");
        String n = choice.getSelectedToggle().getUserData().toString();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/gameWindow.fxml"));
        Scene window = new Scene(loader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("Game");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();
    }

    public void showToolCards() throws IOException{
        FXMLLoader loader = new FXMLLoader();
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
           loader.setLocation(getClass().getResource("/fxml/draftPool.fxml"));
           Scene window = new Scene(loader.load(), 600, 400);
           Stage stage = new Stage();
           stage.setScene(window);
           stage.setTitle("DraftPool");
           stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
           stage.setResizable(false);
           stage.show();
       }
       catch (IOException e) { e.printStackTrace();
       }

    }

    public void showPuCards() throws IOException{
        FXMLLoader loader = new FXMLLoader();
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
        //return playerID;
        return 0;
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
        }





    }

    private void dpSelected(int id){
        switch(state){
            case NOT_YOUR_TURN :
                displayMessage("Not your turn");
                break;
            case COORDINATES_REQUEST:
                displayMessage("Select draftpool die.");
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

    public void cellPressed(int row, int col){
       switch(state){
            case NOT_YOUR_TURN :
                displayMessage("Not your turn");
                break;
           case COORDINATES_REQUEST:
                rawNotify(new RawRequestedMessage(row));
                rawNotify(new RawRequestedMessage(col));
                latch.countDown();
                break;
           case DP_INDEX_REQUEST:
               displayMessage("Insert coordinates");
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
        state = State.COORDINATES_REQUEST;
        latch = new CountDownLatch(1);
        try {
            latch.await();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void getCoordinates2(){
        int column = 0;
        rawNotify(new RawRequestedMessage(column));
    }

    public void getValidCoordinates(List<int[]> validCoordinates){
        Scanner in = new Scanner(System.in);
        if(validCoordinates.isEmpty()){

        }
        else {

            int chosen;
            do {


                chosen = in.nextInt();
            }while(!(chosen>=1 && chosen <= validCoordinates.size()));
            int[] temp = validCoordinates.get(chosen - 1);
            rawNotify(new RawRequestedMessage(temp[0]));
            rawNotify(new RawRequestedMessage(temp[1]));
        }

    }

    public void getIncrement(){

    }

    public void getDraftPoolIndex(){
        state = State.DP_INDEX_REQUEST;
        latch = new CountDownLatch(1);
        try{
            latch.await();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    public void getRoundTrackPosition(String s){

    }

    public void newDieValue(){

    }

    public void displayMessage(String message){

    }

    public void showRoundTrack(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/roundTrack.fxml"));
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("RoundTrack");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){e.printStackTrace();}

    }



    public void showMyBoard(){


    }

    public void showBoards(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/windows.fxml"));
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Windows");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e){e.printStackTrace();}

    }

    public void notifyController(VCAbstractMessage message){

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
            for(int col=0; row<5; col++){
                Cell cell = wpc.getCell(row, col);
                if(cell.isEmpty()){
                    if(cell.getColourR() != null)
                        ;//mettici una cosa del colore giusto
                    if(cell.getValueR() != null)
                        ;//mettici il numero giusto
                }
                else{
                    //mettere il dado giusto
                    Die d = cell.getDie();
                    int val = d.getDieValue();
                    Colour c = d.getDieColour();
                    Button b = (Button)getCellByCoordinates(row, col);
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
            if(myWindow.getRowIndex(node) == row && myWindow.getColumnIndex(node) == col){
                result = node;
                break;
            }
        }
        return result;
    }

    public void visit(MVGameMessage message){
        if (playerID == message.getPlayerID()) {

            updateMR(message);
        } else {
            updateMR(message);
        }
    }

    public void visit(MVSetUpMessage message){}
    public void visit(MVWinnerMessage message){}
    public void visit(MVDiscMessage message){}
    public void visit(MVWelcomeBackMessage message){}
    public void visit(MVStartGameMessage message){}
    public void visit(MVNewTurnMessage message){}
    public void visit(MVTimesUpMessage message){}

}


