package it.polimi.se2018;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDie {
    private Die die1;
    private Die die2;
    private Die die3;

    @Test
    public void test(){
        die1 = new Die();
        assertEquals(null, die1.getDieColour());
        assertEquals(null, die1.getDieValue());
        die2 = new Die(2, Colour.BLUE);
        assertEquals(2, die2.getDieValue().intValue());
        assertEquals(Colour.BLUE, die2.getDieColour());
        die3 = new Die(Colour.GREEN);
        assertEquals(null, die3.getDieValue());
        assertEquals(Colour.GREEN, die3.getDieColour());
    }
}
