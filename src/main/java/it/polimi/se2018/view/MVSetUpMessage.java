package it.polimi.se2018.view;

import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

import java.util.List;
import java.util.Map;

/**
 * Contains the strings representing the extracted boards and an array containing the IDs of the boards.
 * @author Pietro Ghiglio
 */
public class MVSetUpMessage extends MVAbstractMessage {
    private Map<Integer,WPC> extracted;
    private String playerName;
    private String prCard;
    private String[] puCards;
    private List<String> tcInUse;

    public MVSetUpMessage(String playerName, int playerID, Map<Integer, WPC> extracted, String prCard, String[] puCards, List<String> tcInUse){
        this.playerName = playerName;
        this.playerID = playerID;
        this.extracted = extracted;
        this.prCard = prCard;
        this.puCards = puCards;
        this.tcInUse = tcInUse;
    }

    public void accept(AbstractView view){ view.visit(this); }
    public void accept(GUIcontroller gc){
        MVSetUpMessage m = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gc.visit(m);
            }
        });
    }

    public Map<Integer, WPC> getExtracted() {
        return extracted;
    }

    public String getPrCard() {
        return prCard;
    }

    public String[] getPuCards() {
        return puCards;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getTcInUse(){ return tcInUse; }
}
