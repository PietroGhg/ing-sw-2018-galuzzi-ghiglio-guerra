package it.polimi.se2018.view;


import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

/**
 * Class for the messages from the Model to the View, notifies the beginning of the actual game
 */
public class MVStartGameMessage extends MVGameMessage {

    public MVStartGameMessage(String message, int playerID){
        super(message, playerID);
    }

    @Override
    public void accept(AbstractView view){
        view.visit(this);
    }

    public void accept(GUIcontroller gc){
        MVStartGameMessage m = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gc.visit(m);
            }
        });
    }

}
