package it.polimi.se2018;

import it.polimi.se2018.exceptions.UserNameNotFoundException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestModel {
    private Model model;

    @Test
    public void test(){
        model = new Model();
        Player player = new Player(1);
        player.setDisconnected(false);
        player.setSkipTurn(false);
        player.setDisconnected(true);
        player.setSkipTurn(true);
        model.addPlayer("leo");
        model.addPlayer("pietro");
        model.addPlayer("andrea");
        model.startGame();
        model.nextTurn();
        model.getDiscPlayers();
        model.getPlayerNames();
        model.addDiscPlayer("gio");
        model.removeDiscPlayer("gio");
        model.addPlayerName("gigi");
        model.removePlayerName("gigi");
        assertEquals(3, model.numActivePlayers());
        try{
            model.getPlayer("leo");
        }
        catch (UserNameNotFoundException e){
            fail();
        }
        model.removePlayer("leo");
        try{
            model.getPlayer("leo");
            fail();
        }
        catch (UserNameNotFoundException e){

        }

        model.setStartGameMessage("ciao", 1);
        model.setWelcomeBackMessage(1,"leo","bentornato");
        model.setWinnerMessage("leo");

        model.setMVTimesUpMessage();
        model.setWpc(1,1);
        model.setDiscMessage("bye");
        model.setReady(1);
        assertEquals(false, model.allReady());
        model.setReady(1);
        model.setReady(2);
        assertEquals(true, model.allReady());
    }
}
