package it.polimi.se2018.controller;

/**
 * Class for the messages from the View to the Controller, contains the id of the chosen window pattern card
 */
public class VCSetUpMessage extends VCAbstractMessage {
    private int chosenWpc;

    public VCSetUpMessage(int playerID, int chosenWpc){
        super(playerID);
        this.chosenWpc = chosenWpc;
    }
    public void accept(Controller c){ c.visit(this); }

    public int getChosenWpc() {
        return chosenWpc;
    }
}
