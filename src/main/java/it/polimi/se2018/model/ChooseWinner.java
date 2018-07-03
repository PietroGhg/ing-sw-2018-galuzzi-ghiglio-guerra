package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.table.RoundTrack;

import java.util.List;

/**
 * Chooses the winner: checks total score, private score, favor tokens and position in the last turn
 * @author Pietro Ghiglio
 */
public class ChooseWinner {
    private List<Player> possibleW;
    private List<PublicObjectiveCard> puCard;
    private RoundTrack roundTrack;

    public ChooseWinner(List<Player> possibleW, List<PublicObjectiveCard> puCard, RoundTrack r) {
        this.possibleW = possibleW;
        this.puCard = puCard;
        this.roundTrack = r;
    }

    public Player getWinner() throws NoWinnerException{
        calcTotScore();
        if(possibleW.size() == 1) return possibleW.get(0);
        calcPrScore();
        if(possibleW.size() == 1) return possibleW.get(0);
        calcFTokens();
        if(possibleW.size() == 1) return possibleW.get(0);
        return calcRound();
    }

    /**
     * Selects the players with the highest total score:
     * first finds the maximun score, then removes all the players with a score lesser that the maximun
     */
    private void calcTotScore(){
        int i = 0;
        int maxScore = possibleW.get(0).getTotalScore(puCard);

        while(i < possibleW.size()){
            int score = possibleW.get(i).getTotalScore(puCard);
            if(score > maxScore) maxScore = score;
            i++;
        }

        i = 0;
        while(i < possibleW.size()){
            if(possibleW.get(i).getTotalScore(puCard) < maxScore) possibleW.remove(i);
            else i++;
        }
    }

    private void calcPrScore() {
        int i = 0;
        int maxScore = possibleW.get(0).getPrivateScore();

        while(i < possibleW.size()){
            int score = possibleW.get(i).getPrivateScore();
            if(score > maxScore) maxScore = score;
            i++;
        }

        i = 0;
        while(i < possibleW.size()){
            if(possibleW.get(i).getPrivateScore() < maxScore) possibleW.remove(i);
            else i++;
        }
    }

    private void calcFTokens(){
        int i = 0;
        int maxScore = possibleW.get(0).getFavorTokens();

        while(i < possibleW.size()){
            int score = possibleW.get(i).getFavorTokens();
            if(score > maxScore) maxScore = score;
            i++;
        }

        i = 0;
        while(i < possibleW.size()) {
            if(possibleW.get(i).getFavorTokens() < maxScore) possibleW.remove(i);
            else i++;
        }
    }

    /**
     * Finds the player with the first position in the last round
     * @return the only winner
     */
    private Player calcRound() throws NoWinnerException{
        int[] lastRound = roundTrack.getLastRound();
        for(int i = 0; i < lastRound.length; i++){
            for(Player p : possibleW){
                if(p.getPlayerID() == lastRound[i]) return p;
            }
        }
        throw new NoWinnerException();
    }


}
