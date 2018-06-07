package it.polimi.se2018.controller.states;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;

import java.util.Timer;


/**
 * states are used to handle player's connections and disconnections
 * @author Pietro Ghiglio
 */
public interface State {
    void handleRequest(String playerName, ModelFacade modelFacade) throws UserNameTakenException, GameStartedException, ReconnectionException;
    void handleDisconnection(String playerName, ModelFacade modelFacade, Timer timer, ConnectionTimer connectionTimer);
    State checkEnoughPlayers(ModelFacade modelFacade, Controller controller, Timer timer, ConnectionTimer connectionTimer);
}
