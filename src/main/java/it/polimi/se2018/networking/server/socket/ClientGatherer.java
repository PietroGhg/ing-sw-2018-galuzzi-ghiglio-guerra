package it.polimi.se2018.networking.server.socket;

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
    private final SocketServer socketServer;
    private static final Logger LOGGER = Logger.getLogger(ClientGatherer.class.getName());
    private ServerSocket serverSocket;
    private Controller controller;
    private PrintStream out;
    private Scanner in;

    /**
     * The constructor initializes the server and the ServerSocket
     * @param socketServer the server
     * @param port port number
     */
    ClientGatherer(SocketServer socketServer, int port, Controller controller){
        this.socketServer = socketServer;
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
            Socket socket;
            try{
                socket = serverSocket.accept();
                out = new PrintStream(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());

                SocketClientConnection socketClientConnection = new SocketClientConnection(socket, socketServer);
                String playerName = insertPlayerName();
                socketClientConnection.setPlayerName(playerName);
                try {
                    controller.handleRequest(playerName);
                    socketServer.addClient(socketClientConnection, playerName);
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
                    socketServer.addClient(socketClientConnection, playerName);
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
        sendString("Insert player name. ");
        name = in.nextLine();
        return name;
    }

    private void sendString(String s){
        out.println(s);
        out.flush();
    }
}
