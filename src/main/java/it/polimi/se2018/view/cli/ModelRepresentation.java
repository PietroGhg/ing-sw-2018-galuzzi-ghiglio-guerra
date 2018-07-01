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
    private String prCard;
    private String[] puCards;
    private List<String> toolCards;
    private Map<Integer, WPC> wpcs;
    private Map<Integer, WPC> selected;
    private DiceBag diceBag;
    private int currPlayer;

    public ModelRepresentation(){
        wpcs = new HashMap<>();
    }

    public String getRoundTrack() {
        return roundTrack;
    }

    public List<Die> getDraftPool() {
        return draftPool;
    }

    public Die getDieFromDraft(int i) { return draftPool.get(i); }

    public Map<Integer, WPC> getWpcs() {
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

    public void setPrCard(String prCard) { this.prCard = prCard; }

    public void setPuCards(String[] puCards) { this.puCards = puCards; }

    public String[] getPuCards(){ return puCards; }

    public String getPrCard(){return prCard; }

    public List<String> getToolCards(){ return toolCards; }

    public void setToolCards(List<String> toolCards) { this.toolCards = toolCards; }

    public int getNumPlayers(){ return wpcs.size(); }

    public int getRandomIndex(){
        return diceBag.getRandomIndex();
    }

    public Die getDieFromDBag(int index) { return diceBag.getDie(index); }

    public void setDiceBag(DiceBag diceBag){ this.diceBag = diceBag; }

    public void setCurrPlayer(int currPlayer){ this.currPlayer = currPlayer; }

    public int getCurrPlayer(){ return currPlayer; }

    public Map<Integer, WPC> getSelected() {
        return selected;
    }

    public void setSelected(Map<Integer, WPC> selected) {
        this.selected = selected;
    }
}
