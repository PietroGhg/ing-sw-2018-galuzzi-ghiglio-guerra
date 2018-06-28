package it.polimi.se2018;

import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;
import org.junit.Test;

public class TestModel {
    private Model model;

    @Test
    public void test(){
        model = new Model();
        Player player;
        player = new Player(1);
        player.setDisconnected(true);
        player.setSkipTurn(true);
        System.out.println(player.isDisconnected());
        System.out.println(player.getSkipTurn());
        model.addPlayer("leo");
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        model.startGame();
        model.nextTurn();
    }
}
