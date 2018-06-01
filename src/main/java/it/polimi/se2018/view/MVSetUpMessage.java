package it.polimi.se2018.view;

/**
 * Contains the strings representing the extracted boards and an array containing the IDs of the boards.
 * @author Pietro Ghiglio
 */
public class MVSetUpMessage extends MVAbstractMessage {
    private int[] wpcIDs;
    private String prCard;
    private String[] puCards;

    public MVSetUpMessage(int playerID, int[] indexes, String prCard, String[] puCards){
        this.playerID = playerID;
        this.wpcIDs = indexes;
        this.prCard = prCard;
        this.puCards = puCards;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public int[] getIDs(){ return wpcIDs; }

    public String getPrCard() {
        return prCard;
    }

    public String[] getPuCards() {
        return puCards;
    }

}
