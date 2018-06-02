package it.polimi.se2018.view;


public class MVStartGameMessage extends MVGameMessage {

    public MVStartGameMessage(String message, int playerID){
        super(message, playerID);
    }

    @Override
    public void accept(AbstractView view){
        view.visit(this);
    }

}
