package it.polimi.se2018.controller.messages;

public class VCSetUpMessage implements VCMessage {
    private int playerID;
    private int wpcChosen;

    public VCSetUpMessage(int playerID, int wpcChosen){
        this.playerID = playerID;
        this.wpcChosen = wpcChosen;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getWpcChosen() {
        return wpcChosen;
    }
}
