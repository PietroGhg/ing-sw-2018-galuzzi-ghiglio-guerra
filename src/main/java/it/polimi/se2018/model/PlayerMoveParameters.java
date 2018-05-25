package it.polimi.se2018.model;

import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.table.RoundTrack;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

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

    public ArrayList<ArrayList<Die>> getRoundTrack() { return model.getRoundTrack().getRT(); }

    public int turnNumber(int playerID){ return model.turnNumber(playerID); }

    public void addParameter(int i){ parameters.add(i); }

    public int getParameter(int i){ return parameters.get(i); }

    public ArrayList<Integer> getParameters() { return parameters; }

    public Die getVacantDie() {return model.getVacantDie(); }

    public boolean dieHasBeenPlayed() { return model.dieHasBeenPlayed(); }

    public int paramCount(){ return parameters.size(); }
}
