package it.polimi.se2018.controller.states;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameNotFoundException;
import it.polimi.se2018.model.Extractor;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BoardSelectionState implements State {
    private static final Logger LOGGER = Logger.getLogger(BoardSelectionState.class.getName());

    public BoardSelectionState(){
        LOGGER.log(Level.INFO, "Entered board selection state.");
    }

    public void handleRequest(String playerName, ModelFacade model) throws GameStartedException{
        throw new GameStartedException();
    }

    /**
     * If a player disconnects before choosing a wpc, a random generated one will be randomly assigned to him
     * @param playerName the player name
     * @param model the model facade
     * @param timer unused
     * @param ct unused
     */
    public void handleDisconnection(String playerName, Controller c, ModelFacade model, Timer timer, ConnectionTimer ct){
        try {
            Player p = model.getPlayer(playerName);
            Extractor extractor = Extractor.getInstance();
            WpcGenerator generator = WpcGenerator.getInstance();
            if(!p.isReady()){
                int id = extractor.extractOneWpc();
                WPC wpc = generator.getWPC(id);
                p.setWpc(wpc);
                p.setReady();
                model.removePlayerName(playerName);
                model.addDiscPlayer(playerName);
                String s = playerName + " disconnected, wpc automatically assigned";
                LOGGER.log(Level.INFO,s);
                c.checkAllReady();
            }
        }
        catch(UserNameNotFoundException e){
            LOGGER.log(Level.SEVERE, "Error while handling player disconnection");
        }
    }

    public State checkEnoughPlayers(ModelFacade model, Controller controller, Timer t, ConnectionTimer ct){
        LOGGER.log(Level.SEVERE, "Error: checkEnoughPlayers called while in board selection state");
        return new BoardSelectionState();
    }
}
