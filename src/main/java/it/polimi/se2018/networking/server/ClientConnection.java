package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.MVAbstractMessage;

/**
 * The ClientConnection receives VCAbstractMessages from the network, and notifies them to the RemoteView
 * The remote view sends MVAbstractMessages through the network using the send() method of the ClientConnection.
 * @author Pietro Ghiglio
 */
public interface ClientConnection extends  Runnable {
    void send(MVAbstractMessage message);
    void register(Observer<VCAbstractMessage> observer);
}
