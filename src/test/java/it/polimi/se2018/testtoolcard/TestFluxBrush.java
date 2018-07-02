package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.FluxBrush;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for the toolcard FluxBrush
 * @author Leonardo Guerra
 */

public class TestFluxBrush {
    private Model model;
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private FluxBrush card;
    private Player player;
    //dp
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDPno;
    private ArrayList<Die> expectedDPok;
    //wpc
    private WPC before;
    private WPC expected;

    @Before
    public void setUp(){
        WpcGenerator gen = WpcGenerator.getInstance();

        before = gen.getWPC(21); //Fulgor del Cielo
        filler(before);

        expected = gen.getWPC(21);
        fillerExpected(expected);

        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(1, Colour.YELLOW));
        beforeDP.add(new Die(2, Colour.BLUE));
        beforeDP.add(new Die(3, Colour.PURPLE));
        beforeDP.add(new Die(4, Colour.YELLOW));
        beforeDP.add(new Die(5, Colour.BLUE));
        beforeDP.add(new Die(6, Colour.GREEN));

        expectedDPno = new ArrayList<Die>();
        expectedDPno.add(new Die(2, Colour.BLUE));
        expectedDPno.add(new Die(3, Colour.PURPLE));
        expectedDPno.add(new Die(4, Colour.YELLOW));
        expectedDPno.add(new Die(5, Colour.BLUE));
        expectedDPno.add(new Die(6, Colour.GREEN));
        expectedDPno.add(new Die(6, Colour.YELLOW));

        expectedDPok = new ArrayList<Die>();
        expectedDPok.add(new Die(1, Colour.YELLOW));
        expectedDPok.add(new Die(2, Colour.BLUE));
        expectedDPok.add(new Die(3, Colour.PURPLE));
        expectedDPok.add(new Die(4, Colour.YELLOW));
        expectedDPok.add(new Die(6, Colour.GREEN));

        card = FluxBrush.getInstance();
    }

    public void filler(WPC wpc){
        wpc.setDie(0, 0, new Die(4, Colour.YELLOW));
        wpc.setDie(0, 3, new Die(1, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(1, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 1, new Die(4, Colour.RED));
        wpc.setDie(1, 2, new Die(5, Colour.RED));
        wpc.setDie(1, 3, new Die(2, Colour.PURPLE));
        wpc.setDie(2, 1, new Die(2, Colour.RED));
        wpc.setDie(2, 2, new Die(6, Colour.GREEN));
        wpc.setDie(2, 4, new Die(5, Colour.RED));
        wpc.setDie(3, 0, new Die(6, Colour.RED));
        wpc.setDie(3, 2, new Die(3, Colour.RED));
        wpc.setDie(3, 3, new Die(6, Colour.RED));
        wpc.setDie(3, 4, new Die(5, Colour.BLUE));
    }

    public void fillerExpected(WPC wpc){
        wpc.setDie(0, 0, new Die(4, Colour.YELLOW));
        wpc.setDie(0, 1, new Die(1, Colour.BLUE));
        wpc.setDie(0, 3, new Die(1, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(1, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 1, new Die(4, Colour.RED));
        wpc.setDie(1, 2, new Die(5, Colour.RED));
        wpc.setDie(1, 3, new Die(2, Colour.PURPLE));
        wpc.setDie(2, 1, new Die(2, Colour.RED));
        wpc.setDie(2, 2, new Die(6, Colour.GREEN));
        wpc.setDie(2, 4, new Die(5, Colour.RED));
        wpc.setDie(3, 0, new Die(6, Colour.RED));
        wpc.setDie(3, 2, new Die(3, Colour.RED));
        wpc.setDie(3, 3, new Die(6, Colour.RED));
        wpc.setDie(3, 4, new Die(5, Colour.BLUE));
    }

    /**
     * Test for the placeable chosen die
     */
    @Test
    public void testPlaceable(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        model.setDraftPool(beforeDP);
        model.setParameters(param);

        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(1);

        try {
            card.cardAction(param);
            assertEquals(expectedDPok, param.getDraftPool());
            assertEquals(expected, player.getWpc());
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Test for the chosen die not placeable
     */
    @Test
    public void testNot(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        model.setDraftPool(beforeDP);
        model.setParameters(param);

        param.addParameter(0);
        param.addParameter(6);

        try {
            card.cardAction(param);
            assertEquals(expectedDPno, param.getDraftPool());

            assertEquals(before, player.getWpc()); //player's board has not been changed
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
    public void testEnoughFT(){
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(2);
        player.setWpcOnly(before);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        model.setDraftPool(beforeDP);
        model.setParameters(param);

        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(1);

        try {
            card.cardAction(param);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        param2.addParameter(5);
        param2.addParameter(5);
        try {
            card.cardAction(param2);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: not enough favor tokens.", e.getMessage());
        }
    }

}