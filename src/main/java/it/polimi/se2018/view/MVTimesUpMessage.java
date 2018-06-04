package it.polimi.se2018.view;

public class MVTimesUpMessage extends MVAbstractMessage{

    public MVTimesUpMessage(int playerID){
        this.playerID = playerID;
    }

    public void accept(AbstractView view){
        view.visit(this);
    }
}
