package it.polimi.se2018.controller;


import it.polimi.se2018.controller.toolcard.ToolCardFactory;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
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
            model.setMessage("Error: not your turn");
            return;
        }
        model.setParameters(message);
        if(message.isToolCardMove()) {
            int toolcardID = message.getToolcardID();
            try {
                toolCardFactory.get(toolcardID).cardAction(model.getParameters());
                model.setMessage("Success.");
            } catch (MoveNotAllowedException|InputNotValidException e) {
                model.setMessage(e.getMessage());
            }
        }
        if(message.isDiceMove()) {
            try {
                diceMove(model.getParameters());
            }
            catch (MoveNotAllowedException e) {
                model.setMessage(e.getMessage());
            }
        }
        if(message.isEndTurn()){
            model.nextTurn();
        }
    }

    /*
        0: die row
        1: die col
        2: cell row
        3: cell col
    */
    private void diceMove(PlayerMoveParameters parameters)throws MoveNotAllowedException{}
}
