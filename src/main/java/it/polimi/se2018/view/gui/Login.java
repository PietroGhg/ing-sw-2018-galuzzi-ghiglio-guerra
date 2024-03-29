package it.polimi.se2018.view.gui;


import it.polimi.se2018.controller.vcmessagecreator.VCMessageCreator;
import it.polimi.se2018.exceptions.GUIErrorException;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.ServerConnection;
import it.polimi.se2018.networking.client.rmi.ClientPollingTimer;
import it.polimi.se2018.networking.client.rmi.RMIServerConnImpl;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.networking.client.rmi.ServerConnectionAdapter;
import it.polimi.se2018.networking.client.socket.SocketServerConnection;
import it.polimi.se2018.networking.server.rmi.RMIClientConnection;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.view.cli.ModelRepresentation;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the login window
 */
public class Login {
    private static final Logger LOGGER = Logger.getLogger(Login.class.getName());
    @FXML
    private RadioButton connection1;
    @FXML
    private RadioButton connection2;
    @FXML
    private TextField user;
    @FXML
    private TextField portnumber;
    @FXML
    private TextField ipaddress;
    @FXML
    private Button play;
    private Stage stage;

    public void handlePlay() throws IOException {
        boolean isMissing = false;
        String errorMessage = "";
        ToggleGroup link = new ToggleGroup();
        connection1.setToggleGroup(link);
        connection2.setToggleGroup(link);
        connection1.setUserData("socket");
        connection2.setUserData("rmi");

        String username = user.getText();
        String ipadd = ipaddress.getText();
        String s = link.getSelectedToggle().getUserData().toString();

        if(username.equals("")){
            isMissing = true;
            errorMessage = "Missing username.";
        }
        if(ipadd.equals("")){
            if(isMissing) errorMessage = "Missing username.\nMissing IP address";
            else {
                isMissing = true;
                errorMessage = "Missing IP address";
            }
        }
        if(isMissing){
            missing(errorMessage);
        }
        else{
            if(s.equals("socket")){
                try {
                    int pn = Integer.valueOf(portnumber.getText());

                    socketConnect(username, ipadd, pn);
                }
                catch(GameStartedException e){ missing("A game is already started.");}
                catch(UserNameTakenException e){missing("Username already taken.");}
                catch(InputMismatchException|NumberFormatException e){
                    missing("Missing port number.");
                }
                catch(GUIErrorException e){
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }
            }
            else {
                try {
                    rmiConnect(username, ipadd);
                }
                catch(GUIErrorException e){
                    LOGGER.log(Level.SEVERE, e.getMessage());
                }

            }
        }
    }

    private void socketConnect(String username, String ip, int port) throws GUIErrorException, GameStartedException, UserNameTakenException, IOException{
        ServerConnection serverConnection;
        Socket socket;
        //Opens socket and streams

        socket = new Socket(ip, port);
        serverConnection = new SocketServerConnection(socket);
        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());

        //sends the username to the server, checks the response
        in.nextLine();
        out.println(username);
        String response = in.nextLine();
        if(response.equals("A game is already started")) throw new GameStartedException();
        if(response.equals("Username already taken")) throw new UserNameTakenException();


        //Initiates controller, modelrep, vcguimessagecreator
        FXMLLoader loader = prepareLoadingScreen(); //initializes loader
        stage.show();
        GUIcontroller guiController = loader.getController();

        ModelRepresentation modelRep = new ModelRepresentation();
        VCMessageCreator vcMessageCreator = new VCMessageCreator(guiController, modelRep);
        guiController.displayMessage("Welcome to Sagrada, wait for other players.");
        guiController.setPlayerName(username);

        //registers observers and starts a connection thread
        guiController.init(modelRep);
        guiController.register(serverConnection);
        serverConnection.register(guiController);
        guiController.rawRegister(vcMessageCreator);
        Stage stage1 = (Stage) play.getScene().getWindow();
        stage1.close();
        new Thread((SocketServerConnection)serverConnection).start();

    }

    private void rmiConnect(String username, String ip) throws GUIErrorException{
        RMIClientConnection serverService;
        ModelRepresentation modelRep;
        VCMessageCreator vcMessageCreator;
        ClientPollingTimer timer;
        RMIServerConnection rmiServerConnection;
        try {
            //looks up for the rmiclientconnection and remote-calls the handleRequest() method

            serverService = (RMIClientConnection) Naming.lookup("//"+ ip + "/sagradarmi");
            rmiServerConnection = new RMIServerConnImpl(serverService);
            RMIServerConnection serverConnInt = (RMIServerConnection) UnicastRemoteObject.exportObject(rmiServerConnection,0);


            //instantiates modelrep and vcmessagecreator, registers observers
            modelRep = new ModelRepresentation();
            FXMLLoader loader = prepareLoadingScreen(); //initializes guiController
            GUIcontroller guiController = loader.getController();
            guiController.setPlayerName(username);
            guiController.init(modelRep);
            vcMessageCreator = new VCMessageCreator(guiController, modelRep);
            guiController.rawRegister(vcMessageCreator);
            guiController.register(new ServerConnectionAdapter(rmiServerConnection));
            rmiServerConnection.register(new SockToRMIObserverAdapter<>(guiController));

            //remote call for checkEnoughPlayers()
            serverService.handleRequest(username, serverConnInt); //throws exceptions
            serverService.checkEnoughPlayers();

            //starts the polling thread
            Stage stage1 = (Stage) play.getScene().getWindow();
            stage1.close();
            stage.show();
            timer = new ClientPollingTimer(serverService, guiController);
            timer.startPolling();
        }
        catch(NotBoundException |MalformedURLException |RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString());
        }
        catch(UserNameTakenException e){
            missing("Username already taken.");
        }
        catch(GameStartedException e){
            missing("Game already started. ");
        }
    }

    private void missing(String errorM){

            Scene window = new Scene(new Label(errorM), 463, 55);
            Stage stage1 = new Stage();
            stage1.setScene(window);
            stage1.setTitle("Error");
            stage1.setResizable(false);
            stage1.show();
    }

    private FXMLLoader prepareLoadingScreen() throws GUIErrorException{
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loading.fxml"));
            Scene window = new Scene(loader.load());
            stage = new Stage();
            stage.setScene(window);
            stage.setTitle("Loading");
            stage.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage.setResizable(false);
            stage.setOnCloseRequest(confirmCloseEventHandler );

            return loader;
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE,e.getMessage());
        }
        throw new GUIErrorException();

    }

    private EventHandler<WindowEvent> confirmCloseEventHandler = event -> {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/exitConfirmation.fxml"));
            Scene window = new Scene(loader.load());
            Stage stage1 = new Stage();
            stage1.setScene(window);
            stage1.setTitle("Exit");
            stage1.getIcons().add(new Image("https://d30y9cdsu7xlg0.cloudfront.net/png/14169-200.png"));
            stage1.setResizable(false);
            event.consume();
            stage1.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

    };
}
