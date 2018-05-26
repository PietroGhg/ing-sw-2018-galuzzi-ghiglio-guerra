package it.polimi.se2018.networking.client;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.MVAbstractMessage;

/**
 * ServerConnection notifies MVAbstractMessages to the user's view, and gets notified by the view VCAbstractMessages
 * that are sent through the network to the server's ClientConnection.
 * @author Pietro Ghiglio
 */
public abstract class ServerConnection extends Observable<MVAbstractMessage> implements Observer<VCAbstractMessage>, Runnable {
    public abstract void send(VCAbstractMessage message);
}
