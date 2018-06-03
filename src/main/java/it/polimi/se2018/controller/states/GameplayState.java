package it.polimi.se2018.controller.states;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameNotFoundException;
import it.polimi.se2018.model.Player;


import java.util.ArrayList;
import java.util.Timer;

/**
 * In GameplayState, the program keeps track of the players that disconnect, so that they can later reconnect
 * It's not possible for a new player to connect to the game
 * @author Pietro Ghiglio
 */
public class GameplayState implements State {

    public void handleRequest(String playerName, ModelFacade model)throws GameStartedException, ReconnectionException{
        ArrayList<String> discPlayers = (ArrayList<String>)model.getDiscPlayers();

        if (discPlayers.contains(playerName)){
            reconnect(playerName, model);
            throw new ReconnectionException();
        }
        else throw new GameStartedException();
    }

    private void reconnect(String playerName, ModelFacade model){
        try{
            Player p = model.getPlayer(playerName);
            p.setDisconnected(false);
            model.removeDiscPlayer(playerName);
            model.addPlayerName(playerName);
            System.out.println(playerName + " rejoined");
        }
        catch (UserNameNotFoundException e){
            System.out.println("Error while handling player reconnection");
        }
    }

    public void handleDisconnection(String playerName, ModelFacade model, Timer timer, ConnectionTimer connectionTimer){
        try {
            Player p = model.getPlayer(playerName);
            p.setDisconnected(true);
            model.removePlayerName(playerName);
            model.addDiscPlayer(playerName);
        }
        catch (UserNameNotFoundException e){
            System.out.println("Error while handling player disconnection.");
        }
    }

    public State checkEnoughPlayers(ModelFacade model, Controller controller, Timer timer, ConnectionTimer connectionTimer){
        return new GameplayState();
    }
}