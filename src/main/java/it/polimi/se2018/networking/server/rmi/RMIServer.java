package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.networking.server.RemoteView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer {
    private Model model;
    private Controller controller;
    private static final Logger LOGGER = Logger.getLogger(RMIServer.class.getName());
    private RMIClientConnection serverInt;
    private RMIClientConnImpl serverImpl;
    private Map<String, RemoteView> remoteViewMap;

    public RMIServer(Model model, Controller controller, int port){
        this.model = model;
        remoteViewMap = new HashMap<>();
        this.controller = controller;
        try{
            LocateRegistry.createRegistry(port);
        }
        catch(RemoteException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        try {
            serverImpl = new RMIClientConnImpl(this);
            serverInt = (RMIClientConnection) UnicastRemoteObject.exportObject(serverImpl, 0);
            Naming.rebind("//localhost/sagradarmi", serverInt);
        }
        catch(RemoteException|MalformedURLException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRequest(String playerName) throws GameStartedException, UserNameTakenException{
        try{
            controller.handleRequest(playerName);
            RemoteView remoteView = new RemoteView(new ClientConnectionAdapter(serverImpl));
            remoteView.register(controller);
            model.register(remoteView);
            remoteViewMap.put(playerName, remoteView);
        }
        catch(ReconnectionException e){
            RemoteView remoteView = new RemoteView(new ClientConnectionAdapter(serverImpl));
            remoteView.register(controller);
            model.register(remoteView);
            remoteViewMap.put(playerName, remoteView);
            controller.welcomeBack(playerName);
        }

        /**
         * Rebinds the client connection implementation in order to have an unique communication canal between server and client
         */
        try {
            Naming.unbind("//localhost/sagradarmi");
            serverImpl = new RMIClientConnImpl(this);
            serverInt = (RMIClientConnection) UnicastRemoteObject.exportObject(serverImpl, 0);
            Naming.rebind("//localhost/sagradarmi", serverInt);
        }
        catch(RemoteException|NotBoundException|MalformedURLException e){
            e.printStackTrace();
        }
    }

    public void checkEnoughPlayers(){
        controller.checkEnoughPlayers();
    }

    public void detachClient(String playerName){
        RemoteView remoteView = remoteViewMap.get(playerName);
        model.deregister(remoteView);
        remoteViewMap.remove(playerName);
        controller.handleDisconnection(playerName);
    }
}
