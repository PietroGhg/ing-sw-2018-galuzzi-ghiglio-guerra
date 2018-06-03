package it.polimi.se2018.controller.states;

import it.polimi.se2018.exceptions.UserNameNotFoundException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class acts as a facade of the model passed as parameter to the State's methods.
 * It provides all and only the model's methods that could by used while handling connections and disconnections,
 * without giving an explicit reference to the model.
 * @author Pietro Ghiglio
 */
public class ModelFacade {
    private Model model;
    private int timerDuration;

    public ModelFacade(Model model, int timerDuration){
        this.model = model;
        this.timerDuration = timerDuration;
    }

    public List<String> getPlayerNames(){ return model.getPlayerNames(); }

    public List<String> getDiscPlayers() { return model.getDiscPlayers(); }

    public void addPlayer(String playerName){ model.addPlayer(playerName); }

    public int getPlayersNumber() { return model.getPlayersNumber(); }

    public void addDiscPlayer(String playerName){ model.addDiscPlayer(playerName); }

    public void removeDiscPlayer(String playerName){ model.removeDiscPlayer(playerName); }

    public void addPlayerName(String playerName){ model.addPlayerName(playerName); }

    public void removePlayerName(String playerName){ model.removePlayerName(playerName); }

    public void removePlayer(String playerName) { model.removePlayer(playerName); }

    public Player getPlayer(String playerName) throws UserNameNotFoundException{ return model.getPlayer(playerName); }

    public int getTimerDuration(){ return timerDuration; }

    public void startGame(){ model.startGame(); }

}
