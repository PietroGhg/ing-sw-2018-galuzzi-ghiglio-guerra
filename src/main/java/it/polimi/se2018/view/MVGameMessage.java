package it.polimi.se2018.view;


import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.table.DiceBag;
import it.polimi.se2018.model.wpc.WPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MVGameMessage extends MVAbstractMessage {
    private String message;
    private String roundTrack;
    private List<Die> draftPool;
    private Map<Integer, WPC> wpcs;
    private DiceBag diceBag;

    public MVGameMessage(String message, int playerID){
        this.message = message;
        this.playerID = playerID;
        wpcs = new HashMap<>();
    }

    public void accept(AbstractView view){ view.visit(this); }

    public String getMessage() {
        return message;
    }

    public String getRoundTrack() {
        return roundTrack;
    }

    public List<Die> getDraftPool() {
        return draftPool;
    }

    public WPC getWpc(int playerID){
        return wpcs.get(playerID);
    }

    public void setWpc(int playerID, WPC wpc){
        wpcs.put(playerID, wpc);
    }

    public void setDraftPool(List<Die> draftPool){
        this.draftPool = draftPool;
    }

    public void setRoundTrack(String roundTrack){
        this.roundTrack = roundTrack;
    }

    public Map<Integer, WPC> getWpcs() { return wpcs; }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }
}
