package it.polimi.se2018.view.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


public class GUIcontroller {
    public RadioButton connection1;
    public RadioButton connection2;
    public TextField user;
    public RadioButton choice1;
    public RadioButton choice2;
    public RadioButton choice3;
    public RadioButton choice4;





    public void handlePlay() throws IOException {
        ToggleGroup link = new ToggleGroup();
        connection1.setToggleGroup(link);
        connection2.setToggleGroup(link);
        connection1.setUserData("socket");
        connection2.setUserData("rmi");
        String s = link.getSelectedToggle().getUserData().toString();
        if(s.equals("socket")){

        }
        else {


        }

        if(user.getText().equals("")){
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

    public void handleChoice() throws IOException{
        ToggleGroup choice = new ToggleGroup();
        choice1.setToggleGroup(choice);
        choice2.setToggleGroup(choice);
        choice3.setToggleGroup(choice);
        choice4.setToggleGroup(choice);
        choice1.setUserData("1");
        choice2.setUserData("2");
        choice3.setUserData("3");
        choice4.setUserData("4");
        String n = choice.getSelectedToggle().getUserData().toString();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/gameWindow.fxml"));
        Scene window = new Scene(loader.load(), 900, 600);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("Game");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();
    }

    public void showWindows() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/windows.fxml"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("Windows");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();

    }

    public void showToolCards() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/toolCards.fxml"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("ToolCards");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();

    }

    public void showDraftPool() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/draftPool"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("DraftPool");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();

    }

    public void showPuCards() throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/puCards"));
        Scene window = new Scene(loader.load(), 600, 400);
        Stage stage = new Stage();
        stage.setScene(window);
        stage.setTitle("PuCards");
        stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
        stage.setResizable(false);
        stage.show();


    }

    public void diceMove(){


    }

    public void toolCard(){

    }



}


