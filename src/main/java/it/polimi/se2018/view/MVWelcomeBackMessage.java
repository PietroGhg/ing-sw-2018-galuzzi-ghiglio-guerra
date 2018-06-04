package it.polimi.se2018.view;

public class MVWelcomeBackMessage extends MVGameMessage {
    String message;
    String playerName;

    public MVWelcomeBackMessage(int playerID, String playerName, String message){
        super(message, playerID);
        this.playerName = playerName;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public String getPlayerName() {
        return playerName;
    }

    public String getMessage() {
        return message;
    }
}
