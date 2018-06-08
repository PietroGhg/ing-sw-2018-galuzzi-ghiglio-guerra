package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.FluxRemover;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestFluxRemover {
    private Model model;
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private FluxRemover card;
    private Player player;
    //dp
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDP3;
    private ArrayList<Die> expectedDP5;
    //DiceBag
    private ArrayList<Die> beforeDB;
    private ArrayList<Die> expectedDB3;
    private ArrayList<Die> expectedDB5;
    //wpc
    private WPC before;
    private WPC expected;

    @Before
    public void setUp(){
        WpcGenerator gen = new WpcGenerator() ;
        before = gen.getWPC(4); //Kaleidoscopic Dream
        filler(before);

        expected = gen.getWPC(4);
        fillerExpected(expected);

        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(1, Colour.YELLOW));
        beforeDP.add(new Die(2, Colour.BLUE));
        beforeDP.add(new Die(3, Colour.PURPLE));
        beforeDP.add(new Die(4, Colour.YELLOW));
        beforeDP.add(new Die(5, Colour.BLUE));
        beforeDP.add(new Die(6, Colour.GREEN));

        expectedDP3 = new ArrayList<Die>();
        expectedDP3.add(new Die(1, Colour.YELLOW));
        expectedDP3.add(new Die(2, Colour.BLUE));
        expectedDP3.add(new Die(3, Colour.PURPLE));
        expectedDP3.add(new Die(5, Colour.BLUE));
        expectedDP3.add(new Die(6, Colour.GREEN));
        expectedDP3.add(new Die(5, Colour.GREEN));

        expectedDP5 = new ArrayList<Die>();
        expectedDP5.add(new Die(1, Colour.YELLOW));
        expectedDP5.add(new Die(2, Colour.BLUE));
        expectedDP5.add(new Die(3, Colour.PURPLE));
        expectedDP5.add(new Die(4, Colour.YELLOW));
        expectedDP5.add(new Die(5, Colour.BLUE));

        beforeDB = new ArrayList<Die>();
        beforeDB.add(new Die(1, Colour.RED));
        beforeDB.add(new Die(1, Colour.RED));
        beforeDB.add(new Die(1, Colour.YELLOW));
        beforeDB.add(new Die(1, Colour.GREEN));
        beforeDB.add(new Die(1, Colour.GREEN));
        beforeDB.add(new Die(1, Colour.GREEN));
        beforeDB.add(new Die(1, Colour.PURPLE));
        beforeDB.add(new Die(1, Colour.PURPLE));
        beforeDB.add(new Die(1, Colour.BLUE));

        expectedDB3 = new ArrayList<Die>();
        expectedDB3.add(new Die(1, Colour.RED));
        expectedDB3.add(new Die(1, Colour.RED));
        expectedDB3.add(new Die(1, Colour.YELLOW));
        expectedDB3.add(new Die(1, Colour.GREEN));
        expectedDB3.add(new Die(1, Colour.GREEN));
        expectedDB3.add(new Die(1, Colour.PURPLE));
        expectedDB3.add(new Die(1, Colour.PURPLE));
        expectedDB3.add(new Die(1, Colour.BLUE));
        expectedDB3.add(new Die(4, Colour.YELLOW));

        expectedDB5 = new ArrayList<Die>();
        expectedDB5.add(new Die(1, Colour.RED));
        expectedDB5.add(new Die(1, Colour.RED));
        expectedDB5.add(new Die(1, Colour.YELLOW));
        expectedDB5.add(new Die(1, Colour.GREEN));
        expectedDB5.add(new Die(1, Colour.GREEN));
        expectedDB5.add(new Die(1, Colour.GREEN));
        expectedDB5.add(new Die(1, Colour.PURPLE));
        expectedDB5.add(new Die(1, Colour.BLUE));
        expectedDB5.add(new Die(6, Colour.GREEN));

        card = FluxRemover.getInstance();
    }

    public void filler(WPC wpc){
        wpc.setDie(0, 0, new Die(4, Colour.YELLOW));
        wpc.setDie(0, 2, new Die(2, Colour.PURPLE));
        wpc.setDie(0, 3, new Die(4, Colour.GREEN));
        wpc.setDie(1, 3, new Die(1, Colour.BLUE));
        wpc.setDie(2, 3, new Die(6, Colour.PURPLE));
        wpc.setDie(3, 3, new Die(3, Colour.BLUE));
    }

    public void fillerExpected(WPC wpc){
        wpc.setDie(0, 0, new Die(4, Colour.YELLOW));
        wpc.setDie(0, 2, new Die(2, Colour.PURPLE));
        wpc.setDie(0, 3, new Die(4, Colour.GREEN));
        wpc.setDie(1, 1, new Die(4, Colour.PURPLE));
        wpc.setDie(1, 3, new Die(1, Colour.BLUE));
        wpc.setDie(2, 3, new Die(6, Colour.PURPLE));
        wpc.setDie(3, 3, new Die(3, Colour.BLUE));
    }

    /**
     * Tests the normal use of the tool card with 3 parameters
     */
    @Test
    public void test1(){
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(3);
        player.setWpc(before);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        model.setDraftPool(beforeDP);
        model.setDiceBag(beforeDB);
        model.setParameters(param);

        //case 3 parameters
        param.addParameter(3);
        param.addParameter(4);
        param.addParameter(5);

        try {
            card.cardAction(param);
            assertEquals(player.getWpc(),before);
            assertEquals(param.getDiceBag(), expectedDB3);
            assertEquals(param.getDraftPool(), expectedDP3);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Tests the normal use of the tool card with 5 parameters
     */
    @Test
    public void test2(){
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(4);
        player.setWpc(before);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        model.setDraftPool(beforeDP);
        model.setDiceBag(beforeDB);
        model.setParameters(param);

        //case 5 parameters
        param.addParameter(5);
        param.addParameter(6);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(1);

        try {
            card.cardAction(param);
            assertEquals(player.getWpc(),expected);
            assertEquals(param.getDiceBag(), expectedDB5);
            assertEquals(param.getDraftPool(), expectedDP5);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Tests if the player has enough favor tokens
     */
    @Test
    public void test3(){
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(4);
        player.setWpc(before);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        model.setDraftPool(beforeDP);
        model.setDiceBag(beforeDB);
        model.setParameters(param);

        param.addParameter(3);
        param.addParameter(4);
        param.addParameter(5);
        try {
            card.cardAction(param);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        param2.addParameter(0);
        param2.addParameter(0);
        param2.addParameter(2);
        try {
            card.cardAction(param);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(),model);
        param3.addParameter(5);
        param3.addParameter(8);
        param3.addParameter(3);
        try {
            card.cardAction(param3);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: not enough favor tokens.", e.getMessage());
        }
    }

}
