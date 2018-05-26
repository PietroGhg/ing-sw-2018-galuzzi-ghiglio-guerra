package it.polimi.se2018.networking.client;

import it.polimi.se2018.controller.VCAbstractMessage;

import java.net.Socket;

public class SocketServerConnection extends ServerConnection {
    private Socket socket;

    public SocketServerConnection(Socket socket){
        this.socket = socket;
    }

    public void send(VCAbstractMessage message){
        //sends the message
    }

    @Override
    public void run(){
        //deserilizes MVAbstractMessages and notifies them
    }

    public void update(VCAbstractMessage message){
        send(message);
    }
}
