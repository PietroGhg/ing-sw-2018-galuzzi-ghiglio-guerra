package it.polimi.se2018.model.table;

import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;

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
            //notify users that something went a donnacce
        }
    }
}
