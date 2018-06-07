package it.polimi.se2018.view;

public class MVWelcomeBackMessage extends MVGameMessage {
    String playerName;

    public MVWelcomeBackMessage(int playerID, String playerName, String message){
        super(message, playerID);
        this.playerName = playerName;
    }

    @Override
    public void accept(AbstractView view){ view.visit(this); }

    public String getPlayerName() {
        return playerName;
    }

}
