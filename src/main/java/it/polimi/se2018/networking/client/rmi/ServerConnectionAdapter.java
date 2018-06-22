package it.polimi.se2018.networking.client.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.networking.client.ServerConnection;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that adapts an RMIServerConnection to a ServerConnection.
 * See ClientConnectionAdapter for more details.
 */
public class ServerConnectionAdapter implements ServerConnection {
    private static final Logger LOGGER = Logger.getLogger(ServerConnectionAdapter.class.getName());
    private RMIServerConnection adaptee;

    public ServerConnectionAdapter(RMIServerConnection adaptee){
        this.adaptee = adaptee;
    }

    public void update(VCAbstractMessage message){
        send(message);
    }

    public void send(VCAbstractMessage message){
        try{
            adaptee.send(message);
        }
        catch (RemoteException e){
            String m = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, m);
        }
    }

    public void register(Observer<MVAbstractMessage> observer){
        try {
            adaptee.register(new SockToRMIObserverAdapter<MVAbstractMessage>(observer));
        }
        catch(RemoteException e){
            String m = Arrays.toString(e.getStackTrace());
            LOGGER.log(Level.SEVERE, m);
        }
    }
}
