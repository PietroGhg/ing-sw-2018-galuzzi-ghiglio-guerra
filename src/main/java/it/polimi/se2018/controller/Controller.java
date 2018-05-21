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
import it.polimi.se2018.utils.messages.*;



public class Controller implements Observer<VCGameMessage> {
    private Model model;
    private ToolCardFactory toolCardFactory;

    public Controller(Model model){
        this.model = model;
        toolCardFactory = new ToolCardFactory();
    }

    /**
     * checks that the player is trying to make a move in his turn
     * stores the parameters in the model and applies the move
     * if an exception is thrown while trying to apply the move, notifies the error message to the view
     * by using the observer-relation between model and view
     * @param message the VCMessage coming from the view
     */
    public void update(VCGameMessage message){
        //Checks that it's the right turn
        if(model.whoIsPlaying() != message.getPlayerID()){
            model.setMessage("Error: not your turn", message.getPlayerID());
            return;
        }
        //Sets up parameters
        model.setParameters(message);

        if(message.isToolCardMove()) {
            int toolcardID = message.getToolcardID();
            try {
                if(model.cardHasBeenPlayed()) throw new MoveNotAllowedException("Error: a tool card has already been used in the turn.");
                toolCardFactory.get(toolcardID).cardAction(model);
                model.setMessage("Success.", message.getPlayerID());
            } catch (MoveNotAllowedException|InputNotValidException e) {
                model.setMessage(e.getMessage(), message.getPlayerID());
            }
        }

        if(message.isDiceMove()) {
            try {
                if(model.dieHasBeenPlayed()) throw new MoveNotAllowedException("Error: a die has already been placed in the turn.");
                diceMove(model.getParameters());
                model.setMessage("Success.", message.getPlayerID());
            }
            catch (MoveNotAllowedException e) {
                model.setMessage(e.getMessage(), message.getPlayerID());
            }
        }

        if(message.isEndTurn()){
            model.nextTurn();
        }
    }

    /*
        0: draftpool index
        1: die row
        2: die col
        3: cell row
        4: cell col
    */
    private void diceMove(PlayerMoveParameters parameters)throws MoveNotAllowedException{
        Player p = model.getPlayer(parameters.getPlayerID());
        WPC temp = new WPC(p.getWpc());
        int dieIndex = parameters.getParameter(0);


        //checks restrizioni

        //Die d = model.chooseDieFromDraft(dieIndex);

    }
}
