package it.polimi.se2018.view.cli;

import java.util.HashMap;
import java.util.Map;

/*Representation of Model in the View
 *@author Andrea Galuzzi
 */


public class ModelRepresentation {
    private String message;
    private String roundTrack;
    private String draftPool;
    private String prCards;
    private String[] puCards;
    private String toolCards;
    private Map<Integer, String> wpcs;

    public ModelRepresentation(){
        wpcs = new HashMap<>();
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

    public Map<Integer, String> getWpcs() {
        return wpcs;
    }

    public String getWpcs(int playerID) {return wpcs.get(playerID); }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRoundTrack(String roundTrack) {
        this.roundTrack = roundTrack;
    }

    public void setDraftPool(String draftPool) {
        this.draftPool = draftPool;
    }

    public void setWpcs(int playerID, String wpc) {
        wpcs.put(playerID, wpc);
        System.out.println(playerID + "\n"+ wpc);
    }

    public void setWpcs(Map<Integer, String> wpcs) {
        this.wpcs = wpcs;
    }

    public void setPrCards(String prCards) { this.prCards = prCards; }

    public void setPuCards(String[] puCards) { this.puCards = puCards; }

    public String[] getPuCards(){ return puCards; }

    public String getPrCards(){return prCards; }

    public String getToolCards(){ return toolCards; }

    public int getNumPlayers(){ return wpcs.size(); }

}
