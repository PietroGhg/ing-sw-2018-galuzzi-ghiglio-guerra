package it.polimi.se2018.networking;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observable;
import it.polimi.se2018.view.MVAbstractMessage;

/**
 * The ClientConnection receives VCAbstractMessages from the network, and notifies them to the RemoteView
 * The remote view sends MVAbstractMessages through the network using the send() method of the ClientConnection.
 * @author Pietro Ghiglio
 */
public abstract class ClientConnection extends Observable<VCAbstractMessage> {
    public abstract void send(MVAbstractMessage message);
}
