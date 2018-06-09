package it.polimi.se2018.networking.client.GUI;


import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class GUIcontroller {
    public Button Connection;
    public Button Play;


    public void handleConnection() {

    }

    public void handlePlay() throws IOException {
        System.out.println("cazzo, funziona");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/gameWindow.fxml"));
        Scene window = new Scene(loader.load(), 1200, 800);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("Game");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();
    }

}

