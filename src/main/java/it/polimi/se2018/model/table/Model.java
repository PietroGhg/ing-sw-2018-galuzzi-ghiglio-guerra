package it.polimi.se2018.model.table;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.states.States;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.MVExtractedCardsMessage;
import it.polimi.se2018.view.MVGameMessage;
import it.polimi.se2018.view.MVAbstractMessage;
import it.polimi.se2018.view.MVSetUpMessage;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<MVAbstractMessage> {

    //essendo un ArrayList, per il get di un dado basta il metodo get(index)
    // e per settare un dado basta draftPool.add(index,die)

    private ArrayList<Die> draftPool;
    public ArrayList<Die> getDraftPool() { return draftPool; }
    public void setDraftPool(ArrayList<Die> draftPool) { this.draftPool = draftPool;}

    private RoundTrack roundTrack;
    private ArrayList<Player> players;
    private ArrayList<PublicObjectiveCard> puCards;
    private DiceBag diceBag;
    private Die vacantDie; //eventually used for toolcards 6 and 11
    private ChooseWinner chooseWinner;
    private Turn turn;
    private PlayerMoveParameters playerMoveParameters;
    private States state;

    public Model(){
        players = new ArrayList<>();
        puCards = new ArrayList<>();
        draftPool = new ArrayList<>();
        roundTrack = new RoundTrack(getPlayersNumber());
        diceBag = new DiceBag();
        turn = new Turn();
        state = States.CONNECTION;
    }

    public Die getVacantDie(){ return vacantDie; }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack){ this.roundTrack.setRT(roundTrack); }

    public ArrayList<ArrayList<Die>> getRoundTrack(){ return roundTrack.getRT(); }

    public int[][] getRoundMatrix() { return roundTrack.getRoundMatrix(); }

    public int getPlayersNumber() { return players.size(); }

    public int turnNumber(int playerID){ return roundTrack.turnNumber(playerID); }

    public void nextTurn(){
        try {
            roundTrack.nextTurn(draftPool);
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

    public void setParameters(VCAbstractMessage m){
        playerMoveParameters = new PlayerMoveParameters(m.getPlayerID(),
                                                            m.getParameters(), this);
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

    public void addPlayer() throws GameStartedException{
        Extractor extractor = Extractor.getInstance();
        if(state != States.CONNECTION) throw new GameStartedException();

        Player p = new Player(players.size());
        players.add(p);
        int[] wpcsExtracted = extractor.extractWpcs(p);
        setSetupMessage(p.getPlayerID(), wpcsExtracted);

        if(players.size() == 4) startGame();
    }

    /**
     * Method that starts a game, setting the GAMEPLAY state, extracting public, private a tool cards and notifying the
     * players.
     */
    public void startGame() {
        state = States.GAMEPLAY;
        //for each player, extract the cards and send an ExtractedCardMessage
        Extractor extractor = Extractor.getInstance();
        for(Player p: players){
            String prCard = extractor.extractPrCard(p);
            this.puCards = (ArrayList<PublicObjectiveCard>)extractor.extractPuCards();
            setExtractedCardsMessage(p.getPlayerID(), prCard);
        }

        //Initializes the roundtrack
        roundTrack = new RoundTrack(getPlayersNumber());
    }

    /**
     * Sets up a message containing the names of the cards that have been extracted
     * @param playerID the receiver
     * @param prCardName name of the private cards (public cards are an attribute of the model)
     */
    private void setExtractedCardsMessage(int playerID, String prCardName){
        String[] puCardNames = new String[Extractor.NUM_PUCARDS_EXTRACTED];
        for(int i = 0; i < Extractor.NUM_PUCARDS_EXTRACTED; i++){
            puCardNames[i] = this.puCards.get(i).getName();
        }

        MVExtractedCardsMessage message = new MVExtractedCardsMessage(playerID, prCardName, puCardNames);
        notify(message);
    }

    public void addPlayer(String name) {
        Player p = new Player(name, players.size() + 1 );
        players.add(p);
        //setSetupMessage(players.size(),  extractWpcs());
    }

    /**
     * Creates a MVSetupMessage that notifies the player that he's been allowed into the game, asking him tho choose between
     * one of the extracted boards.
     * @param playerID the playerID of the receiver
     * @param indexes the indexes of the boards
     */
    private void setSetupMessage(int playerID, int[] indexes){
        MVSetUpMessage message = new MVSetUpMessage(playerID, indexes);
        notify(message);
    }

}
