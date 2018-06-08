package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.MVAbstractMessage;


import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ClientConnection class
 * @author Pietro Ghiglio
 */
public class SocketClientConnection extends Observable<VCAbstractMessage> implements ClientConnection{
    private Socket socket;
    private Server server;
    private String playerName;
    private static final Logger LOGGER = Logger.getLogger(SocketClientConnection.class.getName());

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    SocketClientConnection(Socket socket, Server server){
        this.server = server;
        this.socket = socket;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void run() {
        //deserializes VCAbstractMessages and notifies them
        VCAbstractMessage message;
        boolean loop = true;

        while(loop){
            try{
                message = (VCAbstractMessage) objectInputStream.readUnshared();
                notify(message);
            }
            catch(IOException|ClassNotFoundException e){
                LOGGER.log(Level.INFO, playerName + " disconnected");
                server.detachClient(playerName);
                loop = false;
            }
        }
        try{
            socket.close();
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    //Synchronized???
    public void send(MVAbstractMessage message){
        //send the message
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
}
