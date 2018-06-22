package it.polimi.se2018.view.gui;

import it.polimi.se2018.controller.vcmessagecreator.VCMessageCreator;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.ServerConnection;
import it.polimi.se2018.networking.client.socket.SocketServerConnection;
import it.polimi.se2018.networking.server.rmi.RMIClientConnection;
import it.polimi.se2018.networking.server.socket.SocketClientConnection;
import it.polimi.se2018.view.cli.ModelRepresentation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;


public class Login {
    public RadioButton connection1;
    public RadioButton connection2;
    public TextField user;
    public TextField portnumber;
    private ServerConnection serverConnection;
    private Socket socket;

    public void handlePlay() throws IOException {
        ToggleGroup link = new ToggleGroup();
        connection1.setToggleGroup(link);
        connection2.setToggleGroup(link);
        connection1.setUserData("socket");
        connection2.setUserData("rmi");
        int pn = Integer.valueOf(portnumber.getText());
        String username = user.getText();
        String s = link.getSelectedToggle().getUserData().toString();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/choice.fxml"));

        if(username.equals("")){
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(getClass().getResource("/fxml/missingUsername.fxml"));
            Scene window = new Scene(loader2.load(), 463, 55);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Error");
            stage.setResizable(false);
            stage.show();
        }
        else{
            //carica schermata di caricamento
            /*Scene window = new Scene(loader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Choice");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png" ));
            stage.setResizable(false);
            stage.show();*/
        }

        GUIcontroller guiController = loader.getController();
        if(s.equals("socket")){
            try {
                socketConnect(username, pn, guiController);
            }
            catch(GameStartedException e){ guiController.displayMessage("A game is already started.");}
            catch(UserNameTakenException e){guiController.displayMessage("Username already taken.");}
        }
        else {
            //rmiConnect(username, guiController);

        }
    }

    private void socketConnect(String username, int port, GUIcontroller guiController) throws GameStartedException, UserNameTakenException, IOException{
        socket = new Socket("localhost", port);
        ModelRepresentation modelRep = new ModelRepresentation();
        VCMessageCreator vcMessageCreator = new VCMessageCreator(guiController, modelRep);
        serverConnection = new SocketServerConnection(socket);
        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());

        System.out.println(in.nextLine());
        out.println(username);
        String response = in.nextLine();
        if(response.equals("A game is already started")) throw new GameStartedException();
        if(response.equals("Username already taken")) throw new UserNameTakenException();

        guiController.displayMessage("Welcome to Sagrada, wait for other players.");

        guiController.register(serverConnection);
        serverConnection.register(guiController);
        guiController.rawRegister(vcMessageCreator);
        new Thread((SocketClientConnection)serverConnection).start();

    }
}
