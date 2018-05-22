package it.polimi.se2018.model;

import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

public class Player {
    private int playerID;
    private String name;
    private int favorTokens;
    private int privateScore;
    private int totalScore;
    private WPC wpc;

    private ArrayList<PrivateObjectiveCard> prCard;

    public Player(int id){
        prCard = new ArrayList<>();
        playerID = id;
    }

    public Player(String name, int playerID){
        this.name = name;
        this.playerID = playerID;
    }

    public void setWpc(WPC wpc) {
        this.wpc = wpc;
    }

    public WPC getWpc() { return wpc; }

    public void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void addPrCard(PrivateObjectiveCard card){
        prCard.add(card);
    }

    public int getPrivateScore(){
        privateScore = 0;
        for(PrivateObjectiveCard c : prCard){
            privateScore = privateScore + c.getScore(wpc);
        }
        return privateScore;
    }

    public int getTotalScore(ArrayList<PublicObjectiveCard> puCards){
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
}