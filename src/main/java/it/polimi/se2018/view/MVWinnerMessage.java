package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

/**
 * Class for the messages from the Model to the View, notifies the winner
 */
public class MVWinnerMessage extends MVAbstractMessage {
    private String message;

    public MVWinnerMessage(int playerID, String message){
        this.playerID = playerID;
        this.message = message;
    }

    public void accept(AbstractView view){
        view.visit(this);
    }

    public void accept(GUIcontroller gc){
        MVWinnerMessage m = this;
        Platform.runLater(() -> gc.visit(m));
    }

    public String getMessage(){ return message; }
}
