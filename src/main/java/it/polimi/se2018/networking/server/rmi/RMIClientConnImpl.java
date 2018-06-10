package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.utils.rmi.RMIObservable;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMIClientConnImpl extends RMIObservable<VCAbstractMessage> implements RMIClientConnection {
    private static final Logger LOGGER = Logger.getLogger(RMIClientConnImpl.class.getName());
    private RMIServerConnection clientService;
    private RMIServer rmiServer;
    private String playerName;

    public RMIClientConnImpl(RMIServer rmiServer){
        this.rmiServer = rmiServer;
    }

    public void send(MVAbstractMessage message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientService.notify(message);
                }
                catch(RemoteException e){
                    LOGGER.log(Level.SEVERE, e.toString());
                    rmiServer.detachClient(playerName);
                }
            }
        }).start();
    }

    public void handleRequest(String playerName, RMIServerConnection clientService) throws UserNameTakenException,
            GameStartedException{
        this.clientService = clientService;
        this.playerName = playerName;
        ServerPollingTimer pt = new ServerPollingTimer(clientService, rmiServer, playerName);
        pt.startPolling();
        rmiServer.handleRequest(playerName);

    }

    public void checkEnoughPlayers(){
        rmiServer.checkEnoughPlayers();
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void poll() {
        //the method does nothing, it's just called to check the connection.
    }

}
