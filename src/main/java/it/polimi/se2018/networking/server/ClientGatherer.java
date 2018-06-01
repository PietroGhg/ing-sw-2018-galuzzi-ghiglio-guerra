package it.polimi.se2018.networking.server;

import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.table.Model;

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
    private Model model;
    private PrintStream out;
    private Scanner in;

    /**
     * The constructor initializes the server and the ServerSocket
     * @param server the server
     * @param port port number
     */
    public ClientGatherer(Server server, int port, Model model){
        this.server = server;
        this.port = port;
        this.model = model;

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

                SocketClientConnection socketClientConnection = new SocketClientConnection(newClientConnection);
                String playerName = insertPlayerName(newClientConnection);
                try {
                    model.handleRequest(playerName);
                    server.addClient(socketClientConnection);
                    System.out.println(playerName + " joined");
                    sendString(newClientConnection, "Welcome to Sagrada, " + playerName);
                    model.checkEnoughPlayers();
                }
                catch (GameStartedException e){
                    //notify the client
                    sendString(newClientConnection, "A game is already started");
                }
                catch (UserNameTakenException e) {
                    sendString(newClientConnection, "Username already taken");
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
