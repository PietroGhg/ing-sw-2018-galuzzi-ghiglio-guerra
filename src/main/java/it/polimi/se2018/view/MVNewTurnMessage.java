package it.polimi.se2018.view;

public class MVNewTurnMessage extends MVGameMessage {

    public MVNewTurnMessage(String message, int playerID){
        super(message, playerID);
    }

    @Override
    public void accept(AbstractView view){ view.visit(this); }
}
