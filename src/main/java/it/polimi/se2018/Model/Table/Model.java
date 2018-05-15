package it.polimi.se2018.Model.Table;

import it.polimi.se2018.Exceptions.GameEndedException;
import it.polimi.se2018.Exceptions.NoWinnerException;
import it.polimi.se2018.Model.*;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.PublicObjectiveCard;

import java.util.ArrayList;

public class Model /*implements Observable*/ {
    private ArrayList<Die> draftPool;
    private ArrayList<Player> players;
    private ArrayList<PublicObjectiveCard> puCards;
    private DiceBag diceBag;
    private ChooseWinner chooseWinner;
    private Turn turn;
    private RoundTrack roundTrack;


    public void nextTurn(){
        try {
            roundTrack.nextTurn();
            turn = new Turn();
        }
        catch (GameEndedException e){
            chooseWinner();
        }
    }

    private void chooseWinner(){
        chooseWinner = new ChooseWinner(players, puCards, roundTrack);
        try {
            chooseWinner.getWinner();
        }
        catch (NoWinnerException e){
            e.printStackTrace();
        }
    }
}
