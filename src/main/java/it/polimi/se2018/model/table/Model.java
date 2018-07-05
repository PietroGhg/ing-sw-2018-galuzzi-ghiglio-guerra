package it.polimi.se2018.model.table;

import it.polimi.se2018.controller.turntimer.TurnTimer;
import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.*;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for the model
 */
public class Model extends Observable<MVAbstractMessage> {

    private static final Logger LOGGER = Logger.getLogger(Model.class.getName());
    private List<Die> draftPool;
    public List<Die> getDraftPool() { return draftPool; }
    public void setDraftPool(List<Die> draftPool) { this.draftPool = draftPool;}
    public static final int MAX_PLAYERS = 4;

    private RoundTrack roundTrack;
    private List<String> toolCardsInUse;
    private List<Player> players;
    private List<PublicObjectiveCard> puCards;
    private DiceBag diceBag;
    private Turn turn;
    private PlayerMoveParameters playerMoveParameters;
    private List<String> discPlayers;
    private List<String> playerNames;

    public Model(){
        players = new ArrayList<>();
        puCards = new ArrayList<>();
        draftPool = new ArrayList<>();
        discPlayers = new ArrayList<>();
        playerNames = new ArrayList<>();
        roundTrack = new RoundTrack(getPlayersNumber());
        diceBag = DiceBag.getInstance();
        turn = new Turn();
    }

    public void setRoundTrack(List<List<Die>> roundTrack){ this.roundTrack.setRT(roundTrack); }

    public List<List<Die>> getRoundTrack(){ return roundTrack.getRT(); }

    public List<Die> getDiceBag() { return diceBag.getDiceBag(); }

    public void setDiceBag(List<Die> bag) { diceBag.setDiceBag(bag); }

    public int[][] getRoundMatrix() { return roundTrack.getRoundMatrix(); }

    public int getPlayersNumber() { return players.size(); }

    public int turnNumber(int playerID){ return roundTrack.turnNumber(playerID); }

    public void nextTurn(TurnTimer turnTimer){
        try {
            boolean newTurn = true; //Used to avoid multiple notifications in case there's a disconnected player
            draftPool = roundTrack.nextTurn(draftPool);
            turn.clear();
            turnTimer.reset(); //resets a turn timer

            Player currPlayer = players.get(whoIsPlaying() - 1);
            if(currPlayer.isDisconnected()){
                nextTurn(turnTimer);
                newTurn = false;
            }
            if(currPlayer.getSkipTurn()){
                nextTurn(turnTimer);
                currPlayer.setSkipTurn(false);
                newTurn = false;
            }
            if(newTurn) {
                setNewTurnMessage(whoIsPlaying());
            }
        }
        catch (GameEndedException e){
            turnTimer.cancel(); //cancels the turn timer
            chooseWinner();
        }
    }

    /**
     * Method used in testing
     */
    public void nextTurn(){
        try {
            draftPool = roundTrack.nextTurn(draftPool);
            turn.clear();
            if(players.get(whoIsPlaying()-1).isDisconnected()) nextTurn();
        }
        catch (GameEndedException e){
            chooseWinner();
        }
    }

    /**
     * Method used the find the winner
     */
    private void chooseWinner(){
        ChooseWinner chooseWinner;
        chooseWinner = new ChooseWinner(players, puCards, roundTrack);
        try {
            Player winner = chooseWinner.getWinner();
            notify(new MVWinnerMessage(winner.getPlayerID(), winner.getName() + " won the game!"));
        }
        catch (NoWinnerException e){
            LOGGER.log(Level.SEVERE,"Error while choosing a winner");
        }
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

    public void addPlayer(Player p){
        players.add(p);
    }

    /**
     * Add a player, basing on his name
     * @param playerName the player name
     */
    public void addPlayer(String playerName) {
        Player p = new Player(players.size() + 1, playerName);
        players.add(p);
        playerNames.add(playerName);
        String s = playerName + " joined";
        LOGGER.log(Level.INFO, s);
    }



    public List<String> getPlayerNames(){
        return playerNames;
    }

    /**
     * @return a list of the disconnected players
     */
    public List<String> getDiscPlayers(){ return discPlayers; }

    public void addDiscPlayer(String playerName){
        discPlayers.add(playerName);
    }

    /**
     * remove a player from the disconnected players list
     * @param playerName the player to remove from the list
     */
    public void removeDiscPlayer(String playerName) {
        discPlayers.remove(playerName);
    }

    public void removePlayerName(String playerName){
        playerNames.remove(playerName);
    }

    public void addPlayerName(String playerName){
        playerNames.add(playerName);
    }

    /**
     * @return the number of the current active players
     */
    public int numActivePlayers(){ return playerNames.size(); }

    public void removePlayer(String playerName){
        try{
            Player p = getPlayer(playerName);
            players.remove(p);
            removePlayerName(playerName);
        }
        catch(UserNameNotFoundException e){
            LOGGER.log(Level.SEVERE, "Error while handling disconnection");
        }
    }


    public Player getPlayer(String playerName) throws UserNameNotFoundException{
        for(Player p: players){
            if(playerName.equals(p.getName())) return p;
        }
        throw new UserNameNotFoundException();
    }

    /**
     * Method that starts a game, extracting public objective cards, private objective cards and tool cards
     * and notifies the players
     */
    public void startGame() {
        //for each player, extract the cards and send an ExtractedCardMessage
        Extractor extractor = Extractor.getInstance();
        this.puCards = extractor.extractPuCards();

        //Converts public objective cards to String
        String[] puCardsNames = new String[this.puCards.size()];

        for(int i = 0; i < this.puCards.size(); i++){
            puCardsNames[i] = this.puCards.get(i).getName();
        }

        //Extracts three toolcards
        toolCardsInUse = extractor.extractToolCards();

        //Initializes the roundtrack and extracts dice for the draftpool
        roundTrack = new RoundTrack(getPlayersNumber());
        draftPool = diceBag.extractDice(getPlayersNumber());

        //Extracts private obj cards and wpcs, sends MVSetUPMessages
        for(Player p: players){
            String prCard = extractor.extractPrCard(p);
            int[] wpcsExtracted = extractor.extractWpcs();
            setSetupMessage(p.getName(), p.getPlayerID(), wpcsExtracted, prCard, puCardsNames);
        }
    }

    public void isInUse(String toolCard) throws MoveNotAllowedException{
        if(!toolCardsInUse.contains(toolCard)) throw new MoveNotAllowedException("Toolcard not in use");
    }

    /**
     * Creates a MVSetupMessage that notifies the player that he's been allowed into the game, asking him tho choose between
     * one of the extracted boards.
     * @param playerID the playerID of the receiver
     * @param indexes the indexes of the boards
     */
    private synchronized void setSetupMessage(String playerName, int playerID, int[] indexes, String prCards, String[] puCards){
        Map<Integer, WPC> extracted = new HashMap<>();
        WpcGenerator generator = WpcGenerator.getInstance();
        for(int i = 0; i < indexes.length; i++){
            extracted.put(i+1, generator.getWPC(indexes[i]));
        }
        MVSetUpMessage message = new MVSetUpMessage(playerName, playerID, extracted, prCards, puCards, toolCardsInUse);
        String s = "Sending to: " + players.get(playerID - 1).getPlayerID() + "\nMessage: " + message +"\n\n";
        LOGGER.log(Level.INFO, s);
        notify(message);
    }

    public void setStartGameMessage(String m, int playerID){
        MVStartGameMessage message = new MVStartGameMessage(m, playerID);

        setData(message);

        notify(message);
    }

    public void setWelcomeBackMessage(int playerID, String playerName, String mex){
        MVWelcomeBackMessage message = new MVWelcomeBackMessage(playerID, playerName, mex);
        setData(message);
        //sets public cards
        String[] puNames = new String[3];
        for(int i = 0; i < 3; i++){
            puNames[i] = puCards.get(i).getName();
        }
        message.setPuCards(puNames);
        //sets toolcards
        message.setTcInUse(toolCardsInUse);
        //sets private card
        PrivateObjectiveCard card = players.get(playerID-1).getPrCard();
        message.setPrCard(card.getName());
        notify(message);
    }

    private void setNewTurnMessage(int playerID){
        MVNewTurnMessage message = new MVNewTurnMessage("Round: " + (roundTrack.getRoundCounter() + 1) + ", Turn: " + (roundTrack.getTurnCounter() + 1), playerID);
        setData(message);
        notify(message);
    }

    public void setWinnerMessage(String playerName){
        try {
            Player p = getPlayer(playerName);
            notify(new MVWinnerMessage(p.getPlayerID(), "All the other players disconnected, you won."));
        }
        catch(UserNameNotFoundException e){
            LOGGER.log(Level.SEVERE, "Error while notifing winner.");
        }
    }

    /**
     * sets up a MVGameMessage containing all the useful datas and notifies it to the view
     * @param m the message that has to be notified to the view
     */
    public void setGameMessage(String m, int playerID){
        MVGameMessage message = new MVGameMessage(m, playerID);

        setData(message);

        notify(message);
    }

    private void setData(MVGameMessage message){
        Map<String, Integer> favourT = new HashMap();
        //set up wpcs and favor tokens
        for(Player p: players){
            message.setWpc(p.getPlayerID(), p.getWpc());
            favourT.put(p.getName(), p.getFavorTokens());
        }
        message.setFavourT(favourT);
        //set up draftpool
        message.setDraftPool(draftPool);
        //set up roundtrack
        message.setRoundTrack(roundTrack.getRT());

        message.setDiceBag(diceBag);

        message.setCurrPlayer(whoIsPlaying());
    }

    public void setMVTimesUpMessage(){
        notify(new MVTimesUpMessage(whoIsPlaying()));
    }

    public void setWpc(int playerID, int chosenWpc){
        WpcGenerator generator = WpcGenerator.getInstance();
        WPC chosen = generator.getWPC(chosenWpc);
        Player player = players.get(playerID - 1);
        player.setWpc(chosen);
    }

    public void setDiscMessage(String playerName){
        notify(new MVDiscMessage(playerName + " disconnected."));
    }

    public void setReady(int playerID){
        players.get(playerID - 1).setReady();
    }

    /**
     * Checks if all the players are ready to start
     * @return true if all the players are ready, otherwise return false
     */
    public boolean allReady() {
        boolean ris = true;

        for(Player p: players){
            if(!p.isReady()) ris = false;
        }

        return ris;
    }

    public void setSkipTurn(int playerID, boolean skipTurn){
        Player p = players.get(playerID - 1);
        p.setSkipTurn(skipTurn);
    }

}
