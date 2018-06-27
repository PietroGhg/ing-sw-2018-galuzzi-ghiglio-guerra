package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;

import java.io.Serializable;

public abstract class MVAbstractMessage implements Serializable {
    protected int playerID;
    public abstract void accept(AbstractView view);
    public abstract void accept(GUIcontroller guIcontroller);
    public int getPlayerID() {
        return playerID;
    }
}
