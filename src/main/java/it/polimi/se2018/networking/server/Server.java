package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.model.table.Model;

/**
 * The Server has an instance of the model and an instance of the controller.
 * A RemoteView object is instantiated as new players connect
 * @author Pietro Ghiglio
 */
public class Server {
    private Model model;
    private Controller controller;
    private final int port = 12345;

    /**
     * Instantiates the model and the controller, starts a ClientGatherer thread.
     * The ClientGatherer will call the addClient() method as new players connect.
     */
    public Server(){
        model = new Model();
        controller = new Controller(model);
        new ClientGatherer(this, port).start();
    }

    /**
     * Instantiates a new RemoteView, registers the controller as an observer to the remoteview and the
     * remoteview as an observer to the model.
     * The remoteview is already registered as an observer to the connection in it's constructor.
     * @param connection the ClientConnection used by the RemoteView
     */
    public void addClient(ClientConnection connection) throws GameStartedException{
        RemoteView remoteView = new RemoteView(connection);
        remoteView.register(controller);
        model.register(remoteView);
        model.addPlayer();
        new Thread(connection).start();
    }
}
