package it.polimi.se2018.view.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class Login {
    public RadioButton connection1;
    public RadioButton connection2;
    public TextField user;
    public TextField portnumber;

    public void handlePlay() throws IOException {
        ToggleGroup link = new ToggleGroup();
        connection1.setToggleGroup(link);
        connection2.setToggleGroup(link);
        connection1.setUserData("socket");
        connection2.setUserData("rmi");
        String pn = portnumber.getText();
        String username = user.getText();
        String s = link.getSelectedToggle().getUserData().toString();
        if(s.equals("socket")){

        }
        else {


        }

        if(username.equals("")){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/missingUsername.fxml"));
            Scene window = new Scene(loader.load(), 463, 55);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Error");
            stage.setResizable(false);
            stage.show();
        }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/choice.fxml"));
            Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Choice");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
            stage.setResizable(false);
            stage.show();
        }
    }
}
