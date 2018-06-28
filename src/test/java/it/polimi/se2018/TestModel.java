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
        assertEquals(0, model.getDiscPlayers().size());
        //TODO: controlla che sia uguale a [leo, pietro, andrea]
        model.getPlayerNames();
        //TODO: controlla che ci sia gio
        model.addDiscPlayer("gio");
        model.removeDiscPlayer("gio");
        model.addPlayerName("gigi");
        model.removePlayerName("gigi");
        assertEquals(3, model.numActivePlayers());
        try{
            assertEquals("leo", model.getPlayer("leo").getName());
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
        assertEquals(1,model.getPlayer(1).getWpc().getId());
        model.setDiscMessage("bye");
        model.setReady(1);
        //TODO: controlla che il giocatore 1 abbia flagReady a 1

        assertEquals(false, model.allReady());
        model.setReady(1);
        model.setReady(2);
        assertEquals(true, model.allReady());
    }
}
