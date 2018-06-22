package it.polimi.se2018.networking.client.rmi;

import it.polimi.se2018.networking.server.rmi.RMIClientConnection;
import it.polimi.se2018.view.ViewInterface;

import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The polling timer calls the poll() method from the server
 * and notifies the user when the server becomes unreachable.
 * The poll() method is called every 0.5 seconds
 * @author Pietro Ghiglio
 */
public class ClientPollingTimer {
    private Timer timer;
    private RMIClientConnection serverService;
    private ViewInterface view;

    public ClientPollingTimer(RMIClientConnection serverService, ViewInterface view){
        this.serverService = serverService;
        this.view = view;
    }

    public void startPolling(){
        PollingTask task;
        task = new PollingTask();
        timer = new Timer();
        timer.scheduleAtFixedRate(task, 500, 500);
    }

    private class PollingTask extends TimerTask{

        @Override
        public void run(){
            try {
                serverService.poll();
            }
            catch(RemoteException e){
                cancel();
                timer.cancel();
                view.displayMessage("Problem reaching the server. ");
            }
        }
    }
}
