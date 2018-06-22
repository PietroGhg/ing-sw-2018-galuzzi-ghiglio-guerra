package it.polimi.se2018.view.gui;

import it.polimi.se2018.controller.vcmessagecreator.VCMessageCreator;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.networking.client.ServerConnection;
import it.polimi.se2018.networking.client.rmi.ClientPollingTimer;
import it.polimi.se2018.networking.client.rmi.RMIServerConnImpl;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.networking.client.rmi.ServerConnectionAdapter;
import it.polimi.se2018.networking.client.socket.SocketServerConnection;
import it.polimi.se2018.networking.server.rmi.RMIClientConnection;
import it.polimi.se2018.networking.server.socket.SocketClientConnection;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.view.MVAbstractMessage;
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.cli.View;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Login {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
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
        loader.load();
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

        /*
        gets a reference of the javafx controller
        the controler will register/be registered as observer
        */
        GUIcontroller guiController = loader.getController();

        if(s.equals("socket")){
            try {
                socketConnect(username, pn, guiController);
            }
            catch(GameStartedException e){ guiController.displayMessage("A game is already started.");}
            catch(UserNameTakenException e){guiController.displayMessage("Username already taken.");}
        }
        else {
            rmiConnect(username, guiController);

        }
    }

    private void socketConnect(String username, int port, GUIcontroller guiController) throws GameStartedException, UserNameTakenException, IOException{
        //Instantiates model representation and vcmessagecreator, opens input and output stream
        socket = new Socket("localhost", port);
        ModelRepresentation modelRep = new ModelRepresentation();
        VCMessageCreator vcMessageCreator = new VCMessageCreator(guiController, modelRep);
        serverConnection = new SocketServerConnection(socket);
        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());

        //sends the username to the server, checks the response
        System.out.println(in.nextLine());
        out.println(username);
        String response = in.nextLine();
        if(response.equals("A game is already started")) throw new GameStartedException();
        if(response.equals("Username already taken")) throw new UserNameTakenException();

        guiController.displayMessage("Welcome to Sagrada, wait for other players.");

        //registers observers and starts a connection thread
        guiController.init(modelRep);
        guiController.register(serverConnection);
        serverConnection.register(guiController);
        guiController.rawRegister(vcMessageCreator);
        new Thread((SocketServerConnection)serverConnection).start();

    }

    private void rmiConnect(String username, GUIcontroller guiController){
        RMIClientConnection serverService;
        ModelRepresentation modelRep;
        VCMessageCreator vcMessageCreator;
        ClientPollingTimer timer;
        RMIServerConnection rmiServerConnection;
        try {
            //looks up for the rmiclientconnection and remote-calls the handleRequest() method
            serverService = (RMIClientConnection) Naming.lookup("//localhost/sagradarmi");
            rmiServerConnection = new RMIServerConnImpl(serverService);
            RMIServerConnection serverConnInt = (RMIServerConnection) UnicastRemoteObject.exportObject(rmiServerConnection,0);
            serverService.handleRequest(username
                    , serverConnInt); //throws exceptions

            //instantiates modelrep and vcmessagecreator, registers observers
            modelRep = new ModelRepresentation();
            guiController.init(modelRep);
            vcMessageCreator = new VCMessageCreator(guiController, modelRep);
            guiController.rawRegister(vcMessageCreator);
            guiController.register(new ServerConnectionAdapter(rmiServerConnection));
            rmiServerConnection.register(new SockToRMIObserverAdapter<>(guiController));

            //remote call for checkEnoughPlayers()
            serverService.checkEnoughPlayers();

            //starts the polling thread
            timer = new ClientPollingTimer(serverService, guiController);
            timer.startPolling();
        }
        catch(NotBoundException |MalformedURLException |RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString());
        }
        catch(UserNameTakenException e){
            guiController.displayMessage("Username already taken.");
        }
        catch(GameStartedException e){
            guiController.displayMessage("Game already started. ");
        }
    }
}
