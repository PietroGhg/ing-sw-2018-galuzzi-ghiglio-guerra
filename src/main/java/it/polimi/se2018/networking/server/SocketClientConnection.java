package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.view.MVAbstractMessage;

import java.io.*;
import java.net.Socket;

/**
 * Implementation of the ClientConnection class
 * @author Pietro Ghiglio
 */
public class SocketClientConnection extends ClientConnection {
    private Socket socket;

    private ObjectInputStream objectInputStream;

    private ObjectOutputStream objectOutputStream;

    public SocketClientConnection(Socket socket){

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
                message = (VCAbstractMessage) objectInputStream.readObject();
                notify(message);
            }
            catch(Exception e){
                System.out.println("Player disconnected");
                e.printStackTrace();
                //TODO: handling a player's disconnection
                loop = false;
            }
        }
    }

    //Synchronized???
    public void send(MVAbstractMessage message){
        //send the message
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
