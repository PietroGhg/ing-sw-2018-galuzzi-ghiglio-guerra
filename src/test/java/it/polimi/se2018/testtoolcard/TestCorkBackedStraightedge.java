package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.CorkBackedStraightedge;
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

/**
 * Test for the toolcard CorkBackedStraightedge
 * @author Leonardo Guerra
 */

public class TestCorkBackedStraightedge {
    private Model model;
    private PlayerMoveParameters param;
    private CorkBackedStraightedge card;
    private Player player;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> emptyDP;
    private ArrayList<Die> expectedDP;
    private WPC before;
    private WPC empty;
    private WPC expected;

    @Before
    public void setUp(){
        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(6, Colour.BLUE));
        beforeDP.add(new Die(5, Colour.RED));
        beforeDP.add(new Die(3, Colour.GREEN));
        beforeDP.add(new Die(2, Colour.RED));
        beforeDP.add(new Die(1, Colour.YELLOW));
        beforeDP.add(new Die(4, Colour.BLUE));

        emptyDP = new ArrayList<Die>();

        expectedDP = new ArrayList<Die>();
        expectedDP.add(new Die(5, Colour.RED));
        expectedDP.add(new Die(3, Colour.GREEN));
        expectedDP.add(new Die(2, Colour.RED));
        expectedDP.add(new Die(1, Colour.YELLOW));
        expectedDP.add(new Die(4, Colour.BLUE));

        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(22); //Water of Life
        filler(before);

        expected = gen.getWPC(22);
        fillerExpected(expected);
        empty = gen.getWPC(22);

        card = CorkBackedStraightedge.getInstance();
    }

    private void filler (WPC wpc){
        wpc.setDie(0, 3, new Die(2, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(1, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.PURPLE));
        wpc.setDie(1, 4, new Die(3, Colour.GREEN));
        wpc.setDie(3, 4, new Die(4, Colour.PURPLE));
    }

    private void fillerExpected (WPC wpc){
        wpc.setDie(0, 3, new Die(2, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(1, Colour.RED));
        wpc.setDie(1, 1, new Die(5, Colour.PURPLE));
        wpc.setDie(1, 4, new Die(3, Colour.GREEN));
        wpc.setDie(3, 1, new Die(6, Colour.BLUE));
        wpc.setDie(3, 4, new Die(4, Colour.PURPLE));
    }

    /**
     * Tests the normal use of the tool card
     */
    @Test
    public void test1(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(before);
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(1);
        model.setParameters(param);

        try {
            card.cardAction(param);
            assertEquals(player.getWpc(),expected);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Draft pool cell empty -> throws exception
     */
    @Test
    public void test2(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(before);
        model.addPlayer(player);
        param.setDraftPool(emptyDP);
        param.addParameter(6);
        param.addParameter(3);
        param.addParameter(1);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: draft pool cell is empty.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not empty -> throws exception
     */
    @Test
    public void test3(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(before);
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(4);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: cell not empty.", e.getMessage());
        }
    }

    /**
     * First move, but die not placed on the border -> throws exception
     */
    @Test
    public void test4(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(empty);
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(3);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: first move, die must be on the border.", e.getMessage());
        }
    }

    /**
     * Value restriction violated -> throws exception
     */
    @Test
    public void test5(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(before);
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(1);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * Colour restriction violated -> throws exception
     */
    @Test
    public void test6(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(before);
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: colour restriction violated.", e.getMessage());
        }
    }

    /**
     * Die adjacent to another die, but it shouldn't -> throws exception
     * If it can't be adjacent to another die, of course there isn't the same die orthogonally adjacent
     */
    @Test
    public void test7(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        player.setWpc(before);
        model.addPlayer(player);
        param.setDraftPool(beforeDP);
        param.addParameter(5);
        param.addParameter(2);
        param.addParameter(0);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: die is adjacent to other dice.", e.getMessage());
        }
    }

    //Test to check favor tokens in TestEglomiseBrush

}
