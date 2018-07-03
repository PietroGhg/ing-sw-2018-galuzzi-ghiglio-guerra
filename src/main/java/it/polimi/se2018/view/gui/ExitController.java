package it.polimi.se2018.view.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for the exit confirmation window
 */
public class ExitController {
    @FXML
    private Button yes;

    @FXML
    public void exitChoice(Event e){
        if(e.getSource().equals(yes)) System.exit(0);
        else{
            Stage s = (Stage)yes.getScene().getWindow();
            s.close();
        }
    }
}
