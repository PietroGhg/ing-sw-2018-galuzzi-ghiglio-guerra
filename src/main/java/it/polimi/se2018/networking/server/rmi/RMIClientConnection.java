package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.utils.rmi.RMIObserver;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Class for RMI client connection, provides the remote interface of the server, used by the client
 * @author Pietro Ghiglio
 */
public interface RMIClientConnection extends Remote{

    void send(MVAbstractMessage message) throws RemoteException;

    void register(RMIObserver<VCAbstractMessage> observer) throws RemoteException;

    void notify(VCAbstractMessage message) throws RemoteException;

    /**
     * Method that gets remote-called by the client in order to try to add a player to the game
     * @param playerName the name of the player
     * @param client the remote-object for the client
     * @throws UserNameTakenException when the username is already taken
     * @throws GameStartedException when a game is already started
     */
    void handleRequest(String playerName, RMIServerConnection client) throws RemoteException, UserNameTakenException,
    GameStartedException;

    /**
     * Method remote-called by the client in order in order to check if, after it's own addition, there are enough players to start a game
     */
    void checkEnoughPlayers() throws RemoteException;

    /**
     * Method used to periodically check if the connection is working
     * @throws RemoteException when there are connection problems
     */
    void poll() throws RemoteException;
}
