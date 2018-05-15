package it.polimi.se2018.model;

import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

/**
 * Class for the parameters required by a toolcard.cardAction() method in order to modify the model according to the
 * player's input.
 * Contains an attribute wpc (the player's board) and an ArrayList of Integer values.
 * The values are sorted in a predefined way, so that in the method cardAction() it's possible to associate each
 * value with the corresponding user input.
 */

public class PlayerMoveParameters {
    private Player player;
    private ArrayList<Integer> parameters;

    public PlayerMoveParameters(Player player){
        this.player = player;
        parameters = new ArrayList<>();
    }

    public void setWPC(WPC wpc){
        player.setWpc(wpc);
    }

    public void addParameter(int i){
        parameters.add(i);
    }

    public Player getPlayer() { return player; }

    public int getParameter(int i){
        return parameters.get(i);
    }
}
