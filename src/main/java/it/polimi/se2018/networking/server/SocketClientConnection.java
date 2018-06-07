package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.view.MVAbstractMessage;
import it.polimi.se2018.view.MVWelcomeBackMessage;

import java.io.*;
import java.net.Socket;

/**
 * Implementation of the ClientConnection class
 * @author Pietro Ghiglio
 */
public class SocketClientConnection extends ClientConnection {
    private Socket socket;
    private Server server;
    private String playerName;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    public SocketClientConnection(Socket socket, Server server){
        this.server = server;
        this.socket = socket;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            e.printStackTrace();
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
                System.out.println(playerName + " disconnected");
                server.detachClient(playerName);
                loop = false;
            }
        }
        try{
            socket.close();
        }
        catch(IOException e){
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }
}
