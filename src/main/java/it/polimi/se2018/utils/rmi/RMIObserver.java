package it.polimi.se2018.utils.rmi;

import java.rmi.RemoteException;

/**
 * Interface for the RMI Observer
 */
public interface RMIObserver<T>  {
    void update(T message) throws RemoteException;
}
