package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.utils.Observer;
import it.polimi.se2018.view.*;

/**
 * RemoteView is the virtual view, it receives VCAbstractMessages from the network through it's ConnectionObserver object
 * and notifies them to it's observer (the controller).
 * RemoteView implements the abstract methods visit() by using the connection to send MVAbstractMessages to the
 * client's ServerConnection.
 * @author Pietro Ghiglio
 */
public class RemoteView extends AbstractView {
    private ClientConnection connection;
    private ConnectionObserver connectionObserver;

    public RemoteView(ClientConnection connection){
        this.connection = connection;
        //Instantiation of the connection observer, which is registered as an observer of the connection.
        connectionObserver = new ConnectionObserver(this);
        connection.register(connectionObserver);
    }

    public void visit(MVGameMessage message) {
        connection.send(message);
    }

    public void visit(MVSetUpMessage message) {
        connection.send(message);
    }

    public void visit(MVStartGameMessage message) { connection.send(message); }

    public void visit(MVNewTurnMessage message) { connection.send(message); }

    public void visit(MVWelcomeBackMessage message){ connection.send(message); }

    public void visit(MVTimesUpMessage message) { connection.send(message); }

    public void visit(MVTC6Message message) { connection.send(message); }

    /**
     * ConnectionObserver observes the ClientConnection, basically simulating the user's input to the View
     * (with the difference that VCAbstractmessages are already instantiated).
     */
    private class ConnectionObserver implements Observer<VCAbstractMessage>{
        RemoteView remoteView;

        /*package private*/ ConnectionObserver(RemoteView remoteView){
            this.remoteView = remoteView;
        }

        public void update(VCAbstractMessage message){
            remoteView.notify(message);
        }
    }
}
