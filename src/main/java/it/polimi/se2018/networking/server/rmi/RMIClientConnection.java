package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.rmi.RMIServerConnection;
import it.polimi.se2018.utils.rmi.RMIObserver;
import it.polimi.se2018.view.MVAbstractMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientConnection extends Remote{

    void send(MVAbstractMessage message) throws RemoteException;

    void register(RMIObserver<VCAbstractMessage> observer) throws RemoteException;

    void notify(VCAbstractMessage message) throws RemoteException;

    void handleRequest(String playerName, RMIServerConnection client) throws RemoteException, UserNameTakenException,
    GameStartedException, ReconnectionException;

    void checkEnoughPlayers() throws RemoteException;
}
