package it.polimi.se2018.controller;


import it.polimi.se2018.controller.toolcard.ToolCardFactory;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.controller.messages.*;



public class Controller implements Observer<VCAbstractMessage> {
    private Model model;
    private ToolCardFactory toolCardFactory;

    public Controller(Model model){
        this.model = model;
        toolCardFactory = new ToolCardFactory();
    }


    public void update(VCAbstractMessage message){
        message.accept(this);
    }

    /*package private*/ void visit(VCToolMessage message){
        int toolcardID = message.getToolCardID();
        int playerID = message.getPlayerID();
        try {
            if(model.cardHasBeenPlayed()) throw new MoveNotAllowedException("Error: a tool card has already been used in the turn.");
            toolCardFactory.get(toolcardID).cardAction(model);
            model.setMessage("Success.", playerID);
        } catch (MoveNotAllowedException|InputNotValidException e) {
            model.setMessage(e.getMessage(), playerID);
        }
    }

    /*package private*/ void visit(VCDieMessage message){
        int playerID = message.getPlayerID();
        try {
            if(model.dieHasBeenPlayed()) throw new MoveNotAllowedException("Error: a die has already been placed in the turn.");
            dieMove(model.getParameters());
            model.setMessage("Success.", playerID);
        }
        catch (MoveNotAllowedException e) {
            model.setMessage(e.getMessage(), playerID);
        }
    }

    /*package private*/ void visit(VCEndTurnMessage message){
        model.nextTurn();
    }

    /*
        0: draftpool index
        1: die row
        2: die col
        3: cell row
        4: cell col
    */
    private void dieMove(PlayerMoveParameters parameters)throws MoveNotAllowedException{
        Player p = model.getPlayer(parameters.getPlayerID());
        WPC temp = new WPC(p.getWpc());
        int dieIndex = parameters.getParameter(0);


        //checks restrizioni

        //Die d = model.chooseDieFromDraft(dieIndex);

    }
}
