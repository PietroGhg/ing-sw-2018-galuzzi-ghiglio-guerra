package it.polimi.se2018.view;

import java.io.Serializable;

public abstract class MVAbstractMessage implements Serializable {
    protected int playerID;
    public abstract void accept(AbstractView view);
}
