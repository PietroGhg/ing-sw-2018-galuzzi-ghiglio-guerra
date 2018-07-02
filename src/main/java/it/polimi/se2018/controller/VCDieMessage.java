package it.polimi.se2018.controller;

/**
 * Class for the messages from the View to the Controller, concerning dice
 */
public class VCDieMessage extends VCAbstractMessage {
    public VCDieMessage(int playerID){
        super(playerID);
    }
    public void accept(Controller c){
        c.visit(this);
    }
}
