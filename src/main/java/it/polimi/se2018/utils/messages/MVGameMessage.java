package it.polimi.se2018.utils.messages;


import java.util.HashMap;
import java.util.Map;

public class MVGameMessage implements MVMessage{
    private String message;
    private String roundTrack;
    private String draftPool;
    private String prCard;
    private int receiver; //playerID of the receiver of the message
    private Map<Integer, String> wpcs;
    private String[] puCards;

    public MVGameMessage(String message, int playerID){
        this.message = message;
        receiver = playerID;
        wpcs = new HashMap<Integer, String>();
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

    public void setWpc(int playerID, String wpc){
        wpcs.put(playerID, wpc);
    }

    public void setDraftPool(String draftPool){
        this.draftPool = draftPool;
    }

    public void setRoundTrack(String roundTrack){
        this.roundTrack = roundTrack;
    }
}
