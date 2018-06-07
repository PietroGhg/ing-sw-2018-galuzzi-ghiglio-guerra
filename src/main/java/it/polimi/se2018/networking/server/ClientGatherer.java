package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The client gatherer waits for incoming connections and calls the addClient method from the Server
 * @author Pietro Ghiglio
 */
public class ClientGatherer extends Thread {
    private final Server server;
    private static final Logger LOGGER = Logger.getLogger(ClientGatherer.class.getName());
    private ServerSocket serverSocket;
    private Controller controller;
    private PrintStream out;
    private Scanner in;

    /**
     * The constructor initializes the server and the ServerSocket
     * @param server the server
     * @param port port number
     */
    ClientGatherer(Server server, int port, Controller controller){
        this.server = server;
        this.controller = controller;

        try{
            this.serverSocket = new ServerSocket(port);
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Waits for new client connections and calls the server.addClient() method
     */
    @Override
    public void run(){
        boolean loop = true;
        while(loop){
            Socket newClientConnection;
            try{
                newClientConnection = serverSocket.accept();
                out = new PrintStream(newClientConnection.getOutputStream());
                in = new Scanner(newClientConnection.getInputStream());

                SocketClientConnection socketClientConnection = new SocketClientConnection(newClientConnection, server);
                String playerName = insertPlayerName();
                socketClientConnection.setPlayerName(playerName);
                try {
                    controller.handleRequest(playerName);
                    server.addClient(socketClientConnection, playerName);
                    sendString("Welcome to Sagrada, " + playerName);
                    controller.checkEnoughPlayers();
                }
                catch (GameStartedException e){
                    sendString("A game is already started");
                }
                catch (UserNameTakenException e) {
                    sendString("Username already taken");
                }
                catch (ReconnectionException e){
                    server.addClient(socketClientConnection, playerName);
                    sendString("Welcome back " + playerName);
                    controller.welcomeBack(playerName);
                }
            }
            catch(IOException e){
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    private String insertPlayerName(){
        String name;

        out.println("Insert player name.");
        out.flush();
        name = in.nextLine();

        return name;
    }

    private void sendString(String s){
        out.println(s);
    }
}
