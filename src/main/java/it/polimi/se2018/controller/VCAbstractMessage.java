package it.polimi.se2018.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class extended by the other messages from View to Controller.
 * Contains the id of the player and the parameters of the performed move
 */
public abstract class VCAbstractMessage implements Serializable {
    private int playerID;
    private ArrayList<Integer> parameters;

    public VCAbstractMessage(int playerID){
        this.playerID = playerID;
        parameters = new ArrayList<>();
    }

    public int getPlayerID(){ return playerID; }
    public List<Integer> getParameters() { return parameters; }
    public abstract void accept(Controller c);
    public void addParameter(int n){
        parameters.add(n);
    }
}
