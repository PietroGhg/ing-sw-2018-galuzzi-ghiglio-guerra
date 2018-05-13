package it.polimi.se2018.Model;

import it.polimi.se2018.Model.WPC.WPC;

import java.util.ArrayList;

/**
 * Class for the parameters required by a ToolCard.cardAction() method in order to modify the model according to the
 * player's input.
 * Contains an attribute wpc (the player's board) and an ArrayList of Integer values.
 * The values are sorted in a predefined way, so that in the method cardAction() it's possible to associate each
 * value with the corresponding user input.
 */

public class PlayerMoveParameters {
    private WPC wpc;
    private ArrayList<Integer> parameters;

    public PlayerMoveParameters(WPC wpc){
        this.wpc = wpc;
        parameters = new ArrayList<>();
    }

    public void setWPC(WPC wpc){
        this.wpc = wpc;
    }

    public void addParameter(int i){
        parameters.add(i);
    }

    public int getParameter(int i){
        return parameters.get(i);
    }
}
