package it.polimi.se2018.view;


import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.table.DiceBag;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class for the messages from the Model to the View, concerning the proper game play
 */
public class MVGameMessage extends MVAbstractMessage {
    private String message;
    private List<List<Die>> roundTrack;
    private List<Die> draftPool;
    private Map<Integer, WPC> wpcs;
    private DiceBag diceBag;
    private int currPlayer;

    public MVGameMessage(String message, int playerID){
        this.message = message;
        this.playerID = playerID;
        wpcs = new HashMap<>();
    }

    public void accept(AbstractView view){ view.visit(this); }
    public void accept(GUIcontroller gc){
        MVGameMessage m = this;
        Platform.runLater(() -> gc.visit(m));
    }

    public String getMessage() {
        return message;
    }

    public List<List<Die>> getRoundTrack() {
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

    public void setRoundTrack(List<List<Die>> roundTrack){
        this.roundTrack = roundTrack;
    }

    public Map<Integer, WPC> getWpcs() { return wpcs; }

    public DiceBag getDiceBag() {
        return diceBag;
    }

    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    public int getCurrPlayer(){ return currPlayer; }

    public void setCurrPlayer(int currPlayer){ this.currPlayer = currPlayer; }
}
