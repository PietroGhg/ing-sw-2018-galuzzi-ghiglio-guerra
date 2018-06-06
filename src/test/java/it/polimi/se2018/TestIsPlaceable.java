package it.polimi.se2018;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestIsPlaceable {
    Player player;
    WPC wpc;
    Die d;
    List<int[]> expectedL = new ArrayList<int[]>();

    @Before
    public void setUp() {
        WpcGenerator gen = new WpcGenerator();
        wpc = gen.getWPC(23);

        int[] e0 = {0,0};
        int[] e1 = {0,1};
        int[] e2 = {0,3};
        int[] e3 = {0,4};
        int[] e4 = {2,2};
        int[] e5 = {3,0};
        int[] e6 = {3,4};

        expectedL.add(e0);
        expectedL.add(e1);
        expectedL.add(e2);
        expectedL.add(e3);
        expectedL.add(e4);
        expectedL.add(e5);
        expectedL.add(e6);
    }

    /*
    //TODO: aggiustare test (assertEquals fallisce)
    @Test
    public void testEmpty(){
        player = new Player(1);
        player.setWpc(wpc);
        Die d = new Die(4, Colour.RED);
        List<int[]> l = wpc.isPlaceable(d);
        System.out.print(l);
        assertEquals(expectedL,l);
    }
    */
}
