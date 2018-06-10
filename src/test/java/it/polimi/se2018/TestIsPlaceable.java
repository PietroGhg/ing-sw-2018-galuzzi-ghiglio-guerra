package it.polimi.se2018;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for WPC method isPlaceable
 * @author Leonardo Guerra
 */

public class TestIsPlaceable {
    private Player player;
    private WPC empty;
    private WPC noSpots;
    private WPC oneSpot;
    private Die d; //die to place
    private List<int[]> expectedListWithEmpty = new ArrayList<int[]>();
    private List<int[]> expectedListOne = new ArrayList<int[]>();

    @Before
    public void setUp() {
        WpcGenerator gen = new WpcGenerator();
        empty = gen.getWPC(23); //Lux Mundi

        noSpots = gen.getWPC(23);
        fillerNo(noSpots);

        oneSpot = gen.getWPC(23);
        fillerOne(oneSpot);

        int[] e0 = {0,0};
        int[] e1 = {0,1};
        int[] e2 = {0,3};
        int[] e3 = {0,4};
        //not (2,2) because it's the first move and the die can be placed only on the border
        int[] e4 = {3,0};
        int[] e5 = {3,4};

        expectedListWithEmpty.add(e0);
        expectedListWithEmpty.add(e1);
        expectedListWithEmpty.add(e2);
        expectedListWithEmpty.add(e3);
        expectedListWithEmpty.add(e4);
        expectedListWithEmpty.add(e5);

        int[] o1 = {0,4};
        expectedListOne.add(o1);
    }

    public void fillerNo(WPC wpc){
        wpc.setDie(0, 0, new Die(1, Colour.PURPLE));
        wpc.setDie(0, 1, new Die(2, Colour.BLUE));
        wpc.setDie(0, 3, new Die(3, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(4, Colour.GREEN));
        wpc.setDie(2, 2, new Die(3, Colour.YELLOW));
        wpc.setDie(3, 0, new Die(6, Colour.RED));
        wpc.setDie(3, 4, new Die(2, Colour.PURPLE));
    }

    public void fillerOne(WPC wpc){
        wpc.setDie(0, 0, new Die(1, Colour.PURPLE));
        wpc.setDie(0, 1, new Die(2, Colour.BLUE));
        wpc.setDie(0, 3, new Die(3, Colour.YELLOW));
        wpc.setDie(2, 2, new Die(3, Colour.YELLOW));
        wpc.setDie(3, 0, new Die(6, Colour.RED));
        wpc.setDie(3, 4, new Die(2, Colour.PURPLE));
    }

    /**
     * Test with the empty board
     */
    @Test
    public void testEmpty(){
        player = new Player(1);
        player.setWpc(empty);
        Die d = new Die(4, Colour.RED);
        List<int[]> l = empty.isPlaceable(d);

        for(int i=0; i<l.size(); i++){
            assertArrayEquals(l.get(i), expectedListWithEmpty.get(i));
        }
    }

    /**
     * Test with no spots available
     */
    @Test
    public void testNoSpots(){
        player = new Player(1);
        player.setWpc(noSpots);
        Die d = new Die(4, Colour.RED);
        List<int[]> l = noSpots.isPlaceable(d);

        assertEquals(l.size(), 0);
    }

    /**
     * Test with only one spot available
     */
    @Test
    public void testOneSpot(){
        player = new Player(1);
        player.setWpc(oneSpot);
        Die d = new Die(4, Colour.RED);
        List<int[]> l = oneSpot.isPlaceable(d);

        assertArrayEquals(l.get(0),expectedListOne.get(0));
    }

}
