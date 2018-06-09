package it.polimi.se2018.utils.rmi;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class RMIObservable<T> {
    private final List<RMIObserver<T>> observers = new ArrayList<>();

    public void register(RMIObserver<T> observer) throws RemoteException{
        synchronized (observers) {
            observers.add(observer);
        }
    }

    public void deregister(RMIObserver<T> observer) throws RemoteException{
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    public void notify(T message) throws RemoteException{
        synchronized (observers) {
            for(RMIObserver<T> observer : observers){
                observer.update(message);
            }
        }
    }
}
