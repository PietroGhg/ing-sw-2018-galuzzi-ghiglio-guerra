package it.polimi.se2018.model;

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
    private int playerID;
    private ArrayList<Integer> parameters;

    public PlayerMoveParameters(int playerID){
        this.playerID = playerID;
        parameters = new ArrayList<>();
    }

    public PlayerMoveParameters(int playerID, ArrayList<Integer> p){
        this.playerID = playerID;
        parameters = p;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void addParameter(int i){
        parameters.add(i);
    }

    public int getParameter(int i){
        return parameters.get(i);
    }

    public ArrayList<Integer> getParameters() { return parameters; }
}
