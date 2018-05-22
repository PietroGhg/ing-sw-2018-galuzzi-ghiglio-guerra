package it.polimi.se2018.controller;

public class VCToolMessage extends VCAbstractMessage {
    private int toolCardID;

    public int getToolCardID(){ return toolCardID; }
    public void accept(Controller controller){
        controller.visit(this);
    }
}
