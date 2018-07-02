package it.polimi.se2018.controller;

/**
 * Class for the messages from the View to the Controller, concerning tool cards
 */
public class VCToolMessage extends VCAbstractMessage {
    private int toolCardID;
    public VCToolMessage(int playerID, int toolCardID){
        super(playerID);
        this.toolCardID = toolCardID;
    }

    public int getToolCardID(){ return toolCardID; }
    public void accept(Controller controller){
        controller.visit(this);
    }
}
