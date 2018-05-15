package it.polimi.se2018.Model;

public class Turn {
    private boolean playedCard;
    private boolean playedDie;

    public void cardPlayed(){
        playedCard = true;
    }

    public void diePlayed() {
        playedDie = true;
    }

    public boolean isPlayedCard() {
        return playedCard;
    }

    public boolean isPlayedDie() {
        return playedDie;
    }

    public Turn(){
        playedCard = false;
        playedDie = false;
    }
}
