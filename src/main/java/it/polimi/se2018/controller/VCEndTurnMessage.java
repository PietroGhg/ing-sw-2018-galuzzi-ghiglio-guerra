package it.polimi.se2018.controller;

/**
 * Class for the messages from the View to the Controller, concerning turns
 */
public class VCEndTurnMessage extends VCAbstractMessage {
    public VCEndTurnMessage(int playerID){
        super(playerID);
    }
    public void accept(Controller c){
        c.visit(this);
    }
}
