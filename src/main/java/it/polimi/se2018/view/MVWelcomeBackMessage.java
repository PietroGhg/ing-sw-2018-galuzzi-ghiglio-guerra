package it.polimi.se2018.view;

public class MVWelcomeBackMessage extends MVAbstractMessage {
    String message;
    String playerName;

    public MVWelcomeBackMessage(int playerID, String playerName, String message){
        this.playerID = playerID;
        this.playerName = playerName;
        this.message = message;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
}
