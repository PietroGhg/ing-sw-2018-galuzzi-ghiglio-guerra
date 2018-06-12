package it.polimi.se2018.controller.states;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.model.table.Model;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ConnectionState acts as a game lobby, while in this state, players can connect and disconnect freely:
 * while handling a connection request, the controller simply checks if the username is already taken.
 * in case of disconnection, the record of the player (his name, basically) is deleted.
 * The transition between ConnectionState and GameplayState takes place when either 4 players connect
 * or the Server's timer end it's countdown
 * @author Pietro Ghiglio
 */
public class ConnectionState implements State{
    private static final Logger LOGGER = Logger.getLogger(ConnectionState.class.getName());
    public void handleRequest(String playerName, ModelFacade model) throws UserNameTakenException{
        ArrayList<String> playerNames = (ArrayList<String>)model.getPlayerNames();

        if (playerNames.contains(playerName)) throw new UserNameTakenException();
        else model.addPlayer(playerName);
    }

    public void handleDisconnection(String playerName, ModelFacade model, Timer timer, ConnectionTimer connectionTimer){

        if(model.getPlayersNumber() - 1 < 2 && connectionTimer.isScheduled()){
            connectionTimer.cancel();
            LOGGER.log(Level.INFO, "Timer stopped");
        }
        model.removePlayer(playerName);
        String s = playerName + " left the lobby";
        LOGGER.log(Level.INFO, s);
    }

    public State checkEnoughPlayers(ModelFacade model, Controller controller, Timer timer, ConnectionTimer connectionTimer){
        int timerDuration = model.getTimerDuration();
        if (model.getPlayersNumber() >= 2) {
            //If timer was running, restarts the timer (when a new player connects)
            if(connectionTimer.isScheduled()) {
                connectionTimer.cancel();
                TimerTask newTimerTask = connectionTimer.newTask();
                timer.schedule(newTimerTask, (long) timerDuration * 1000);
                LOGGER.log(Level.INFO, "Timer restarted.");
            }
            else{ //start the timer
                TimerTask newTimerTask = connectionTimer.newTask();
                connectionTimer.setScheduled(true);
                timer.schedule(newTimerTask, (long)timerDuration * 1000);
                String s = "Timer started: " + (newTimerTask.scheduledExecutionTime() - System.currentTimeMillis());
                LOGGER.log(Level.INFO, s);
            }
        }
        if (model.getPlayersNumber() == Model.MAX_PLAYERS){
            controller.startGame();
            return new GameplayState();
        }
        return new ConnectionState();
    }


}
