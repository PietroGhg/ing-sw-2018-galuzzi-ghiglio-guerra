package it.polimi.se2018.view.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class for the GUI main method
 */
public class GUImain extends Application {
    @Override
    public void start(Stage primarystage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginGUI.fxml"));

        Parent root = loader.load();

        primarystage.setTitle("Sagrada");
        primarystage.setScene(new Scene (root, 1200, 800));
        primarystage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        primarystage.setResizable(false);
        primarystage.show();

    }

public static void main(String[] args){
    launch(args);
}
}
