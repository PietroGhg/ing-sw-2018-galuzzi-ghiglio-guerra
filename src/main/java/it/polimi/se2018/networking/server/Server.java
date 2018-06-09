package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.table.Model;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The Server has an instance of the model and an instance of the controller.
 * A RemoteView object is instantiated as new players connect
 * @author Pietro Ghiglio
 */
public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private Model model;
    private Controller controller;
    private Map<String, RemoteView> remoteViewMap;

    /**
     * Instantiates the model and the controller, starts a ClientGatherer thread.
     * The ClientGatherer will call the addClient() method as new players connect.
     */
    public Server(int timerSeconds, int port, int turnDuration){
        model = new Model();
        controller = new Controller(model, timerSeconds, turnDuration);
        new ClientGatherer(this, port, controller).start();
        remoteViewMap = new HashMap<>();
    }

    /**
     * Instantiates a new RemoteView, registers the controller as an observer to the remoteview and the
     * remoteview as an observer to the model.
     * The remoteview is already registered as an observer to the connection in it's constructor.
     * @param connection the ClientConnection used by the RemoteView
     * @param playerName the name of the user
     */
    public void addClient(ClientConnection connection, String playerName){
        RemoteView remoteView = new RemoteView(connection);
        remoteView.register(controller);
        model.register(remoteView);

        remoteViewMap.put(playerName, remoteView);
        SocketClientConnection sockConn = (SocketClientConnection)connection;
        new Thread(sockConn).start();
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        LOGGER.log(Level.INFO, "Insert port number.");
        int port = in.nextInt();
        LOGGER.log(Level.INFO, "Insert timer duration [seconds].");
        int timerDuration = in.nextInt();
        LOGGER.log(Level.INFO, "Insert turn duration [minutes]");
        int turnDuration = in.nextInt();

        new Server(timerDuration, port, turnDuration);
    }

    /**
     * Method called when a client disconnects from the Server.
     * Deregisters the remoteview from the observers and removes it from the Map
     * The rest of the handling is done by the controller
     * @param playerName username of the disconnected player
     */
    public void detachClient(String playerName){
        RemoteView remoteView = remoteViewMap.get(playerName);
        model.deregister(remoteView);
        remoteViewMap.remove(playerName);
        controller.handleDisconnection(playerName);
    }
}
