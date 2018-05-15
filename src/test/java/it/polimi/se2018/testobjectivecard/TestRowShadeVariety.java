package it.polimi.se2018.testobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.RowShadeVariety;
import it.polimi.se2018.model.wpc.WPC;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for publicobjectivecard RowShadeVariety
 * @author Leonardo Guerra
 */

public class TestRowShadeVariety {
    private WPC wpc;
    private WPC wpc2;
    private WPC wpc3;

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
        wpc2.setDie(1, 0, new Die(4, Colour.RED));
        wpc2.setDie(1, 1, new Die(2, Colour.BLUE));
        wpc2.setDie(1, 2, new Die(1, Colour.RED));
        wpc2.setDie(1, 3, new Die(6, Colour.BLUE));
        wpc2.setDie(1, 4, new Die(3, Colour.RED));
        wpc2.setDie(2, 0, new Die(5, Colour.BLUE));
        wpc2.setDie(2, 1, new Die(5, Colour.RED));
        wpc2.setDie(2, 2, new Die(5, Colour.BLUE));
        wpc2.setDie(2, 3, new Die(5, Colour.RED));
        wpc2.setDie(2, 4, new Die(1, Colour.GREEN));
        wpc2.setDie(3, 0, new Die(1, Colour.RED));
        wpc2.setDie(3, 1, new Die(2, Colour.YELLOW));
        wpc2.setDie(3, 2, new Die(3, Colour.RED));
        wpc2.setDie(3, 3, new Die(4, Colour.PURPLE));

        wpc3 = new WPC();

    }

    @Test
    public void testWpc() {
        RowShadeVariety r = new RowShadeVariety();
        RowShadeVariety r2 = new RowShadeVariety();
        RowShadeVariety r3 = new RowShadeVariety();
        int result = r.getScore(wpc);
        int result2 = r2.getScore(wpc2);
        int result3 = r3.getScore(wpc3);
        assertEquals(result, 0);
        assertEquals(result2, 1 * 5);
        assertEquals(result3, 0);
    }
}
