package it.polimi.se2018.testobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.ColumnShadeVariety;
import it.polimi.se2018.model.wpc.WPC;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for publicobjectivecard ColumnShadeVariety
 * @author Leonardo Guerra
 */

public class TestColumnShadeVariety {
    private WPC wpc;
    private WPC wpc2;
    private WPC wpc3;
    private WPC wpc4;

    @Before
    public void setUp() {
        wpc = new WPC();
        wpc.setDie(0, 1, new Die(4, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc.setDie(1, 3, new Die(1, Colour.GREEN));
        wpc.setDie(1, 4, new Die(2, Colour.YELLOW));
        wpc.setDie(2, 0, new Die(5, Colour.PURPLE));
        wpc.setDie(2, 1, new Die(6, Colour.GREEN));
        wpc.setDie(2, 3, new Die(6, Colour.PURPLE));
        wpc.setDie(2, 4, new Die(3, Colour.RED));
        wpc.setDie(3, 0, new Die(4, Colour.BLUE));
        wpc.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc.setDie(3, 2, new Die(4, Colour.GREEN));
        wpc.setDie(3, 3, new Die(2, Colour.BLUE));
        wpc.setDie(3, 4, new Die(4, Colour.GREEN));

        wpc2 = new WPC();
        wpc2.setDie(0, 0, new Die(1, Colour.YELLOW));
        wpc2.setDie(0, 1, new Die(2, Colour.GREEN));
        wpc2.setDie(0, 2, new Die(3, Colour.YELLOW));
        wpc2.setDie(0, 3, new Die(4, Colour.BLUE));
        wpc2.setDie(0, 4, new Die(5, Colour.PURPLE));
        wpc2.setDie(1, 0, new Die(6, Colour.RED));
        wpc2.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc2.setDie(1, 2, new Die(2, Colour.GREEN));
        wpc2.setDie(1, 3, new Die(3, Colour.PURPLE));
        wpc2.setDie(1, 4, new Die(4, Colour.RED));
        wpc2.setDie(2, 0, new Die(5, Colour.GREEN));
        wpc2.setDie(2, 1, new Die(6, Colour.RED));
        wpc2.setDie(2, 2, new Die(1, Colour.BLUE));
        wpc2.setDie(2, 3, new Die(2, Colour.RED));
        wpc2.setDie(2, 4, new Die(3, Colour.GREEN));
        wpc2.setDie(3, 0, new Die(4, Colour.PURPLE));
        wpc2.setDie(3, 1, new Die(5, Colour.YELLOW));
        wpc2.setDie(3, 2, new Die(6, Colour.RED));
        wpc2.setDie(3, 3, new Die(1, Colour.GREEN));
        wpc2.setDie(3, 4, new Die(2, Colour.YELLOW));

        wpc3 = new WPC();
        wpc3.setDie(0, 0, new Die(1, Colour.YELLOW));
        wpc3.setDie(0, 1, new Die(2, Colour.GREEN));
        wpc3.setDie(0, 2, new Die(3, Colour.YELLOW));
        wpc3.setDie(0, 3, new Die(4, Colour.BLUE));
        wpc3.setDie(0, 4, new Die(5, Colour.PURPLE));
        wpc3.setDie(1, 0, new Die(6, Colour.RED));
        wpc3.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc3.setDie(1, 2, new Die(2, Colour.GREEN));
        wpc3.setDie(1, 3, new Die(3, Colour.PURPLE));
        wpc3.setDie(1, 4, new Die(4, Colour.RED));
        wpc3.setDie(2, 0, new Die(5, Colour.GREEN));
        wpc3.setDie(2, 1, new Die(6, Colour.RED));
        wpc3.setDie(2, 2, new Die(1, Colour.BLUE));
        wpc3.setDie(2, 3, new Die(2, Colour.RED));
        wpc3.setDie(2, 4, new Die(2, Colour.RED));
        wpc3.setDie(3, 0, new Die(4, Colour.PURPLE));
        wpc3.setDie(3, 1, new Die(5, Colour.YELLOW));
        wpc3.setDie(3, 2, new Die(6, Colour.RED));
        wpc3.setDie(3, 3, new Die(1, Colour.GREEN));
        wpc3.setDie(3, 4, new Die(2, Colour.YELLOW));

        wpc4 = new WPC();

    }

    @Test
    public void testWpc() {
        ColumnShadeVariety c = new ColumnShadeVariety();
        ColumnShadeVariety c2 = new ColumnShadeVariety();
        ColumnShadeVariety c3 = new ColumnShadeVariety();
        ColumnShadeVariety c4 = new ColumnShadeVariety();
        int result = c.getScore(wpc);
        int result2 = c2.getScore(wpc2);
        int result3 = c3.getScore(wpc3);
        int result4 = c4.getScore(wpc4);
        assertEquals(result, 3*4);
        assertEquals(result2, 5*4);
        assertEquals(result3, 4*4);
        assertEquals(result4, 0);
    }
}
