package it.polimi.se2018.view;

public class MVExtractedCardsMessage extends MVAbstractMessage {
    private String prCard;
    private String[] puCards;

    public MVExtractedCardsMessage(int playerID, String prCard, String[] puCards){
        this.playerID = playerID;
        this.prCard = prCard;
        this.puCards = puCards;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public int getPlayerID() {
        return playerID;
    }

    public String getPrCard() {
        return prCard;
    }

    public String[] getPuCards() {
        return puCards;
    }
}
