package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
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

public class View extends AbstractView implements RawInputObservable {
    private int currentplayerID;
    private String playerName;
    private String errorID = "Wrong Player ID";
    private ModelRepresentation modelRepresentation;
    private VCAbstractMessage message;
    private List<RawInputObserver> rawObservers;



    public void visit(MVGameMessage message) {
        if(currentplayerID == message.getPlayerID()){
            System.out.println( message.getMessage() );
            modelRepresentation.setRoundTrack(message.getRoundTrack());
            modelRepresentation.setDraftPool(message.getDraftPool());
            modelRepresentation.setWpcs(message.getWpcs());
        }

        System.out.println(errorID);

    }


    public void visit(MVSetUpMessage message) {
        if(currentplayerID == message.getPlayerID()){
            chooseWpc(message.getIDs());
        }

        System.out.println(errorID);

    }


    public void visit(MVExtractedCardsMessage message) {
        if(currentplayerID == message.getPlayerID()){
            modelRepresentation.setPrCards(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
        }

        System.out.println(errorID);
    }



    public void getCoordinates() {

        System.out.println("Insert row number");
        Scanner Input = new Scanner(System.in);
        int row = Input.nextInt();
        message.addParameter(row);

        System.out.println("Insert column number ");
        int column = Input.nextInt();
    }

    //the player has to choose his wpc for the game

    public void chooseWpc(int possibleWPCs[]){

        int i;
        int choice = 0;
        WpcGenerator wpcGenerator = new WpcGenerator();
        WPC chosen;

        for(i=0; i<=3; i++){
            WPC temp = wpcGenerator.getWPC(possibleWPCs[i]);
            System.out.println(temp.toString() +choice);
        }

        do {
            System.out.println("Choose wpc number (Form 1 to 4)");
            choice = Integer.valueOf(choice).intValue();
        } while (choice < 1 || choice > 4);
        chosen = wpcGenerator.getWPC(choice);
        modelRepresentation.setWpcs(possibleWPCs[choice], chosen.toString());
    }

    //the player has to digit his name

    public void setPlayerName(){

        System.out.println("Insert your name");
        Scanner input = new Scanner(System.in);
        playerName = input.nextLine();
        System.out.println("Your name is: " +playerName);
    }

    public void getDraftPoolIndex(){

    }

    public void getRoundTrackPosition(){

    }

    public void displayMessage(String message){
        System.out.println(message);
    }

    public void createToolMessage(int toolcardID){
        message = new VCToolMessage(currentplayerID, toolcardID);
    }

    public void createDieMessage(){

    }

    public void createEndMessage(){
        
    }

    public void notifyController(){
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

}



