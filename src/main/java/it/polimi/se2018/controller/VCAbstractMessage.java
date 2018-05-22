package it.polimi.se2018.controller;

import it.polimi.se2018.controller.Controller;

import java.util.ArrayList;

public abstract class VCAbstractMessage {
    private int playerID;
    private ArrayList<Integer> parameters;

    public int getPlayerID(){ return playerID; }

    public ArrayList<Integer> getParameters() { return parameters; }
    public abstract void accept(Controller c);
}
