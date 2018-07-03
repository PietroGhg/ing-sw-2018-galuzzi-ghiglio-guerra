package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

/**
 * Class for the messages from the Model to the View, concerning the disconnection
 */
public class MVDiscMessage extends MVAbstractMessage {
    private String message;

    public MVDiscMessage(String message){
        this.message = message;
    }

    public void accept(AbstractView view){
        view.visit(this);
    }

    public void accept(GUIcontroller gc){
        MVDiscMessage m = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gc.visit(m);
            }
        });
    }

    public String getMessage(){return message; }
}
