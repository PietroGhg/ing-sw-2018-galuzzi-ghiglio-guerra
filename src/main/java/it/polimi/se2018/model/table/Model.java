package it.polimi.se2018.model.table;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameEndedException;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.NoWinnerException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.states.States;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.*;

import java.util.ArrayList;
import java.util.List;

public class Model extends Observable<MVAbstractMessage> {

    //essendo un ArrayList, per il get di un dado basta il metodo get(index)
    // e per settare un dado basta draftPool.add(index,die)

    private ArrayList<Die> draftPool;
    public ArrayList<Die> getDraftPool() { return draftPool; }
    public void setDraftPool(ArrayList<Die> draftPool) { this.draftPool = draftPool;}
    private final int MAX_PLAYERS = 2;

    private RoundTrack roundTrack;
    private ArrayList<Player> players;
    private ArrayList<PublicObjectiveCard> puCards;
    private DiceBag diceBag;
    private Die vacantDie; //eventually used for toolcards 6 and 11
    private ChooseWinner chooseWinner;
    private Turn turn;
    private PlayerMoveParameters playerMoveParameters;
    private States state;
    private List<String> discPlayers;

    public Model(){
        players = new ArrayList<>();
        puCards = new ArrayList<>();
        draftPool = new ArrayList<>();
        discPlayers = new ArrayList<>();
        roundTrack = new RoundTrack(getPlayersNumber());
        diceBag = DiceBag.getInstance();
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
            //TODO: notify the players
            notify(new MVNewTurnMessage("It's your turn", whoIsPlaying()));
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
     * sets up a MVGameMessage containing all the useful datas and notifies it to the view
     * @param m the message that has to be notified to the view
     */
    public void setGameMessage(String m, int playerID){
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

    public void setDiePlaced() { turn.diePlayed(); }

    public void setToolCardUsed() { turn.cardPlayed(); }

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

    public void addPlayer(String playerName) throws GameStartedException{
        if(state != States.CONNECTION) throw new GameStartedException();

        Player p = new Player(players.size() + 1, playerName);
        players.add(p);
    }

    /**
     * Method called by the server in order to check if there are enough players to start a game
     */
    public void checkEnoughPlayers() {
        if(players.size() == MAX_PLAYERS) startGame();
    }

    public void handleRequest(String playerName) throws GameStartedException, UserNameTakenException{
        ArrayList<String> playerNames = (ArrayList<String>)getPlayerNames();

        if (playerNames.contains(playerName)) throw new UserNameTakenException();
        if (discPlayers.contains(playerName)) reconnect(playerName);
        if (state != States.CONNECTION) throw new GameStartedException();
        if (state == States.CONNECTION){ addPlayer(playerName); }
    }

    private List<String> getPlayerNames(){
        List<String> playerNames = new ArrayList<>();

        for(Player p: players){
            playerNames.add(p.getName());
        }
        return playerNames;
    }

    public void handleDisconnection(String playerName) {
        //Get the right player having the playername
        //Set the disconnected flag in player
    }

    public void reconnect(String playerName) {
        //Get the right player having the playername
        //Set disconnected flag to 0
        //Throw reconnected player exception
    }

    /**
     * Method that starts a game, setting the GAMEPLAY state, extracting public, private a tool cards and notifying the
     * players.
     */
    public void startGame() {
        state = States.GAMEPLAY;
        //for each player, extract the cards and send an ExtractedCardMessage
        Extractor extractor = Extractor.getInstance();
        this.puCards = (ArrayList<PublicObjectiveCard>)extractor.extractPuCards();

        //Converts public objective cards to String
        String[] puCardsNames = new String[this.puCards.size()];

        for(int i = 0; i < this.puCards.size(); i++){
            puCardsNames[i] = this.puCards.get(i).getName();
        }

        for(Player p: players){
            String prCard = extractor.extractPrCard(p);
            int[] wpcsExtracted = extractor.extractWpcs(p);
            setSetupMessage(p.getName(), p.getPlayerID(), wpcsExtracted, prCard, puCardsNames);
        }

        //Initializes the roundtrack and extracts dice for the draftpool
        roundTrack = new RoundTrack(getPlayersNumber());
        draftPool = diceBag.extractDice(getPlayersNumber());
    }



    /**
     * Creates a MVSetupMessage that notifies the player that he's been allowed into the game, asking him tho choose between
     * one of the extracted boards.
     * @param playerID the playerID of the receiver
     * @param indexes the indexes of the boards
     */
    private void setSetupMessage(String playerName, int playerID, int[] indexes, String prCards, String[] puCards){
        MVSetUpMessage message = new MVSetUpMessage(playerName, playerID, indexes, prCards, puCards);
        notify(message);
    }

    public void setStartGameMessage(String m, int playerID){
        MVStartGameMessage message = new MVStartGameMessage(m, playerID);
        notify(message);
    }

    public void setWpc(int playerID, int chosenWpc){
        WpcGenerator generator = new WpcGenerator();
        WPC chosen = generator.getWPC(chosenWpc);
        Player player = players.get(playerID - 1);
        player.setWpc(chosen);
    }

    public void setReady(int playerID){
        players.get(playerID - 1).setReady();
    }

    public boolean allReady() {
        boolean ris = true;

        for(Player p: players){
            if(!p.isReady()) ris = false;
        }

        return ris;
    }

}
