package it.polimi.se2018.model;

import it.polimi.se2018.model.table.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for the parameters required by a toolcard.cardAction() or diceMove() method in order to modify the model according to the
 * player's input.
 * Contains an attribute wpc (the player's board) and an ArrayList of Integer values.
 * The values are sorted in a predefined way, so that in the method cardAction() it's possible to associate each
 * value with the corresponding user input.
 */

public class PlayerMoveParameters {
    private Model model;
    private int playerID;
    private ArrayList<Integer> parameters;

    public PlayerMoveParameters(int playerID, Model model){
        this.model = model;
        this.playerID = playerID;
        parameters = new ArrayList<>();
    }

    public PlayerMoveParameters(int playerID, ArrayList<Integer> p, Model model){
        this.playerID = playerID;
        parameters = p;
        this.model = model;
    }

    public Player getPlayer() { return model.getPlayer(playerID); }

    public ArrayList<Die> getDraftPool() { return model.getDraftPool(); }

    public void setDraftPool(ArrayList<Die> draftPool) { model.setDraftPool(draftPool);}

    public ArrayList<ArrayList<Die>> getRoundTrack() { return model.getRoundTrack(); }

    public void setRoundTrack(ArrayList<ArrayList<Die>> roundTrack) { model.setRoundTrack(roundTrack); }

    public int turnNumber(int playerID){ return model.turnNumber(playerID); }

    public void addParameter(int i){ parameters.add(i); }

    public int getParameter(int i){ return parameters.get(i); }

    public ArrayList<Integer> getParameters() { return parameters; }

    public Die getVacantDie() {return model.getFloatingDie(); }

    public boolean dieHasBeenPlayed() { return model.dieHasBeenPlayed(); }

    public int paramCount(){ return parameters.size(); }

    public Die getFloatingDie(){ return model.getFloatingDie(); }

    public void setFloatingDie(Die d){model.setFloatingDie(d); }

    public void setTC6Message(int playerID, String message, List<int[]> validCoordinates){
        model.setTC6Message(playerID, message, validCoordinates);
    }

}
