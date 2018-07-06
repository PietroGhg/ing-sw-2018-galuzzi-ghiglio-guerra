package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

/**
 * Class for the messages from the Model to the View, notifies a player that his turn timer run out
 */
public class MVTimesUpMessage extends MVAbstractMessage{

    public MVTimesUpMessage(int playerID){
        this.playerID = playerID;
    }

    public void accept(AbstractView view){
        view.visit(this);
    }

    public void accept(GUIcontroller gc){
        MVTimesUpMessage m = this;
        Platform.runLater(() -> gc.visit(m));
    }
}
