package it.polimi.se2018.model;

import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;
import java.util.List;

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

    public void setWpc(WPC wpc) {
        this.favorTokens = wpc.getFavorTokens();
        this.wpc = wpc;
    }

    public WPC getWpc() { return wpc; }

    public void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void setWpcOnly(WPC wpc){ this.wpc = wpc; }

    public void addPrCard(PrivateObjectiveCard card){
        prCard = card;
    }

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