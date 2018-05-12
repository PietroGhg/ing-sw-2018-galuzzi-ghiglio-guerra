package it.polimi.se2018.TestObjectiveCard;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.ColourDiagonals;
import it.polimi.se2018.Model.WPC.WPC;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for PublicObjectiveCard ColourDiagonals
 * @author Leonardo Guerra
 */

public class TestColourDiagonals {
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
        wpc2.setDie(0, 0, new Die(4, Colour.BLUE));
        wpc2.setDie(0, 2, new Die(2, Colour.BLUE));
        wpc2.setDie(0, 4, new Die(6, Colour.BLUE));
        wpc2.setDie(1, 0, new Die(3, Colour.RED));
        wpc2.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc2.setDie(1, 2, new Die(3, Colour.RED));
        wpc2.setDie(1, 4, new Die(3, Colour.RED));
        wpc2.setDie(2, 1, new Die(3, Colour.RED));
        wpc2.setDie(2, 2, new Die(1, Colour.BLUE));
        wpc2.setDie(2, 3, new Die(3, Colour.RED));
        wpc2.setDie(2, 4, new Die(3, Colour.GREEN));
        wpc2.setDie(3, 0, new Die(3, Colour.RED));
        wpc2.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc2.setDie(3, 2, new Die(3, Colour.RED));
        wpc2.setDie(3, 4, new Die(3, Colour.RED));

        wpc3 = new WPC();

    }

    @Test
    public void testWpc(){
        ColourDiagonals c = new ColourDiagonals();
        ColourDiagonals c2 = new ColourDiagonals();
        ColourDiagonals c3 = new ColourDiagonals();
        int result = c.getScore(wpc);
        int result2 = c2.getScore(wpc2);
        int result3 = c3.getScore(wpc3);
        assertEquals(result, 4);
        assertEquals(result2, 12);
        assertEquals(result3, 0);
    }
}
