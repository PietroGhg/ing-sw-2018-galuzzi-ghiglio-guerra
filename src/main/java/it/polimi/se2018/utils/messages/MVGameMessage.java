package it.polimi.se2018.utils.messages;


import java.util.Map;

public class MVGameMessage implements MVMessage{
    private String message;
    private String roundTrack;
    private String draftPool;
    private String prCard;
    private Map<Integer, String> wpcs;
    private String[] puCards;

    public MVGameMessage(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getRoundTrack() {
        return roundTrack;
    }

    public String getDraftPool() {
        return draftPool;
    }

    private String getPrCard(){
        return prCard;
    }

    public String getWpc(int playerID){
        return wpcs.get(playerID);
    }
}
