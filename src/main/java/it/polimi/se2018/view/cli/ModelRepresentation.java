package it.polimi.se2018.view.cli;

import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.table.DiceBag;
import it.polimi.se2018.model.wpc.WPC;

import java.util.*;

/*Representation of Model in the View
 *@author Andrea Galuzzi
 */


public class ModelRepresentation {
    private String roundTrack;
    private ArrayList<Die> draftPool;
    private String prCards;
    private String[] puCards;
    private String toolCards;
    private Map<Integer, WPC> wpcs;
    private DiceBag diceBag;

    public ModelRepresentation(){
        wpcs = new HashMap<>();
    }

    public String getRoundTrack() {
        return roundTrack;
    }

    public String getDraftPool() {
        return draftPool.toString();
    }

    public Die getDieFromDraft(int i) { return draftPool.get(i); }

    public Map<Integer, WPC> getWpc() {
        return wpcs;
    }

    public WPC getWpc(int playerID) {return wpcs.get(playerID); }

    public void setRoundTrack(String roundTrack) {
        this.roundTrack = roundTrack;
    }

    public void setDraftPool(List<Die> draftPool) {
        this.draftPool = (ArrayList<Die>)draftPool;
    }

    public void setWpcs(int playerID, WPC wpc) {
        wpcs.put(playerID, wpc);
    }

    public void setWpcs(Map<Integer, WPC> wpcs) {
        this.wpcs = wpcs;
    }

    public void setPrCards(String prCards) { this.prCards = prCards; }

    public void setPuCards(String[] puCards) { this.puCards = puCards; }

    public String[] getPuCards(){ return puCards; }

    public String getPrCards(){return prCards; }

    public String getToolCards(){ return toolCards; }

    public int getNumPlayers(){ return wpcs.size(); }

    public int getRandomIndex(){
        return diceBag.getRandomIndex();
    }

    public Die getDieFromDBag(int index) { return diceBag.getDie(index); }

    public void setDiceBag(DiceBag diceBag){ this.diceBag = diceBag; }

}
