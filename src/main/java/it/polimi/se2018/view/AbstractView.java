package it.polimi.se2018.view;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

public abstract class AbstractView extends Observable<VCAbstractMessage> implements Observer<MVAbstractMessage> {
    private int playerID;

    public void update(MVAbstractMessage message){
        message.accept(this);
    }

    public abstract void visit(MVGameMessage message);

    public abstract void visit(MVSetUpMessage message);

    public int getPlayerID(){ return playerID; }

}
