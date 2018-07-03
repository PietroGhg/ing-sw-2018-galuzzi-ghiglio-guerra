package it.polimi.se2018.controller;


import it.polimi.se2018.controller.states.*;
import it.polimi.se2018.controller.toolcard.ToolCard;
import it.polimi.se2018.controller.toolcard.ToolCardFactory;
import it.polimi.se2018.controller.turntimer.TurnFacade;
import it.polimi.se2018.controller.turntimer.TurnTimer;
import it.polimi.se2018.exceptions.*;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.utils.Observer;

import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for the controller
 */
public class Controller implements Observer<VCAbstractMessage> {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private Model model;
    private Timer timer;
    private TurnTimer turnTimer;
    private ConnectionTimer connectionTimer;
    private int timerDuration;
    private ToolCardFactory toolCardFactory;
    private State state;

    public Controller(Model model, int timerDuration, int turnDuration){
        this.model = model;
        turnTimer = new TurnTimer(turnDuration, new TurnFacade(model));
        timer = new Timer();
        state = new ConnectionState();
        this.timerDuration = timerDuration;
        toolCardFactory = new ToolCardFactory();

        connectionTimer = new ConnectionTimer(this);
    }

    public void startGame() {
        state = new BoardSelectionState();
        LOGGER.log(Level.INFO, "Game starting.");
        timer.cancel(); //cancels the connection timer
        turnTimer.reset(); //starts the turn timer
        model.startGame();
    }


    public synchronized void update(VCAbstractMessage message){
        message.accept(this);
    }

    /*package private*/ void visit(VCToolMessage message){
        int toolcardID = message.getToolCardID();
        int playerID = message.getPlayerID();
        try {
            checkTurn(message); //throws exception if it's not the player's turn
            ToolCard toolCard = toolCardFactory.get(toolcardID);
            model.isInUse(toolCard.getName()); //throws exception if the toolcard is not in use
            if(model.cardHasBeenPlayed()) throw new MoveNotAllowedException("Error: a tool card has already been used in the turn.");
            model.setParameters(message);

            toolCard.cardAction(model.getParameters()); //throws exception based on the cardAction
            model.setToolCardUsed();
            model.setGameMessage("Success.", playerID);

        }
        catch (MoveNotAllowedException|InputNotValidException e) {
            model.setGameMessage(e.getMessage(), playerID);
        }
    }

    /*package private*/ void visit(VCDieMessage message){
        int playerID = message.getPlayerID();
        try {
            checkTurn(message);
            if(model.dieHasBeenPlayed()) throw new MoveNotAllowedException("Error: a die has already been placed in the turn.");
            model.setParameters(message);
            dieMove(model.getParameters());
            model.setDiePlaced();
            model.setGameMessage("Success.", playerID);
        }
        catch (MoveNotAllowedException e) {
            model.setGameMessage(e.getMessage(), playerID);
        }
    }

    /*package private*/ void visit(VCEndTurnMessage message){
        try{
            checkTurn(message);
            model.nextTurn(turnTimer);
        }
        catch (MoveNotAllowedException e){
            model.setGameMessage(e.getMessage(), message.getPlayerID());
        }
    }

    /*package private*/ void visit(VCSetUpMessage message){
        int playerID = message.getPlayerID();
        String s = playerID + " ready.";
        LOGGER.log(Level.INFO, s);
        model.setWpc(playerID, message.getChosenWpc());
        model.setReady(playerID);
        checkAllReady();
    }

    /**
     * Method that checks if all the players have chosen a board, a game can start
     */
    public void checkAllReady(){
        if(model.allReady()){
            LOGGER.log(Level.INFO, "All ready.");
            state = new GameplayState();
            model.setStartGameMessage("Game start", model.whoIsPlaying());
        }
    }

    /**
     * Usual die moving
     * @param parameters the move's parameters
     * @throws MoveNotAllowedException if any of the restrictions is violated
     * @author Leonardo Guerra
     */
    private void dieMove(PlayerMoveParameters parameters)throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = parameters.getPlayer();

        WPC wpc = player.getWpc();
        List<Die> dp = parameters.getDraftPool();
        int dpIndex = parameters.getParameter(0); //DraftPool index for the chosen die
        int cellRow = parameters.getParameter(1); //Row of the recipient cell
        int cellCol = parameters.getParameter(2); //Column of the recipient cell

        //Checks for the DraftPool (non empty, chosen cell not empty)
        rc.checkDPNotEmpty(dp);
        rc.checkDPCellNotEmpty(dp, dpIndex);

        //Copy the die to move
        Die toMove = new Die(dp.get(dpIndex));

        //Other restrictions check
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkFirstMove(wpc, cellRow, cellCol);
        rc.checkColourRestriction(wpc,cellRow,cellCol,toMove);
        rc.checkValueRestriction(wpc,cellRow,cellCol,toMove);
        rc.checkAdjacent(wpc, cellRow, cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,toMove);

        //Set the die in the board
        wpc.setDie(cellRow,cellCol,toMove);

        //Remove the moved die from the DraftPool
        dp.remove(dpIndex);

        }

    /**
     * Checks that the move message comes from the current player
     * @param message a VCAbstractMessage
     * @throws MoveNotAllowedException if the message is not from the current player
     */
    private void checkTurn(VCAbstractMessage message) throws MoveNotAllowedException{
        if(model.whoIsPlaying() != message.getPlayerID()) throw new MoveNotAllowedException("Error: not your turn");
    }

    /**
     * Method called by the server in order to check if there are enough players to start a game
     */
    public void checkEnoughPlayers() {
        state = state.checkEnoughPlayers(new ModelFacade(model, timerDuration), this, timer, connectionTimer);
    }

    public void handleRequest(String playerName) throws GameStartedException, UserNameTakenException, ReconnectionException {
        state.handleRequest(playerName, new ModelFacade(model, timerDuration));
    }

    public void handleDisconnection(String playerName) {
        ModelFacade mf = new ModelFacade(model, timerDuration);
        mf.setTurnTimer(turnTimer);
        state.handleDisconnection(playerName,this, mf,timer, connectionTimer);
    }


    public void welcomeBack(String playerName) {
        try{
            Player p = model.getPlayer(playerName);
            model.setWelcomeBackMessage(p.getPlayerID(), p.getName(),p.getName()+" rejoined");
        }
        catch (UserNameNotFoundException e){
            LOGGER.log(Level.SEVERE, "Error while handling player reconnection");
        }
    }
}
