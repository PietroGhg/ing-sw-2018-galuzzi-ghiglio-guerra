package it.polimi.se2018.view.cli;

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

    private Map<Integer, String> wpcs;

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
    }

    public void setWpcs(Map<Integer, String> wpcs) {
        this.wpcs = wpcs;
    }

    public void setPrCards(String prCards) { this.prCards = prCards; }

    public void setPuCards(String[] puCards) { this.puCards = puCards; }

}
