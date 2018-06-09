package it.polimi.se2018.networking.client.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.rmi.RMIObserver;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServerConnection extends Remote {
    void send(VCAbstractMessage message) throws RemoteException;
    void register(RMIObserver<MVAbstractMessage> observer) throws RemoteException;
    void notify(MVAbstractMessage message) throws RemoteException;
}
