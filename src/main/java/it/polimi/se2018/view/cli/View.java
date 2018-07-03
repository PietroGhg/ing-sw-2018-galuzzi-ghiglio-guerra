package it.polimi.se2018.view.cli;

import it.polimi.se2018.controller.*;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawRequestedMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawUnrequestedMessage;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.Printer;
import it.polimi.se2018.utils.RawInputObservable;
import it.polimi.se2018.utils.RawInputObserver;
import it.polimi.se2018.view.*;



import java.util.*;


/**
 * Class for the command line interface.
 *
 * Command are asked in a loop and notified to VCMessageCreator; VCMessageCreator either shows the
 * requested elements (toolcard, objective cards, game boards, and so on) or starts a ParameterGetter input sequence.
 * When the sequence is completed the server is notified with the message representing the player move.
 *
 * This class contains two private classes that extend the Thread class: InputThread and AskingThread.
 * InputThread handles the loop that requires command from the user, put on wait when a VCMessage is notified
 * to the controller, and woken up when an answer (a MVmessage) is received.
 * AskingThread polls every 0.5 second that no ParameterGetter request is being performed and, if InputThread
 * is in waiting state, wakes it up. This is done in order to handle the situation where a player enters
 * data during a ParamerGetter request, and the turn timer runs out: the player will finish to complete the request,
 * but will receive a MVMessage reporting that it is not his turn. This prevents overlapping between a command request
 * by InputThread and a value request by a ParameterGetter.
 *
 * @author Andrea Galuzzi
 * @author Pietro Ghiglio
 */

public class View extends AbstractView implements RawInputObservable, ViewInterface {
    private static final String START_GAME = "Game starting. Type \"help\" for a list of commands";
    private Printer out = new Printer();
    private String playerName;
    private ModelRepresentation modelRepresentation;
    private boolean gameLoop;
    private boolean inputLoop;
    private final Object lock = new Object();
    private InputThread inputThread;
    private AskingThread askingThread;
    private List<RawInputObserver> rawObservers;
    private boolean isAsking; //flag set to true when VCMessageCreator is performing a series of request

    public View(String playerName, ModelRepresentation modelRepresentation) {
        this.modelRepresentation = modelRepresentation;
        playerID = 0;
        this.playerName = playerName;
        rawObservers = new ArrayList<>();
        gameLoop = true;
        inputLoop = true;
        isAsking = false;
        askingThread = new AskingThread();
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
        if (playerID == message.getPlayerID()) {
            out.println("It's your turn!");
            out.println(START_GAME);
        }
        updateMR(message);
        inputThread.start();
        askingThread.start();
    }

    public void visit(MVNewTurnMessage message) {
        if (playerID == message.getPlayerID())
            out.println("It's your turn!");
        updateMR(message);
    }

    /**
     * Method that updates the ModelRepresentation and, if no ParameterRequest is being performed, wakes up the InputThread
     * (otherwise it will be awakened by the AskingThread)
     * @param message the MVGameMessage containing the data
     */
    private void updateMR(MVGameMessage message) {
        modelRepresentation.setRoundTrack(message.getRoundTrack());
        modelRepresentation.setDraftPool(message.getDraftPool());
        modelRepresentation.setWpcs(message.getWpcs());
        modelRepresentation.setDiceBag(message.getDiceBag());
        modelRepresentation.setCurrPlayer(message.getCurrPlayer());

        if(!isAsking) {
            synchronized (lock) {
                inputLoop = true;
                lock.notifyAll();
            }
        }
    }

    /**
     * Method that sets the Private, Public and ToolCards in the ModelRepresentation and calls chooseWPC(), asking the player to choose
     * one of the extracted schema cards
     * @param message the MVSetUpMessage
     */
    public void visit(MVSetUpMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            out.println("You have been assigned the id: " + playerID);
            modelRepresentation.setPrCard(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            modelRepresentation.setToolCards(message.getTcInUse());
            chooseWpc(message.getExtracted());
        }
    }

    /**
     * Method called when a player re-joins the game, resets the cards in ModelRepresentation and updates the ModelRep
     * @param message A welcome back message containing both the cards and the game data
     */
    public void visit(MVWelcomeBackMessage message) {
        if (playerName.equals(message.getPlayerName())) {
            playerID = message.getPlayerID();
            modelRepresentation.setToolCards(message.getTcInUse());
            modelRepresentation.setPrCard(message.getPrCard());
            modelRepresentation.setPuCards(message.getPuCards());
            updateMR(message);
            inputThread.start();
            askingThread.start();
        } else {
            out.println(message.getMessage());
        }
    }

    /**
     * Notifies the player that the turn timer run out.
     * @param message the MVTimerUpMessage
     */
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
        out.println("Press enter to close Sagrada.");
        new Scanner(System.in).nextLine();
        System.exit(0);
    }

    public void visit(MVDiscMessage message){
        out.println(message.getMessage());
    }


    /**
     * Method that displays the extracted schema cards and ask a player to choose one of them.
     * @param extracted the extracted cards
     */
    private void chooseWpc(Map<Integer, WPC> extracted) {
        int i;
        int choice;
        Scanner in = new Scanner(System.in);

        for (i = 0; i < 4; i++) {
            WPC temp = extracted.get(i+1);
            out.println(i + 1 + ":\n" + temp.toString());
        }

        do {
            out.println("Choose wpc number (Form 1 to 4)");
            choice = in.nextInt();
        } while (choice < 1 || choice > 4);

        WPC chosen = extracted.get(choice);
        modelRepresentation.setWpcs(playerID, chosen);
        notify(new VCSetUpMessage(playerID, chosen.getId()));
    }

    /**
     * Method that asks for coodinates in a cell
     * @param s A string that will be displayed
     */
    public void getCoordinates(String s) {
        out.println(s);
        out.println("Insert row number");
        Scanner input = new Scanner(System.in);
        int row;
        do {
            try {
                row = input.nextInt();
            }
            catch(InputMismatchException e){
                row = -1;
            }
        }while(!(row >= 0 && row <= 3));
        rawNotify(new RawRequestedMessage(row));

        out.println("Insert column number ");
        int column;
        do {
            try {
                column = input.nextInt();
            }
            catch(InputMismatchException e){
                column = -1;
            }
        }while(!(column >= 0 && column <= 4));
        rawNotify(new RawRequestedMessage(column));
    }

    /**
     * Method that asks the coordinates of a cell after asking a confirmation, used for ToolCard 12
     */
    public void getCoordinates2() {

        out.println("Insert another die? [yes/no]");
        Scanner input = new Scanner(System.in);
        String answer;
        do {
            answer = input.nextLine();
        }while(!(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")));

        if (answer.equalsIgnoreCase("yes")) {
            getCoordinates("Insert the coordinates of the Die to move. ");
            getCoordinates("Insert the coordinates of the recipient cell. ");

        }
    }

    /**
     * Method that, given a list of valid coordinates, asks for the player to choose one between them, used for ToolCard 6  and 11.
     * @param validCoordinates the list of valid coordinates
     */
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

    /**
     * Method that asks if a player wants to increase or decrease the value of a die, used for ToolCard 1
     */
    public void getIncrement() {
        out.println("Increas or decrease? \n[Increase: 1, Decrease: -1]");
        Scanner input = new Scanner(System.in);
        int value;
        do{
            try {
                value = input.nextInt();
            }
            catch(InputMismatchException e){
                value = 0;
            }
        }while(!(value == -1 || value == 1));
        rawNotify(new RawRequestedMessage(value));
    }


    public void getDraftPoolIndex() {
        out.println("Insert DraftPool Index");
        Scanner input = new Scanner(System.in);
        int index;
        do {
            index = input.nextInt();
        }while(!(index >= 0 && index < modelRepresentation.getDraftPool().size()));
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

    /**
     * Method that asks for a new die value
     */
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

    /**
     * Method that notifies the server and sets inputLoop and isAsking to false: the InputThread enteres the waiting state and no ParameterGetter sequence is
     * being performed.
     * @param message the message that gets notifed
     */
    public void notifyController(VCAbstractMessage message) {
        inputLoop = false;
        isAsking = false;
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
        out.println(modelRepresentation.getDraftPool().toString());
    }

    public void setAsking(){
        isAsking = true;
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

    /**
     * Thread used to periodically check if InputThread must be woke up
     */
    private class AskingThread extends Thread{
        private Timer timer;
        private TimerTask timerTask;

        AskingThread(){
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if(!isAsking && inputThread.getState() == State.TIMED_WAITING){
                        inputLoop = true;
                        synchronized (lock) {
                            lock.notifyAll();
                        }
                    }
                }
            };
        }

        @Override
        public void run(){
            timer.scheduleAtFixedRate(timerTask, 5000, 5000);
        }
    }



}



