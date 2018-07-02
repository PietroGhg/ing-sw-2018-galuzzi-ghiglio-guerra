package it.polimi.se2018.networking.server.rmi;

import it.polimi.se2018.networking.client.rmi.RMIServerConnection;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The ServerPollingTimer checks every 0.5 seconds that a player is connected by remote calling the poll() method
 * If a player is disconnected, calls the appropriate method from the server in order to handle the disconnection
 * @author Pietro Ghiglio
 */
public class ServerPollingTimer {
    private Timer timer;
    private PollingTask task;
    private RMIServer rmiServer;
    private RMIServerConnection clientService;
    private String playerName;

    public ServerPollingTimer(RMIServerConnection clientService, RMIServer rmiServer, String playerName){
        this.clientService = clientService;
        this.rmiServer = rmiServer;
        this.playerName = playerName;
    }

    public void startPolling(){
        timer = new Timer();
        task = new PollingTask();
        timer.scheduleAtFixedRate(task, 500, 500);
    }


    private class PollingTask extends TimerTask{

        @Override
        public void run(){
            try{
                clientService.poll();
            }
            catch(RemoteException e){
                cancel();
                timer.cancel();
                rmiServer.detachClient(playerName);
            }
        }
    }
}
