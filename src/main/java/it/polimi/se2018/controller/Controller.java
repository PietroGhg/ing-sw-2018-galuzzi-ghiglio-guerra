package it.polimi.se2018.controller;


import it.polimi.se2018.controller.toolcard.ToolCardFactory;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.Observer;

import java.util.ArrayList;


public class Controller implements Observer<VCAbstractMessage> {
    private Model model;
    private ToolCardFactory toolCardFactory;

    public Controller(Model model){
        this.model = model;
        toolCardFactory = new ToolCardFactory();
    }


    public synchronized void update(VCAbstractMessage message){
        message.accept(this);
    }

    /*package private*/ void visit(VCToolMessage message){
        int toolcardID = message.getToolCardID();
        int playerID = message.getPlayerID();
        try {
            checkTurn(message);
            if(model.cardHasBeenPlayed()) throw new MoveNotAllowedException("Error: a tool card has already been used in the turn.");
            model.setParameters(message);
            toolCardFactory.get(toolcardID).cardAction(model.getParameters());
            model.setToolCardUsed();
            model.setGameMessage("Success.", playerID);
        }
        catch (MoveNotAllowedException|InputNotValidException e) {
            model.setGameMessage(e.getMessage(), playerID);
        }
        /*catch (ToolCard6Exception e){
            model.setTC6Message(message.getPlayerID());
        }
        catch (ToolCard11Exception e) {
            model.setTC11Exception(message.getPlayerID());
        }*/
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
            model.nextTurn();
        }
        catch (MoveNotAllowedException e){
            model.setGameMessage(e.getMessage(), message.getPlayerID());
        }
    }

    /*package private*/ void visit(VCSetUpMessage message){
        int playerID = message.getPlayerID();
        model.setWpc(playerID, message.getChosenWpc());
        model.setReady(playerID);

        //if all the players have chosen a board, a game can start
        if(model.allReady()){
            System.out.println("All ready.");
            model.setStartGameMessage("Game start", model.whoIsPlaying());
        }
    }

    /**
     * Usual die moving
     * @param parameters
     * @throws MoveNotAllowedException if any of the restrictions is violated
     * @author Leonardo Guerra
     */
    private void dieMove(PlayerMoveParameters parameters)throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = parameters.getPlayer();

        WPC wpc = player.getWpc();
        ArrayList<Die> dp = parameters.getDraftPool();
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
}
