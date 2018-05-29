package it.polimi.se2018.controller;

public class VCDieMessage extends VCAbstractMessage {
    public VCDieMessage(int playerID){
        super(playerID);
    }
    public void accept(Controller c){
        c.visit(this);
    }
}
