package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.*;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawRequestedMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawUnrequestedMessage;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import it.polimi.se2018.utils.RawInputObservable;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.*;

import java.util.List;
import java.util.Scanner;

/* Manages interrogation to be asked the user
 *@author Andrea Galuzzi
 */

public class View extends AbstractView implements RawInputObservable, Runnable {
    private int currentplayerID = 0;
    private String playerName;
    private String errorID = "Wrong Player ID";
    private ModelRepresentation modelRepresentation;
    private VCAbstractMessage message;
    private List<RawInputObserver> rawObservers;

    public View(){
        modelRepresentation = new ModelRepresentation();
    }

    public void visit(MVGameMessage message) {
        if(currentplayerID == message.getPlayerID()){
            System.out.println( message.getMessage() );
            modelRepresentation.setRoundTrack(message.getRoundTrack());
            modelRepresentation.setDraftPool(message.getDraftPool());
            modelRepresentation.setWpcs(message.getWpcs());
        }
        else{
            modelRepresentation.setRoundTrack(message.getRoundTrack());
            modelRepresentation.setDraftPool(message.getDraftPool());
            modelRepresentation.setWpcs(message.getWpcs());
        }

    }


    public void visit(MVSetUpMessage message) {
        if(currentplayerID == 0) {
            currentplayerID = message.getPlayerID();
            modelRepresentation.setPrCards(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            chooseWpc(message.getIDs());
            System.out.println(errorID);
        }

    }


    public void visit(MVExtractedCardsMessage message) {
        if(currentplayerID == message.getPlayerID()){
            modelRepresentation.setPrCards(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
        }

        System.out.println(errorID);
    }



    public void getCoordinates(String s) {
        System.out.println(s);
        System.out.println("Insert row number");
        Scanner Input = new Scanner(System.in);
        int row = Input.nextInt();
        rawNotify(new RawRequestedMessage(row));

        System.out.println("Insert column number ");
        int column = Input.nextInt();
        rawNotify(new RawRequestedMessage(column));
    }

    public void getCoordinates2(String s){

        System.out.println(s);
        Scanner Input = new Scanner(System.in);
        String answer = Input.nextLine();

        if(answer.equalsIgnoreCase("yes")) {
            System.out.println("Insert row number");
            int row = Input.nextInt();
            rawNotify(new RawRequestedMessage(row));

            System.out.println("Insert column number ");
            int column = Input.nextInt();
            rawNotify(new RawRequestedMessage(column));

            getCoordinates("Insert the coordinates of the Die to move. ");
            getCoordinates("Insert the coordinates of the recipient cell. ");

        }


    }

    //the player has to choose his wpc for the game

    private void chooseWpc(int[] possibleWPCs){
        int i;
        int choice = 0;
        WpcGenerator wpcGenerator = new WpcGenerator();
        WPC chosen;
        Scanner in = new Scanner(System.in);

        for(i=0; i<=3; i++){
            WPC temp = wpcGenerator.getWPC(possibleWPCs[i]);
            System.out.println(temp.toString() +choice);
        }

        do {
            System.out.println("Choose wpc number (Form 1 to 4)");
            choice = in.nextInt();
        } while (choice < 1 || choice > 4);
        int chosenID = possibleWPCs[choice];
        chosen = wpcGenerator.getWPC(chosenID);
        modelRepresentation.setWpcs(currentplayerID, chosen.toString());
        notify(new VCSetUpMessage(currentplayerID, chosenID));
    }

    //the player has to digit his name

    public void setPlayerName(){

        System.out.println("Insert your name");
        Scanner input = new Scanner(System.in);
        playerName = input.nextLine();
        System.out.println("Your name is: " + playerName);
    }

    public void getDraftPoolIndex(String s){
        System.out.println(s);
        System.out.println("Insert DraftPool Index number");
        Scanner Input = new Scanner(System.in);
        int index = Input.nextInt();
        rawNotify(new RawRequestedMessage(index));


    }

    public void getRoundTrackPosition(String s){
        System.out.println(s);
        System.out.println("Insert Round number");
        Scanner Input = new Scanner(System.in);
        int roundNumber = Input.nextInt();
        rawNotify(new RawRequestedMessage(roundNumber));

        System.out.println("Insert Die number");
        int dieNumber = Input.nextInt();
        rawNotify(new RawRequestedMessage(dieNumber));

    }

    public void newDieValue(String s){
        System.out.println(s);
        System.out.println("Insert new Value");
        Scanner Input = new Scanner(System.in);
        int value = Input.nextInt();
        rawNotify(new RawRequestedMessage(value));
    }

    public void getConfirmation(String s){


    }

    public void displayMessage(String message){
        System.out.println(message);
    }

    public void createToolMessage(int toolcardID){
        message = new VCToolMessage(currentplayerID, toolcardID);
    }

    public void createDieMessage(){ message = new VCDieMessage(currentplayerID); }

    public void createEndMessage(){ message = new VCEndTurnMessage(currentplayerID); }

    public void notifyController(VCAbstractMessage message){
        notify(message);
    }

    public void rawRegister(RawInputObserver observer){
        rawObservers.add(observer);
    }

    public void rawNotify(RawInputMessage message){
        for(RawInputObserver ob : rawObservers){
            ob.rawUpdate(message);
        }
    }

    public void getMove(){
        System.out.println("Make you move");
        Scanner Input = new Scanner(System.in);
        String move = Input.nextLine();
        rawNotify(new RawUnrequestedMessage(move));

    }

    public void run(){
        getMove();
    }

}



