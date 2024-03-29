package it.polimi.se2018.networking.client.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.networking.server.rmi.RMIClientConnection;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.utils.rmi.RMIObservable;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements client remote interface
 */
public class RMIServerConnImpl extends RMIObservable<MVAbstractMessage> implements RMIServerConnection {
    private RMIClientConnection serverService;
    private static final Logger LOGGER = Logger.getLogger(RMIServerConnImpl.class.getName());

    public RMIServerConnImpl(RMIClientConnection serverService){
        this.serverService = serverService;
    }

    /**
     * Remote calls the server notify() method
     * @param message the VCAbstractMessage
     */
    public void send(VCAbstractMessage message){
        try{
            serverService.notify(message);
        }
        catch(RemoteException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void update(VCAbstractMessage message){
        send(message);
    }

    public void register(Observer<MVAbstractMessage> observer){
        try {
            super.register(new SockToRMIObserverAdapter<MVAbstractMessage>(observer));
        }
        catch(RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    /**
     * method called to check the connection
     */
    public void poll(){

    }

}
