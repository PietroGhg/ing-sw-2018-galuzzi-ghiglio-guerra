package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.networking.server.RemoteView;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer {
    private Model model;
    private Controller controller;
    private static final int PORT = 1099;
    private static final Logger LOGGER = Logger.getLogger(RMIServer.class.getName());
    private RMIClientConnection serverInt;
    private RMIClientConnImpl serverImpl;

    private RMIServer(){
        model = new Model();
        controller = new Controller(model,120, 10);
        try{
            LocateRegistry.createRegistry(PORT);
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

    public void handleRequest(String playerName) throws GameStartedException, UserNameTakenException, ReconnectionException{
        controller.handleRequest(playerName);
        RemoteView remoteView = new RemoteView(new ClientConnectionAdapter(serverImpl));
        remoteView.register(controller);
        model.register(remoteView);
        controller.checkEnoughPlayers();
    }

    public static void main(String[] args){
        new RMIServer();
    }
}
