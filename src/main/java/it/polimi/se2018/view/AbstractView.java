package it.polimi.se2018.view;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;

public abstract class AbstractView extends Observable<VCAbstractMessage> implements Observer<MVAbstractMessage> {
    protected int playerID;

    public void update(MVAbstractMessage message){
        message.accept(this);
    }

    public abstract void visit(MVGameMessage message);

    public abstract void visit(MVSetUpMessage message);

    public abstract void visit(MVStartGameMessage message);

    public abstract void visit(MVNewTurnMessage message);

    public abstract void visit(MVWelcomeBackMessage message);

    public abstract void visit(MVTimesUpMessage message);

    public abstract void visit(MVWinnerMessage message);

    public int getPlayerID(){ return playerID; }

}
