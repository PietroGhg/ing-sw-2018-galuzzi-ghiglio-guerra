package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.utils.rmi.RMIObservable;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements the remote interface for the Server.
 * @author Pietro Ghiglio
 */
public class RMIClientConnImpl extends RMIObservable<VCAbstractMessage> implements RMIClientConnection {
    private static final Logger LOGGER = Logger.getLogger(RMIClientConnImpl.class.getName());
    private RMIServerConnection clientService;
    private RMIServer rmiServer;
    private String playerName;

    public RMIClientConnImpl(RMIServer rmiServer){
        this.rmiServer = rmiServer;
    }

    /**
     * Method that sends a MVAbstractMessage by remote-calling the notify method of the client.
     * If a RemoteException is throws while calling the notify() method, the server will start handling
     * the disconnection.
     * @param message the message to send
     */
    public void send(MVAbstractMessage message){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clientService.notify(message);
                }
                catch(RemoteException e){
                    LOGGER.log(Level.SEVERE, e.toString());
                    rmiServer.detachClient(playerName);
                }
            }
        }).start();
    }

    /**
     * Method called by the remote client in order to add a player to the game.
     * @param playerName the name of the player
     * @param clientService the client's remote interface
     * @throws UserNameTakenException if the username is already taken
     * @throws GameStartedException if a game is already started and the requesting client is not reconnecting.
     */
    public void handleRequest(String playerName, RMIServerConnection clientService) throws UserNameTakenException,
            GameStartedException{
        this.clientService = clientService;
        this.playerName = playerName;
        ServerPollingTimer pt = new ServerPollingTimer(clientService, rmiServer, playerName);
        pt.startPolling();
        rmiServer.handleRequest(playerName);

    }

    public void checkEnoughPlayers(){
        rmiServer.checkEnoughPlayers();
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void poll() {
        //the method does nothing, it's just called to check the connection.
    }

}
