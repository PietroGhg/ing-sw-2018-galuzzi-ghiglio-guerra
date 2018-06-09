package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.utils.rmi.RMIObservable;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RMIClientConnImpl extends RMIObservable<VCAbstractMessage> implements RMIClientConnection {
    private static final Logger LOGGER = Logger.getLogger(RMIClientConnImpl.class.getName());
    private RMIServerConnection clientService;
    private RMIServer rmiServer;

    public RMIClientConnImpl(RMIServer rmiServer){
        this.rmiServer = rmiServer;
    }

    public void send(MVAbstractMessage message){
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.log(Level.INFO, "Sending message: " + message + " To " + message.getPlayerID());
                    clientService.notify(message);
                }
                catch(RemoteException e){
                    LOGGER.log(Level.SEVERE, e.toString());
                }
            }
        }).start();*/
        try {
            LOGGER.log(Level.INFO, this.toString());
            LOGGER.log(Level.INFO, "Sending message: " + message + " To " + message.getPlayerID());
            clientService.notify(message);
        }
        catch(RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    public void handleRequest(String playerName, RMIServerConnection clientService) throws UserNameTakenException,
            GameStartedException, ReconnectionException{
        this.clientService = clientService;
        rmiServer.handleRequest(playerName);
    }


}
