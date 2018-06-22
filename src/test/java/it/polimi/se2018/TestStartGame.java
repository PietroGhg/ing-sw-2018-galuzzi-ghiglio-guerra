package it.polimi.se2018;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.VCEndTurnMessage;
import it.polimi.se2018.controller.VCToolMessage;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Extractor;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestStartGame {
    private Model model;
    private Controller controller;
    private WpcGenerator generator;
    Player player1;
    Player player2;
    Player player3;

    @Before
    public void setup(){
        Extractor.resetInstance();
        model = new Model();
        generator = WpcGenerator.getInstance();
        player1 = new Player(1);
        player1.setWpc(generator.getWPC(1));
        player1.setFavorTokens(5);
        player2 = new Player(2);
        player2.setWpc(generator.getWPC(2));
        player2.setFavorTokens(5);
        player3 = new Player(3);
        player3.setWpc(generator.getWPC(3));
        player3.setFavorTokens(5);

        model.addPlayer(player1);
        model.addPlayer(player2);
        model.addPlayer(player3);

        controller = new Controller(model, 10,10);
    }

    /**
     * Tests that a roundmatrix is correctly instantiated when the game starts
     */
    @Test
    public void test() {
        model.startGame();
        int[][] expectedRM = {
                {1, 2, 3, 3, 2, 1},
                {2, 3, 1, 1, 3, 2},
                {3, 1, 2, 2, 1, 3},
                {1, 2, 3, 3, 2, 1},
                {2, 3, 1, 1, 3, 2},
                {3, 1, 2, 2, 1, 3},
                {1, 2, 3, 3, 2, 1},
                {2, 3, 1, 1, 3, 2},
                {3, 1, 2, 2, 1, 3},
                {1, 2, 3, 3, 2, 1}
        };

        for (int i = 0; i < 10; i++){
            for(int j = 0; j < 6; j++){
                assertEquals(expectedRM[i][j], model.getRoundMatrix()[i][j]);
            }
        }
    }

    /**
     * Tests that a turn is correctly ended
     */
    @Test
    public void test2(){
        model.startGame();

        //the turn should not be ended since the player is not the current
        VCAbstractMessage message = new VCEndTurnMessage(player2.getPlayerID());
        controller.update(message);
        assertEquals(1, model.whoIsPlaying());

        //the turn should be ended
        message = new VCEndTurnMessage(player1.getPlayerID());
        controller.update(message);
        assertEquals(2, model.whoIsPlaying());
    }
}
