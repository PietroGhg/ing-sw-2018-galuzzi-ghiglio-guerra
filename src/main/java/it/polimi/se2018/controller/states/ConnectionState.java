package it.polimi.se2018.controller.states;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.table.Model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ConnectionState acts as a game lobby, while in this state, players can connect and disconnect freely
 * The transition between ConnectionState and GameplayState takes place when either 4 players connect
 * or the Server's timer end it's countdown
 * @author Pietro Ghiglio
 */
public class ConnectionState implements State{
    public void handleRequest(String playerName, ModelFacade model) throws UserNameTakenException{
        ArrayList<String> playerNames = (ArrayList<String>)model.getPlayerNames();

        if (playerNames.contains(playerName)) throw new UserNameTakenException();
        else model.addPlayer(playerName);
    }

    public void handleDisconnection(String playerName, ModelFacade model, Timer timer, ConnectionTimer connectionTimer){

        if(model.getPlayersNumber() - 1 < 2){
            if(connectionTimer.isScheduled()){
                connectionTimer.cancel();
                System.out.println("Timer stopped");
            }
        }
        model.removePlayer(playerName);
        System.out.println(playerName + " left the lobby");
    }

    public State checkEnoughPlayers(ModelFacade model, Controller controller, Timer timer, ConnectionTimer connectionTimer){
        int timerDuration = model.getTimerDuration();
        if (model.getPlayersNumber() >= 2) {
            //If timer was running, restarts the timer (when a new player connects)
            if(connectionTimer.isScheduled()) {
                connectionTimer.cancel();
                TimerTask newTimerTask = connectionTimer.newTask();
                timer.schedule(newTimerTask, (long) timerDuration * 1000);
                System.out.println("Timer restarted.");
            }
            else{ //start the timer
                TimerTask newTimerTask = connectionTimer.newTask();
                connectionTimer.setScheduled(true);
                timer.schedule(newTimerTask, (long)timerDuration * 1000);
                System.out.println("Timer started: " + (newTimerTask.scheduledExecutionTime() - System.currentTimeMillis()));
            }
            return new ConnectionState();
        }
        if (model.getPlayersNumber() == Model.MAX_PLAYERS){
            controller.startGame();
            return new GameplayState();
        }
        return new ConnectionState();
    }


}
