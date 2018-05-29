package it.polimi.se2018.networking.server;

import it.polimi.se2018.exceptions.GameStartedException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The client gatherer waits for incoming connections and calls the addClient method from the Server
 * @author Pietro Ghiglio
 */
public class ClientGatherer extends Thread {
    private final Server server;
    private int port;
    private ServerSocket serverSocket;

    /**
     * The constructor initializes the server and the ServerSocket
     * @param server the server
     * @param port port number
     */
    public ClientGatherer(Server server, int port){
        this.server = server;
        this.port = port;

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
                SocketClientConnection socketClientConnection = new SocketClientConnection(newClientConnection);

                try {
                    server.addClient(socketClientConnection);
                }
                catch (GameStartedException e){
                    //notify the client
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
