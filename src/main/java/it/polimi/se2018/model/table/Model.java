package it.polimi.se2018.model.table;

import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.messages.MVGameMessage;
import it.polimi.se2018.utils.messages.MVMessage;
import it.polimi.se2018.utils.messages.VCGameMessage;

import java.util.ArrayList;

public class Model extends Observable<MVGameMessage> {
    private ArrayList<Die> draftPool;
    private ArrayList<Player> players;
    private ArrayList<PublicObjectiveCard> puCards;
    private DiceBag diceBag;
    private ChooseWinner chooseWinner;
    private Turn turn;
    private RoundTrack roundTrack;
    private PlayerMoveParameters playerMoveParameters;


    public void nextTurn(){
        try {
            roundTrack.nextTurn();
            turn = new Turn();
            //TO DO: notify the players
        }
        catch (GameEndedException e){
            chooseWinner();
        }
    }

    private void chooseWinner(){
        chooseWinner = new ChooseWinner(players, puCards, roundTrack);
        try {
            chooseWinner.getWinner();
            //notify the winner
        }
        catch (NoWinnerException e){
            e.printStackTrace();
            //notify users that something went a donnacce
        }
    }

    /**
     * sets up a MVMessage contaning all the useful datas and notifies it to the view
     * @param m the message that has to be notified to the view
     */
    public void setMessage(String m){
        MVGameMessage message = new MVGameMessage(m);
        //chiamate ai vari metodi toString e creazione messaggio
        notify(message);
    }

    public void setParameters(VCGameMessage m){
        playerMoveParameters = new PlayerMoveParameters(players.get(m.getPlayerID()),
                                                            m.getParameters());
    }

    public PlayerMoveParameters getParameters() {
        return playerMoveParameters;
    }

    public int whoIsPlaying(){
        return roundTrack.whoIsPlaying();
    }
}
