package it.polimi.se2018;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.VCDieMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestController {
    private Controller controller;

    @Test
    public void test(){
        Model model = new Model();
        controller = new Controller(model,30,30);
        model.addPlayer("leo");
        Player p = new Player(1);
        controller.startGame();
        controller.checkEnoughPlayers();
    }
}
