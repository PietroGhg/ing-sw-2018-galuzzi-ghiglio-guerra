package it.polimi.se2018.view;

import it.polimi.se2018.utils.Observer;

public abstract class AbstractView implements Observer<MVMessage> {
    private int playerID;

    public void update(MVMessage message){
        message.accept(this);
    }

    public abstract void visit(MVGameMessage message);
    public abstract void visit(MVSetUpMessage message);
    public abstract void visit(MVExtractedCardsMessage message);

    public int getPlayerID(){ return playerID; }

}
