package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.networking.server.ClientConnection;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.view.MVAbstractMessage;
import java.rmi.RemoteException;

/**
 * Class that adapts an RMIClientConnection to a ClientConnection
 * @author Pietro Ghiglio
 */
public class ClientConnectionAdapter implements ClientConnection{
    private RMIClientConnection adaptee;

    public ClientConnectionAdapter(RMIClientConnection adaptee){
        this.adaptee = adaptee;
    }

    public void send(MVAbstractMessage message){
        try{
            adaptee.send(message);
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }

    public void register(Observer<VCAbstractMessage> observer){
        try {
            adaptee.register(new SockToRMIObserverAdapter<VCAbstractMessage>(observer));
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }
}
