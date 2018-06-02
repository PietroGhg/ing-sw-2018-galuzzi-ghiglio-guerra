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

/**
 * The client gatherer waits for incoming connections and calls the addClient method from the Server
 * @author Pietro Ghiglio
 */
public class ClientGatherer extends Thread {
    private final Server server;
    private int port;
    private ServerSocket serverSocket;
    private Controller controller;
    private PrintStream out;
    private Scanner in;

    /**
     * The constructor initializes the server and the ServerSocket
     * @param server the server
     * @param port port number
     */
    public ClientGatherer(Server server, int port, Controller controller){
        this.server = server;
        this.port = port;
        this.controller = controller;

        try{
            this.serverSocket = new ServerSocket(port);
        }
        catch (IOException e){
            e.printStackTrace();
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
                String playerName = insertPlayerName(newClientConnection);
                socketClientConnection.setPlayerName(playerName);
                try {
                    controller.handleRequest(playerName);
                    server.addClient(socketClientConnection, playerName);
                    sendString(newClientConnection, "Welcome to Sagrada, " + playerName);
                    controller.checkEnoughPlayers();
                }
                catch (GameStartedException e){
                    //notify the client
                    sendString(newClientConnection, "A game is already started");
                }
                catch (UserNameTakenException e) {
                    sendString(newClientConnection, "Username already taken");
                }
                catch (ReconnectionException e){
                    server.addClient(socketClientConnection, playerName);
                    sendString(newClientConnection, "Welcome back " + playerName);
                    controller.welcomeBack(playerName);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private String insertPlayerName(Socket socket) throws IOException{
        String name;

        out.println("Insert player name.");
        out.flush();
        name = in.nextLine();

        return name;
    }

    private void sendString(Socket socket, String s){
        out.println(s);
    }
}
