package it.polimi.se2018.model.table;

import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.messages.MVGameMessage;
import it.polimi.se2018.utils.messages.MVMessage;
import it.polimi.se2018.utils.messages.MVSetUpMessage;
import it.polimi.se2018.utils.messages.VCGameMessage;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<MVGameMessage> {

    //essendo un ArrayList, per il get di un dado basta il metodo get(index)
    // e per settare un dado basta draftPool.add(index,die)
    private ArrayList<Die> draftPool;
    public ArrayList<Die> getDraftPool() { return draftPool; }
    public void setDraftPool(ArrayList<Die> draftPool) { this.draftPool = draftPool;}

    private RoundTrack roundTrack;
    
    private ArrayList<Player> players;
    private ArrayList<PublicObjectiveCard> puCards;
    private DiceBag diceBag;
    private ChooseWinner chooseWinner;
    private Turn turn;
    private PlayerMoveParameters playerMoveParameters;

    public Model(){
        players = new ArrayList<>();
        puCards = new ArrayList<>();
        diceBag = new DiceBag();
        // chooseWinner = new ChooseWinner(); ??
        turn = new Turn();
        // playerMoveParameters = new PlayerMoveParameters(); ??
    }

    public void nextTurn(){
        try {
            roundTrack.nextTurn();
            turn.clear();
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
     * sets up a MVMessage containing all the useful datas and notifies it to the view
     * @param m the message that has to be notified to the view
     */
    public void setMessage(String m, int playerID){
        MVGameMessage message = new MVGameMessage(m, playerID);

        //set up wpcs
        for(Player p: players){
            message.setWpc(p.getPlayerID(), p.getWpc().toString());
        }
        //set up draftpool
        message.setDraftPool(getDraftPoolToString());
        //set up roundtrack
        message.setRoundTrack(roundTrack.toString());

        notify(message);
    }

    public void setParameters(VCGameMessage m){
        playerMoveParameters = new PlayerMoveParameters(m.getPlayerID(),
                                                            m.getParameters());
    }

    public void setParameters(PlayerMoveParameters param){
        playerMoveParameters = param;
    }

    public PlayerMoveParameters getParameters() {
        return playerMoveParameters;
    }

    /**
     * @return The ID of the current player
     */
    public int whoIsPlaying(){
        return roundTrack.whoIsPlaying();
    }

    public Player getPlayer(int playerID){
        return players.get(playerID - 1);
    }

    private String getDraftPoolToString(){
        StringBuilder builder = new StringBuilder();
        Die temp;
        for(int i = 0; i< draftPool.size(); i++){
            temp = draftPool.get(i);
            builder.append(Colour.RESET + temp.getDieColour().escape() + temp.getDieValue() +"\t" +Colour.RESET);
        }
        return builder.toString();
    }

    /**
     * @return true if a card has already been played in the turn
     */
    public boolean cardHasBeenPlayed(){
        return turn.cardHasBeenPlayed();
    }

    /**
     * @return true if a die has already been played in a turn
     */
    public boolean dieHasBeenPlayed(){
        return turn.dieHasBeenPlayed();
    }

    public void addPlayer(int playerID){
        Player p = new Player(playerID);
        players.add(p);
    }

    public void addPlayer(Player p){
        players.add(p);
    }

    public void addPlayer(String name) {
        Player p = new Player(name, players.size() + 1 );
        players.add(p);
        //setSetupMessage(players.size(),  extractWpcs());
    }

    private void setSetupMessage(int playerID, String[] wpcs){
        MVSetUpMessage message = new MVSetUpMessage(playerID, wpcs);
        //notify(message);
    }
}
