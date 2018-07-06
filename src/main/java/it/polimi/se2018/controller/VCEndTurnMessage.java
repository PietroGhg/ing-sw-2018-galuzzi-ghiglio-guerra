package it.polimi.se2018.controller;

/**
 * Class for the messages from the View to the Controller, signals that a player chose to end his turn
 */
public class VCEndTurnMessage extends VCAbstractMessage {
    public VCEndTurnMessage(int playerID){
        super(playerID);
    }
    public void accept(Controller c){
        c.visit(this);
    }
}
