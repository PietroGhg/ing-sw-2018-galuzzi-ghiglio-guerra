package it.polimi.se2018.view;

import java.util.List;

/**
 * Contains the strings representing the extracted boards and an array containing the IDs of the boards.
 * @author Pietro Ghiglio
 */
public class MVSetUpMessage extends MVAbstractMessage {
    private int[] wpcIDs;
    private String playerName;
    private String prCard;
    private String[] puCards;
    private List<String> tcInUse;

    public MVSetUpMessage(String playerName, int playerID, int[] indexes, String prCard, String[] puCards, List<String> tcInUse){
        this.playerName = playerName;
        this.playerID = playerID;
        this.wpcIDs = indexes;
        this.prCard = prCard;
        this.puCards = puCards;
        this.tcInUse = tcInUse;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public int[] getIDs(){ return wpcIDs; }

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
