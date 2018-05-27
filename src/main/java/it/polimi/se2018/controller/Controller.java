package it.polimi.se2018.controller;


import it.polimi.se2018.controller.toolcard.ToolCardFactory;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.Observer;



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
            model.setMessage("Success.", playerID);
        } catch (MoveNotAllowedException|InputNotValidException e) {
            model.setMessage(e.getMessage(), playerID);
        }
    }

    /*package private*/ void visit(VCDieMessage message){
        int playerID = message.getPlayerID();
        try {
            checkTurn(message);
            if(model.dieHasBeenPlayed()) throw new MoveNotAllowedException("Error: a die has already been placed in the turn.");
            model.setParameters(message);
            dieMove(model.getParameters());
            model.setMessage("Success.", playerID);
        }
        catch (MoveNotAllowedException e) {
            model.setMessage(e.getMessage(), playerID);
        }
    }

    /*package private*/ void visit(VCEndTurnMessage message){
        try{
            checkTurn(message);
            model.nextTurn();
        }
        catch (MoveNotAllowedException e){
            model.setMessage(e.getMessage(), message.getPlayerID());
        }
    }

    /*
        0: draftpool index
        1: die row
        2: die col
        3: cell row
        4: cell col
    */
    private void dieMove(PlayerMoveParameters parameters)throws MoveNotAllowedException{
        //Player p = model.getPlayer(parameters.getPlayerID());
        //WPC temp = new WPC(p.getWpc());
        //int dieIndex = parameters.getParameter(0);


        //checks restrizioni

        //Die d = model.chooseDieFromDraft(dieIndex);

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
