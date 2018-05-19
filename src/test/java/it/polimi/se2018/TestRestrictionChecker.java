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
 * Tests for colour, value and first move restrictions (in case of an empty board)
 * @author Pietro Ghiglio
 */

public class TestRestrictionChecker {
    private WPC wpc;
    private WPC wpc2;

    @Before
    public void setUp() {
        WpcGenerator gen = new WpcGenerator();
        wpc = gen.getWPC(1);
    }

    //Test when color restriction is NOT violated
    @Test
    public void testColor1(){
        RestrictionChecker restr = new RestrictionChecker();
        try{
            restr.checkColourRestriction(wpc, 0, 4, new Die(4, Colour.GREEN));
        }
        catch (MoveNotAllowedException e){
            fail();
        }
    }

    //Test when color restriction is violated
    @Test
    public void testColor2(){
        RestrictionChecker restr = new RestrictionChecker();
        try{
            restr.checkColourRestriction(wpc, 0, 4, new Die(4, Colour.RED));
            fail("Expected exception");
        }
        catch (MoveNotAllowedException e){
            assertEquals( "Color restriction violated.", e.getMessage());
        }
    }

    //Test when value restriction is NOT violated
    @Test
    public void testValue1(){
        RestrictionChecker restr = new RestrictionChecker();
        try{
            restr.checkValueRestriction(wpc, 0, 0, new Die(4, Colour.GREEN));
        }
        catch(MoveNotAllowedException e){
            fail();
        }
    }

    //Test when value restriction is violated
    @Test
    public void testValue2(){
        RestrictionChecker restr = new RestrictionChecker();
        try{
            restr.checkValueRestriction(wpc, 0, 0, new Die(5, Colour.GREEN));
            fail("Expectd exception");
        }
        catch(MoveNotAllowedException e){
            assertEquals("Value restriction violated.", e.getMessage());
        }
    }

    //Test for the first move restriction (violated)
    @Test
    public void testFM1(){
        RestrictionChecker restr = new RestrictionChecker();
        try {
            restr.checkFirstMove(wpc, 1, 1);
            fail("Expected exception");
        }
        catch (MoveNotAllowedException e){
            assertEquals("First move: die must be on the border.", e.getMessage());
        }
    }






}
