package it.polimi.se2018.controller;

public class VCDieMessage extends VCAbstractMessage {
    public void accept(Controller c){
        c.visit(this);
    }
}
