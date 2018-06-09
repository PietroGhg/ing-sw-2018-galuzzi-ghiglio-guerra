package it.polimi.se2018.utils.rmi;

import it.polimi.se2018.utils.Observer;

import java.io.Serializable;
import java.util.logging.Logger;

public class SockToRMIObserverAdapter<T> implements RMIObserver<T>, Serializable {
    private static final Logger LOGGER = Logger.getLogger(SockToRMIObserverAdapter.class.getName());
    private Observer<T> adaptee;

    public SockToRMIObserverAdapter(Observer<T> observer){
        adaptee = observer;
    }

    public void update(T message) {
        adaptee.update(message);
    }
}
