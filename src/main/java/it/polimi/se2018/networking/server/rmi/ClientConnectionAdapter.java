package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.networking.server.ClientConnection;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.view.MVAbstractMessage;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that adapts an RMIClientConnection to a ClientConnection:
 * the methods in RMIClientConnection need to throw a RemoteException, so RMIClientConnection cannot directly
 * extend the ClientConnection interface, an adapter is needed.
 * This class simply calls the remote methods and handles the RemoteExceptions internally, allowing compatibility
 * with a normal ClientConnection.
 * @author Pietro Ghiglio
 */
public class ClientConnectionAdapter implements ClientConnection{
    private static final Logger LOGGER = Logger.getLogger(ClientConnectionAdapter.class.getName());
    private RMIClientConnection adaptee;

    ClientConnectionAdapter(RMIClientConnection adaptee){
        this.adaptee = adaptee;
    }

    public void send(MVAbstractMessage message){
        try{
            adaptee.send(message);
        }
        catch(RemoteException e){
            String m = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, m);
        }
    }

    public void register(Observer<VCAbstractMessage> observer){
        try {
            adaptee.register(new SockToRMIObserverAdapter<>(observer));
        }
        catch(RemoteException e){
            String m = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, m);
        }
    }
}
