package it.polimi.se2018.model;

/**
 * Class for the game turn
 */
public class Turn {
    private boolean playedCard;
    private boolean playedDie;

    public void cardPlayed(){
        playedCard = true;
    }

    public void diePlayed() {
        playedDie = true;
    }

    public boolean cardHasBeenPlayed() {
        return playedCard;
    }

    public boolean dieHasBeenPlayed() {
        return playedDie;
    }

    public void clear(){
        playedCard = false;
        playedDie = false;
    }

    public Turn(){
        playedCard = false;
        playedDie = false;
    }
}
