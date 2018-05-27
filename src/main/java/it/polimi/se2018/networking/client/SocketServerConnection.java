package it.polimi.se2018.networking.client;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.view.MVAbstractMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Implementation of the ServerConnection class
 * @author Pietro Ghiglio
 */
public class SocketServerConnection extends ServerConnection {
    private Socket socket;

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    public SocketServerConnection(Socket socket){
        this.socket = socket;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    //synchronized ??
    public void send(VCAbstractMessage message){
        //sends the message
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run(){
        //deserilizes MVAbstractMessages and notifies them
        boolean loop = true;
        MVAbstractMessage message;

        while(loop){
            try{
                message = (MVAbstractMessage)objectInputStream.readObject();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void update(VCAbstractMessage message){
        send(message);
    }
}