package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.*;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawRequestedMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawUnrequestedMessage;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import it.polimi.se2018.utils.Printer;
import it.polimi.se2018.utils.RawInputObservable;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.*;



import java.util.*;


/* Manages interrogation to be asked the user
 *@author Andrea Galuzzi
 */

public class View extends AbstractView implements RawInputObservable, ViewInterface {
    private Printer out = new Printer();
    private String playerName;
    private ModelRepresentation modelRepresentation;
    private boolean gameLoop;
    private boolean inputLoop;
    private final Object lock = new Object();
    private InputThread inputThread;
    private List<RawInputObserver> rawObservers;

    public View(String playerName, ModelRepresentation modelRepresentation) {
        this.modelRepresentation = modelRepresentation;
        playerID = 0;
        this.playerName = playerName;
        rawObservers = new ArrayList<>();
        gameLoop = true;
        inputLoop = true;
        inputThread = new InputThread(this, lock);
    }

    public void visit(MVGameMessage message) {
        if (playerID == message.getPlayerID()) {
            out.println(message.getMessage());
            updateMR(message);
        } else {
            updateMR(message);
        }
    }

    public void visit(MVStartGameMessage message) {
        if (playerID == message.getPlayerID())
            out.println("It's your turn!");
        updateMR(message);
        inputThread.start();
    }

    public void visit(MVNewTurnMessage message) {
        if (playerID == message.getPlayerID())
            out.println("It's your turn!");
        updateMR(message);
    }

    private void updateMR(MVGameMessage message) {
        modelRepresentation.setRoundTrack(message.getRoundTrack());
        modelRepresentation.setDraftPool(message.getDraftPool());
        modelRepresentation.setWpcs(message.getWpcs());
        modelRepresentation.setDiceBag(message.getDiceBag());

        synchronized (lock) {
            inputLoop = true;
            lock.notifyAll();
        }
    }


    public void visit(MVSetUpMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            out.println("You have been assigned the id: " + playerID);
            modelRepresentation.setPrCard(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            modelRepresentation.setToolCards(message.getTcInUse());
            chooseWpc(message.getIDs());
        }
    }

    public void visit(MVWelcomeBackMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            updateMR(message);
            inputThread.start();
        } else {
            out.println(message.getMessage());
        }
    }

    public void visit(MVTimesUpMessage message) {
        if(message.getPlayerID() == playerID){
            out.println("Time's up. End of your turn.");
        }
    }

    public void visit(MVWinnerMessage message) {
        if(message.getPlayerID() == playerID){
            out.println("Congratulations, you won!");
        }
        else{
            out.println(message.getMessage());
        }
    }

    //the player has to choose his wpc for the game

    private void chooseWpc(int[] possibleWPCs) {
        int i;
        int choice;
        WpcGenerator wpcGenerator = new WpcGenerator();
        WPC chosen;
        Scanner in = new Scanner(System.in);

        for (i = 0; i < 4; i++) {
            WPC temp = wpcGenerator.getWPC(possibleWPCs[i]);
            out.println(i + 1 + ":\n" + temp.toString());
        }

        do {
            out.println("Choose wpc number (Form 1 to 4)");
            choice = in.nextInt();
        } while (choice < 1 || choice > 4);
        int chosenID = possibleWPCs[choice - 1];
        chosen = wpcGenerator.getWPC(chosenID);
        modelRepresentation.setWpcs(playerID, chosen);
        notify(new VCSetUpMessage(playerID, chosenID));
    }


    public void getCoordinates(String s) {
        out.println(s);
        out.println("Insert row number");
        Scanner input = new Scanner(System.in);
        int row = input.nextInt();
        rawNotify(new RawRequestedMessage(row));

        out.println("Insert column number ");
        int column = input.nextInt();
        rawNotify(new RawRequestedMessage(column));
    }

    public void getCoordinates2() {

        out.println("Insert another die? [yes/no]");
        Scanner input = new Scanner(System.in);
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            getCoordinates("Insert the coordinates of the Die to move. ");
            getCoordinates("Insert the coordinates of the recipient cell. ");

        }
    }

    public void getValidCoordinates(List<int[]> validCoordinates){
        Scanner in = new Scanner(System.in);
        for(int i = 0; i < validCoordinates.size(); i++) {
            int[] temp = validCoordinates.get(i);
            out.println((i+1) + ": " + temp[0] + ", " + temp[1]);
        }
        if(validCoordinates.isEmpty()){
            out.println("Die not placeable. ");
        }
        else {
            out.println("Select valid coordinates. ");
            int chosen;
            do {
                chosen = in.nextInt();
            }while(!(chosen>=1 && chosen <= validCoordinates.size()));
            int[] temp = validCoordinates.get(chosen - 1);
            rawNotify(new RawRequestedMessage(temp[0]));
            rawNotify(new RawRequestedMessage(temp[1]));
        }
    }

    public void getIncrement() {
        out.println("Increas or decrease? \n[Increase: 1, Decrease: -1]");
        Scanner input = new Scanner(System.in);
        int value = input.nextInt();
        rawNotify(new RawRequestedMessage(value));
    }


    public void getDraftPoolIndex() {
        out.println("Insert DraftPool Index");
        Scanner input = new Scanner(System.in);
        int index = input.nextInt();
        rawNotify(new RawRequestedMessage(index));


    }

    public void getRoundTrackPosition(String s) {
        out.println(s);
        out.println("Insert Round number");
        Scanner input = new Scanner(System.in);
        int roundNumber = input.nextInt();
        rawNotify(new RawRequestedMessage(roundNumber));

        out.println("Insert Die number");
        int dieNumber = input.nextInt();
        rawNotify(new RawRequestedMessage(dieNumber));

    }

    public void newDieValue() {
        out.println("Insert new Value");
        Scanner input = new Scanner(System.in);
        int value;
        do {
            value = input.nextInt();
        }while(!(value>=1 && value <=6));
        rawNotify(new RawRequestedMessage(value));
    }

    public void displayMessage(String message) {
        out.println(message);
    }

    public int getPlayerID(){ return playerID; }

    public void notifyController(VCAbstractMessage message) {
        inputLoop = false;
        inputThread.notifyController(message);
    }

    public void rawRegister(RawInputObserver observer) {
        rawObservers.add(observer);
    }

    public void rawNotify(RawInputMessage message) {
        for (RawInputObserver ob : rawObservers) {
            ob.rawUpdate(message);
        }
    }

    public void showRoundTrack(){
        out.println(modelRepresentation.getRoundTrack());
    }

    public void showMyBoard(){
        String myBoard = modelRepresentation.getWpc(playerID).toString();
        out.println(myBoard);
    }

    public void showBoards(){
        int id;
        for(id=1; id <= modelRepresentation.getNumPlayers(); id++){
            if(id!=playerID){
                String board = modelRepresentation.getWpc(id).toString();
                out.println(board);
            }
        }

    }

    public void showDraftPool(){
        out.println(modelRepresentation.getDraftPool());

    }

    /**
     * Thread used to prevent overlays of user requested and unrequested input
     * @author Pietro Ghiglio
     */
    private class InputThread extends Thread{
        private View view;
        private final Object lock;

        InputThread(View view, Object lock){
            this.view = view;
            this.lock = lock;
        }

        @Override
        public void run() {
            while (gameLoop) {
                getMove();
            }
        }

        void notifyController(VCAbstractMessage message) {
            view.notify(message);
            synchronized (lock){
                try{
                    while(!inputLoop)
                        lock.wait();
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void getMove() {
            out.println("Insert command");
            Scanner input = new Scanner(System.in);
            String move = input.nextLine();
            rawNotify(new RawUnrequestedMessage(move));
        }
    }



}



