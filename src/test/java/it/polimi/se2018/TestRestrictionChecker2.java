package it.polimi.se2018;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for adjacent, similar and first move (not violated) restrictions
 * @author Pietro Ghiglio
 */

public class TestRestrictionChecker2 {
    private WPC wpc;

    @Before
    public void testSetup(){
        WpcGenerator gen = new WpcGenerator();
        wpc = gen.getWPC(1);
        wpc.getCell(0, 0).setDie(new Die(4, Colour.GREEN));
    }

    //Test for first move restriction (not violated)
    @Test
    public void testFM(){
        RestrictionChecker restr = new RestrictionChecker();
        try{
            restr.checkFirstMove(wpc, 1, 1);
        }
        catch (MoveNotAllowedException e){
            fail();
        }
    }

    //Test for adjacent restriction (violated)
    @Test
    public void testAdj1(){
        RestrictionChecker restr = new RestrictionChecker();
        try {
            restr.checkAdjacent(wpc, 0, 2);
        }
        catch (MoveNotAllowedException e){
            assertEquals("Die must be adjacent to another die.", e.getMessage());
        }
    }

    //Test for adjacent restriction (not violated)
    @Test
    public void testAdj2(){
        RestrictionChecker restr = new RestrictionChecker();
        try {
            restr.checkAdjacent(wpc, 0, 1);
        }
        catch (MoveNotAllowedException e){
            fail();
        }
    }

    //Test for same die restriction (violated)
    @Test
    public void testSame1(){
        RestrictionChecker restr = new RestrictionChecker();
        try {
            restr.sameDie(wpc, 1, 0, new Die(4, Colour.GREEN));
            fail("Expected exception");
        }
        catch(MoveNotAllowedException e){
            assertEquals("Same die orthogonally adjacent.", e.getMessage());
        }
    }

    //Test for same die restriction (not violated)
    @Test
    public void testSame2() {
        RestrictionChecker restr = new RestrictionChecker();
        try{
            //adjacent die, different value
            restr.sameDie(wpc, 0, 1, new Die(2, Colour.GREEN));
        }
        catch (MoveNotAllowedException e) {
            fail();
        }

        try {
            //adjacent die, different colour
            restr.sameDie(wpc, 0, 1, new Die(4, Colour.RED));
        }
        catch (MoveNotAllowedException e){
            fail();
        }

        try {
            //same die, not adjacent
            restr.sameDie(wpc, 0, 2, new Die (4, Colour.GREEN));
        }
        catch (MoveNotAllowedException e) {
            fail();
        }
    }
}
