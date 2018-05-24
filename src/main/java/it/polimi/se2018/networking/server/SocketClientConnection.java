package it.polimi.se2018.networking.server;

import it.polimi.se2018.view.MVAbstractMessage;

import java.net.Socket;

/**
 * Implementation of the ClientConnection class
 */
public class SocketClientConnection extends ClientConnection {
    private Socket socket;

    public SocketClientConnection(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        //deserializes VCAbstractMessages and notifies them
    }

    public void send(MVAbstractMessage message){
        //send the message
    }
}
