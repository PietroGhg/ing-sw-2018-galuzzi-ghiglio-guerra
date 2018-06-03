package it.polimi.se2018.networking.client;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.view.MVAbstractMessage;

import java.io.*;
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
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
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
                notify(message);
            }
            catch(EOFException e){
                System.out.println("mah");
            }
            catch(IOException|ClassNotFoundException e){
                e.printStackTrace();
                loop = false;
            }
        }
    }

    public void update(VCAbstractMessage message){
        send(message);
    }
}
