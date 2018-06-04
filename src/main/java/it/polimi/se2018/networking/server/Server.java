package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.table.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Server has an instance of the model and an instance of the controller.
 * A RemoteView object is instantiated as new players connect
 * @author Pietro Ghiglio
 */
public class Server {
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

        new Thread(connection).start();
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("Insert port number.");
        int port = in.nextInt();
        System.out.println("Insert timer duration [seconds].");
        int timerDuration = in.nextInt();
        System.out.println("Insert turn duration [minutes]");
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
