package it.polimi.se2018.utils.messages;

public class MVExtractedCardsMessage {
    private int playerID;
    private String prCard;
    private String[] puCards;

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
