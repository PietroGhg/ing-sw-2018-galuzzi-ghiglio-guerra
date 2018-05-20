package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GrozingPliers;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for the toolcard1
 * @author Pietro Ghiglio
 */
public class TestGrozingPliers {
    private PlayerMoveParameters param;
    private GrozingPliers gp;
    private Player player;
    private WPC before;
    private WPC wpc1;
    private WPC wpc6;
    private WPC expected;

    @Before
    public void setUp(){
        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(1);
        filler(before);

        expected = gen.getWPC(1);
        fillerExpected(expected);

        wpc1 = gen.getWPC(1);
        fillerWith1(wpc1);

        wpc6 = gen.getWPC(1);
        fillerWith6(wpc6);

        gp = GrozingPliers.getInstance();
    }

    private void filler(WPC wpc){
        wpc.setDie(0, 1, new Die(4, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.BLUE));
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
    }

    //Same wpc as before, die in position 0, 1 should have it's value increased by one
    private void fillerExpected(WPC wpc){
        wpc.setDie(0, 1, new Die(5, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.BLUE));
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
    }

    //Should throw exception
    private void fillerWith6(WPC wpc){
        wpc.setDie(0, 1, new Die(6, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.BLUE));
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
    }

    //Should throw exception
    private void fillerWith1(WPC wpc){
        wpc.setDie(0, 1, new Die(1, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.BLUE));
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
    }

    /**
     * Increasing a value between 2 and 5
     */
    @Test
    public void test1(){
        player = new Player(1);
        player.setWpc(before);
        param = new PlayerMoveParameters(player);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(1);


        try {
            gp.cardAction(param);
            assertEquals(player.getWpc(), expected);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Decreasing 1 -> throws exception
     */
    @Test
    public void test2(){
        player = new Player(1);
        player.setWpc(wpc1);
        param = new PlayerMoveParameters(player);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(-1);

        try{
            gp.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: cannot decrease value 1.", e.getMessage());
        }
    }

    /**
     * Increasing 6 -> throws exception
     */
    @Test
    public void test3() {
        player = new Player(1);
        player.setWpc(wpc6);
        param = new PlayerMoveParameters(player);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(1);

        try{
            gp.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: cannot increase value 6.", e.getMessage());
        }

    }

}