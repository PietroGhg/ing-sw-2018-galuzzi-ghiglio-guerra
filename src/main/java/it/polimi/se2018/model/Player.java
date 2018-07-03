package it.polimi.se2018.model;

import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;
import java.util.List;

/**
 * Class for the player
 */
public class Player {
    private int playerID;
    private String name;
    private int favorTokens;
    private boolean disconnected;
    private boolean skipTurn;
    private WPC wpc;
    private boolean ready;

    private PrivateObjectiveCard prCard;

    public Player(int id){
        ready = false;
        skipTurn = false;
        disconnected = false;
        playerID = id;
    }

    public Player (int id, String name){
        ready = false;
        playerID = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Player(String name, int playerID){
        this.name = name;
        this.playerID = playerID;
    }

    /**
     * Sets board and favor tokens
     * @param wpc window pattern card to set
     */
    public void setWpc(WPC wpc) {
        this.favorTokens = wpc.getFavorTokens();
        this.wpc = wpc;
    }

    public WPC getWpc() { return wpc; }

    public void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    /**
     * Only sets the board
     * @param wpc window pattern card to set
     */
    public void setWpcOnly(WPC wpc){ this.wpc = wpc; }

    /**
     * Adds the player's private objective card for the game
     * @param card the private objective card to set
     */
    public void addPrCard(PrivateObjectiveCard card){
        prCard = card;
    }

    /**
     * @return player's private objective card
     */
    public PrivateObjectiveCard getPrCard() { return prCard; }

    public int getPrivateScore(){
        return prCard.getScore(wpc);
    }

    public int getTotalScore(List<PublicObjectiveCard> puCards){
        int ris = 0;
        for(PublicObjectiveCard c : puCards){
            ris = ris + c.getScore(wpc);
        }
        return ris + getPrivateScore() - numEmptyCells() + favorTokens;
    }

    /**
     * @return the number of empty cells in the player's board
     */
    private int numEmptyCells(){
        int tot = 0;
        for(int i = 0; i < WPC.NUMROW; i++){
            for(int j = 0; j < WPC.NUMROW; j++){
                if(wpc.getCell(i, j).isEmpty()) tot++;
            }
        }
        return tot;
    }

    public int getFavorTokens() {
        return favorTokens;
    }

    public int getPlayerID() {
        return playerID;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady() {
        this.ready = true;
    }

    public boolean isDisconnected(){ return disconnected; }

    public void setDisconnected(boolean disconnected){
        this.disconnected = disconnected;
    }

    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }

    public boolean getSkipTurn() {
        return skipTurn;
    }
}