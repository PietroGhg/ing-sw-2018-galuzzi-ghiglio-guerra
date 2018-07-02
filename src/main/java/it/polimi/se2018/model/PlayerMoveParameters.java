package it.polimi.se2018.model;

import it.polimi.se2018.model.table.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the parameters required by a toolcard.cardAction() or diceMove() method in order to modify the
 * model according to the player's input.
 * Contains an attribute wpc (the player's board) and an ArrayList of Integer values.
 * The values are sorted in a predefined way, so that in the method cardAction() it's possible to associate each
 * value with the corresponding user input.
 */

public class PlayerMoveParameters {
    private Model model;
    private int playerID;
    private List<Integer> parameters;

    public PlayerMoveParameters(int playerID, Model model){
        this.model = model;
        this.playerID = playerID;
        parameters = new ArrayList<>();
    }

    public PlayerMoveParameters(int playerID, List<Integer> p, Model model){
        this.playerID = playerID;
        parameters = p;
        this.model = model;
    }

    public Player getPlayer() { return model.getPlayer(playerID); }

    public List<Die> getDraftPool() { return model.getDraftPool(); }

    public void setDraftPool(List<Die> draftPool) { model.setDraftPool(draftPool);}

    public List<List<Die>> getRoundTrack() { return model.getRoundTrack(); }

    public void setRoundTrack(List<List<Die>> roundTrack) { model.setRoundTrack(roundTrack); }

    public List<Die> getDiceBag () {return model.getDiceBag(); }

    public int turnNumber(int playerID){ return model.turnNumber(playerID); }

    public void addParameter(int i){ parameters.add(i); }

    public int getParameter(int i){ return parameters.get(i); }

    public List<Integer> getParameters() { return parameters; }

    public boolean dieHasBeenPlayed() { return model.dieHasBeenPlayed(); }

    public int paramCount(){ return parameters.size(); }

    public void setSkipTurn(int playerID, boolean skipTurn){
        model.setSkipTurn(playerID, skipTurn);
    }

}
