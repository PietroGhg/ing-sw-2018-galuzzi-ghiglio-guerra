package it.polimi.se2018.view;

public class MVWinnerMessage extends MVAbstractMessage {
    private String message;

    public MVWinnerMessage(int playerID, String message){
        this.playerID = playerID;
        this.message = message;
    }

    public void accept(AbstractView view){
        view.visit(this);
    }

    public String getMessage(){ return message; }
}
