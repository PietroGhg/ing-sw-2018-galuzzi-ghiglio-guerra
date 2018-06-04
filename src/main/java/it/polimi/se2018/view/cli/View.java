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

import java.util.*;

/* Manages interrogation to be asked the user
 *@author Andrea Galuzzi
 */

public class View extends AbstractView implements RawInputObservable, Runnable {
    private String playerName;
    private ModelRepresentation modelRepresentation;
    private boolean gameLoop;
    private List<RawInputObserver> rawObservers;

    public View(String playerName) {
        modelRepresentation = new ModelRepresentation();
        playerID = 0;
        this.playerName = playerName;
        rawObservers = new ArrayList<>();
        gameLoop = true;
    }

    public void visit(MVGameMessage message) {
        if (playerID == message.getPlayerID()) {
            System.out.println(message.getMessage());
            System.out.println(message.getDraftPool());
            updateMR(message);
        } else {
            updateMR(message);
        }
    }

    public void visit(MVStartGameMessage message) {
        if (playerID == message.getPlayerID())
            System.out.println("It's your turn!");
        updateMR(message);
        new Thread(this).start();
    }

    public void visit(MVNewTurnMessage message) {
        if (playerID == message.getPlayerID())
            System.out.println("It's your turn!");
        updateMR(message);
    }

    private void updateMR(MVGameMessage message) {
        modelRepresentation.setRoundTrack(message.getRoundTrack());
        modelRepresentation.setDraftPool(message.getDraftPool());
        modelRepresentation.setWpcs(message.getWpcs());
    }


    public void visit(MVSetUpMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            System.out.println("You have been assigned the id: " + playerID);
            modelRepresentation.setPrCards(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            chooseWpc(message.getIDs());
        }
    }

    public void visit(MVWelcomeBackMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            new Thread(this).start();
        } else {
            System.out.println(message.getMessage());
        }
    }

    public void visit(MVTimesUpMessage message) {
        if(message.getPlayerID() == playerID){
            System.out.println("Time's up. End of your turn.");
        }
    }

    //the player has to choose his wpc for the game

    private void chooseWpc(int[] possibleWPCs) {
        int i;
        int choice = 0;
        WpcGenerator wpcGenerator = new WpcGenerator();
        WPC chosen;
        Scanner in = new Scanner(System.in);

        for (i = 0; i < 4; i++) {
            WPC temp = wpcGenerator.getWPC(possibleWPCs[i]);
            System.out.println(i + 1 + ":\n" + temp.toString());
        }

        do {
            System.out.println("Choose wpc number (Form 1 to 4)");
            choice = in.nextInt();
        } while (choice < 1 || choice > 4);
        int chosenID = possibleWPCs[choice - 1];
        chosen = wpcGenerator.getWPC(chosenID);
        modelRepresentation.setWpcs(playerID, chosen.toString());
        notify(new VCSetUpMessage(playerID, chosenID));
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

    public void getCoordinates2() {

        System.out.println("Insert another die? [yes/no]");
        Scanner Input = new Scanner(System.in);
        String answer = Input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            getCoordinates("Insert the coordinates of the Die to move. ");
            getCoordinates("Insert the coordinates of the recipient cell. ");

        }
    }

    public void getIncrement() {
        System.out.println("Increas or decrease? \n[Increase: 1, Decrease: -1]");
        Scanner input = new Scanner(System.in);
        int value = input.nextInt();
        rawNotify(new RawRequestedMessage(value));
    }


    public void getDraftPoolIndex() {
        System.out.println("Insert DraftPool Index");
        Scanner input = new Scanner(System.in);
        int index = input.nextInt();
        rawNotify(new RawRequestedMessage(index));


    }

    public void getRoundTrackPosition(String s) {
        System.out.println(s);
        System.out.println("Insert Round number");
        Scanner Input = new Scanner(System.in);
        int roundNumber = Input.nextInt();
        rawNotify(new RawRequestedMessage(roundNumber));

        System.out.println("Insert Die number");
        int dieNumber = Input.nextInt();
        rawNotify(new RawRequestedMessage(dieNumber));

    }

    public void newDieValue(String s) {
        System.out.println(s);
        System.out.println("Insert new Value");
        Scanner Input = new Scanner(System.in);
        int value = Input.nextInt();
        rawNotify(new RawRequestedMessage(value));
    }

    public void displayMessage(String message) {
        System.out.println(message);
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

    public void getMove() {
        System.out.println("Insert command");
        Scanner Input = new Scanner(System.in);
        String move = Input.nextLine();
        rawNotify(new RawUnrequestedMessage(move));

    }

    @Override
    public void run() {
        while (gameLoop) {
            getMove();
        }

    }

    public void showRoundTrack(){
        System.out.println(modelRepresentation.getDraftPool());
    }

    public void showMyBoard(){
        String myBoard = modelRepresentation.getWpcs(playerID);
        System.out.println(myBoard);
    }

    public void showBoards(){
        int id;
        for(id=1; id <= modelRepresentation.getNumPlayers(); id++){
            if(id!=playerID){
                String board = modelRepresentation.getWpcs(id);
                System.out.println(board);
            }
        }

    }

    public void showToolCards(){
        System.out.println();


    }

    public void showDraftPool(){
        System.out.println(modelRepresentation.getDraftPool());

    }

    public void showMyObjectiveCard(){
        System.out.println(modelRepresentation.getPrCards());
    }

    public void showObjectiveCards(){
        int i;
       String[] puCards = modelRepresentation.getPuCards();
       for(i=0; i<=2; i++){
           System.out.println(puCards[i]);
       }
    }

}



