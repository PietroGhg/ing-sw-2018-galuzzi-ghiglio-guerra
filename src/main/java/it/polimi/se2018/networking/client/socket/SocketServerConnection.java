package it.polimi.se2018.networking.client.socket;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.networking.client.ServerConnection;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.MVAbstractMessage;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for the implementation of the ServerConnection
 * @author Pietro Ghiglio
 */
public class SocketServerConnection extends Observable<MVAbstractMessage> implements ServerConnection,Runnable{
    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private static final Logger LOGGER = Logger.getLogger(SocketServerConnection.class.getName());

    public SocketServerConnection(Socket socket){
        try{
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void send(VCAbstractMessage message){
        //sends the message
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

    }

    @Override
    public void run(){
        //deserilizes MVAbstractMessages and notifies them
        boolean loop = true;
        MVAbstractMessage message;
        while(loop){
            try{
                message = (MVAbstractMessage)objectInputStream.readUnshared();
                notify(message);
            }
            catch(IOException|ClassNotFoundException e){
                LOGGER.log(Level.SEVERE, e.getMessage());
                loop = false;
            }
        }
    }

    public void update(VCAbstractMessage message){
        send(message);
    }
}
