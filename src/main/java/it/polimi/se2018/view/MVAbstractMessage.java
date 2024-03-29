package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;

import java.io.Serializable;

/**
 * Abstract class extended by the other messages from Model to View
 */
public abstract class MVAbstractMessage implements Serializable {
    protected int playerID;
    public abstract void accept(AbstractView view);
    public abstract void accept(GUIcontroller guIcontroller);
    public int getPlayerID() {
        return playerID;
    }
}
