package it.polimi.se2018.utils.rmi;

import java.rmi.RemoteException;

public interface RMIObserver<T>  {
    void update(T message) throws RemoteException;
}
