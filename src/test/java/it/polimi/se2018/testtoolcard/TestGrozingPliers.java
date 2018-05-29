package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GrozingPliers;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for the toolcard GrozingPliers
 * @author Leonardo Guerra
 */
public class TestGrozingPliers {
    private PlayerMoveParameters param;
    private GrozingPliers card;
    private Model model;
    private Player player;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedIncreaseDP;
    private ArrayList<Die> expectedDecreaseDP;
    private ArrayList<Die> emptyDP;
    private WPC before;
    private WPC expectedIncrease;
    private WPC expectedDecrease;
    private WPC empty;

    @Before
    public void setUp(){
        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(1, Colour.BLUE));
        beforeDP.add(new Die(4, Colour.RED));
        beforeDP.add(new Die(2, Colour.RED));
        beforeDP.add(new Die(3, Colour.YELLOW));
        beforeDP.add(new Die(6, Colour.YELLOW));

        expectedIncreaseDP = new ArrayList<Die>();
        expectedIncreaseDP.add(new Die(1, Colour.BLUE));
        expectedIncreaseDP.add(new Die(2, Colour.RED));
        expectedIncreaseDP.add(new Die(3, Colour.YELLOW));
        expectedIncreaseDP.add(new Die(6, Colour.YELLOW));

        expectedDecreaseDP = new ArrayList<Die>();
        expectedDecreaseDP.add(new Die(1, Colour.BLUE));
        expectedDecreaseDP.add(new Die(4, Colour.RED));
        expectedDecreaseDP.add(new Die(2, Colour.RED));
        expectedDecreaseDP.add(new Die(6, Colour.YELLOW));

        emptyDP = new ArrayList<Die>();

        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(2); //Via Lux
        filler(before);

        expectedIncrease = gen.getWPC(2);
        fillerExpectedIncrease(expectedIncrease);

        expectedDecrease = gen.getWPC(2);
        fillerExpectedDecrease(expectedDecrease);

        empty = gen.getWPC(2);

        card = GrozingPliers.getInstance();
    }

    private void filler (WPC wpc){
        wpc.setDie(0,2,new Die(6,Colour.GREEN));
        wpc.setDie(1,1,new Die(1,Colour.BLUE));
        wpc.setDie(1,3,new Die(4,Colour.RED));
        wpc.setDie(2,1,new Die(2,Colour.YELLOW));
    }

    private void fillerExpectedIncrease (WPC wpc){
        wpc.setDie(0,2,new Die(6,Colour.GREEN));
        wpc.setDie(0,4,new Die(5,Colour.RED));
        wpc.setDie(1,1,new Die(1,Colour.BLUE));
        wpc.setDie(1,3,new Die(4,Colour.RED));
        wpc.setDie(2,1,new Die(2,Colour.YELLOW));
    }

    private void fillerExpectedDecrease (WPC wpc){
        wpc.setDie(0,0,new Die(2,Colour.YELLOW));
        wpc.setDie(0,2,new Die(6,Colour.GREEN));
        wpc.setDie(1,1,new Die(1,Colour.BLUE));
        wpc.setDie(1,3,new Die(4,Colour.RED));
        wpc.setDie(2,1,new Die(2,Colour.YELLOW));
    }


    /**
     * Increasing a value between 2 and 5
     */
    @Test
    public void test1(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(1);
        param.addParameter(+1);
        param.addParameter(0);
        param.addParameter(4);
        param.setDraftPool(beforeDP);
        model.setParameters(param);


        try {
            card.cardAction(param);
            assertEquals(player.getWpc(), expectedIncrease);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Decreasing a value between 2 and 5
     */
    @Test
    public void test2(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(3);
        param.addParameter(-1);
        param.addParameter(0);
        param.addParameter(0);
        param.setDraftPool(beforeDP);
        model.setParameters(param);


        try {
            card.cardAction(param);
            assertEquals(player.getWpc(), expectedDecrease);
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
    public void test3(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(-1);
        param.addParameter(0);
        param.addParameter(4);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
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
    public void test4() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(4);
        param.addParameter(+1);
        param.addParameter(0);
        param.addParameter(0);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: cannot increase value 6.", e.getMessage());
        }

    }

    //Mancano altri test



}