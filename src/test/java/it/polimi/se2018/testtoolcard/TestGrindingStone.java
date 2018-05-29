package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GrindingStone;
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

public class TestGrindingStone {
    private PlayerMoveParameters param;
    private GrindingStone card;
    private Model model;
    private Player player;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDP;
    private ArrayList<Die> emptyDP;
    private WPC before;
    private WPC expected;
    private WPC empty;

    @Before
    public void setUp(){
        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(1, Colour.BLUE));
        beforeDP.add(new Die(4, Colour.RED));
        beforeDP.add(new Die(2, Colour.RED));
        beforeDP.add(new Die(3, Colour.YELLOW));
        beforeDP.add(new Die(6, Colour.YELLOW));
        beforeDP.add(new Die(5, Colour.GREEN));

        expectedDP = new ArrayList<Die>();
        expectedDP.add(new Die(1, Colour.BLUE));
        expectedDP.add(new Die(2, Colour.RED));
        expectedDP.add(new Die(3, Colour.YELLOW));
        expectedDP.add(new Die(6, Colour.YELLOW));
        expectedDP.add(new Die(5, Colour.GREEN));

        emptyDP = new ArrayList<Die>();

        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(11); //Aurora Sagradis
        filler(before);

        expected = gen.getWPC(11);
        fillerExpected(expected);

        empty = gen.getWPC(11);

        card = GrindingStone.getInstance();
    }

    public void filler (WPC wpc){
        wpc.setDie(0, 2, new Die(4,Colour.BLUE));
        wpc.setDie(0, 4, new Die(1, Colour.YELLOW));
        wpc.setDie(1, 1, new Die(2, Colour.PURPLE));
        wpc.setDie(1, 3, new Die(5, Colour.GREEN));
        wpc.setDie(2, 3, new Die(5, Colour.BLUE));
    }

    public void fillerExpected (WPC wpc){
        wpc.setDie(0, 0, new Die(4, Colour.RED));
        wpc.setDie(0, 2, new Die(4,Colour.BLUE));
        wpc.setDie(0, 4, new Die(1, Colour.YELLOW));
        wpc.setDie(1, 1, new Die(2, Colour.PURPLE));
        wpc.setDie(1, 3, new Die(5, Colour.GREEN));
        wpc.setDie(2, 3, new Die(5, Colour.BLUE));
    }

    /**
     * Tests the normal use of the tool card
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
        param.addParameter(0);
        param.addParameter(0);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try {
            card.cardAction(param);
            assertEquals(player.getWpc(), expected);
        }
        catch (MoveNotAllowedException e){
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
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(5);
        param.addParameter(0);
        param.addParameter(4);
        param.setDraftPool(emptyDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
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
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(4);
        param.addParameter(2);
        param.addParameter(3);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
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
        player.setWpc(empty);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: first move, die must be on the border.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not adjacent to other dice -> throws exception
     */
    @Test
    public void test5(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(0);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: die must be adjacent to another die.", e.getMessage());
        }
    }

    /**
     * Value restriction violated -> throws exception
     */
    @Test
    public void test6(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(0);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * Colour restriction violated -> throws exception
     */
    @Test
    public void test7(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(5);
        param.addParameter(0);
        param.addParameter(0);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: colour restriction violated.", e.getMessage());
        }
    }

    /**
     * Recipient cell has the same orthogonally adjacent die -> throws exception
     */
    @Test
    public void test8(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(3);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: same die orthogonally adjacent.", e.getMessage());
        }
    }

    //Test to check favor tokens in TestEglomiseBrush

}
