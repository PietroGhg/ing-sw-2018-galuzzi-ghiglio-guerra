package it.polimi.se2018.controller;

/**
 * Class for the messages from the View to the Controller, signals that a player performed a dice placement
 */
public class VCDieMessage extends VCAbstractMessage {
    public VCDieMessage(int playerID){
        super(playerID);
    }
    public void accept(Controller c){
        c.visit(this);
    }
}
