package it.polimi.se2018.controller.messages;

public class MVSetUpMessage implements MVMessage {
    private  int playerID;
    private String[] wpcs;

    public MVSetUpMessage(int playerID, String[] wpcs){
        this.playerID = playerID;
        this.wpcs = wpcs;
    }

    public int getPlayerID(){
        return playerID;
    }

    public String[] getWpcs(){
        return wpcs;
    }

}
