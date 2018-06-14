package it.polimi.se2018.controller;

import it.polimi.se2018.controller.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
