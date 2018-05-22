package it.polimi.se2018.controller;

public class VCEndTurnMessage extends VCAbstractMessage {
    public void accept(Controller c){
        c.visit(this);
    }
}
