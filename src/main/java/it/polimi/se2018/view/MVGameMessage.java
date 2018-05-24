package it.polimi.se2018.view;


import java.util.HashMap;
import java.util.Map;

public class MVGameMessage extends MVAbstractMessage {
    private String message;
    private String roundTrack;
    private String draftPool;
    private Map<Integer, String> wpcs;

    public MVGameMessage(String message, int playerID){
        this.message = message;
        this.playerID = playerID;
        wpcs = new HashMap<Integer, String>();
    }

    public void accept(AbstractView view){ view.visit(this); }

    public String getMessage() {
        return message;
    }

    public String getRoundTrack() {
        return roundTrack;
    }

    public String getDraftPool() {
        return draftPool;
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
