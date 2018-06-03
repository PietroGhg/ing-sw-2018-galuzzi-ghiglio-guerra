package it.polimi.se2018;

import it.polimi.se2018.model.Extractor;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.table.RoundTrack;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestRoundTrack {
    private Model model;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @Before
    public void setup(){
        Extractor.resetInstance();
        model = new Model();
        p1 = new Player(1);
        p2 = new Player(2);
        p3 = new Player(3);
        p4 = new Player(4);
        model.addPlayer(p1);
        model.addPlayer(p2);
        model.addPlayer(p3);
        model.addPlayer(p4);
        model.startGame();
    }

    @Test
    public void test(){
        assertEquals(1, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(2, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(3, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(4, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(4, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(3, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(2, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(1, model.whoIsPlaying());
        model.nextTurn();
        assertEquals(2, model.whoIsPlaying());
    }

}
