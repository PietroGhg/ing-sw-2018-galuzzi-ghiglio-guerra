package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.EglomiseBrush;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for tool card EglomiseBrush
 * @author Leonardo Guerra
 */

public class TestEglomiseBrush {
    private PlayerMoveParameters param;
    private EglomiseBrush card;
    private Model model;
    private Player player;
    private WPC before;
    private WPC expected;

    @Before
    public void setUp(){
        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(2); //Via Lux
        filler(before);

        expected = gen.getWPC(2);
        fillerExpected(expected);

        card = EglomiseBrush.getInstance();
    }

    private void filler(WPC wpc){
        wpc.setDie(0, 3, new Die(3, Colour.PURPLE));
        wpc.setDie(0, 4, new Die(5, Colour.RED));
        wpc.setDie(1, 1, new Die(1, Colour.RED));
        wpc.setDie(1, 2, new Die(5, Colour.BLUE));
        wpc.setDie(2, 3, new Die(3, Colour.PURPLE));
    }

    //die in (1,2) moved to (3,4)
    private void fillerExpected(WPC wpc){
        wpc.setDie(0, 0, new Die(5, Colour.RED));
        wpc.setDie(0, 3, new Die(3, Colour.PURPLE));
        wpc.setDie(1, 1, new Die(1, Colour.RED));
        wpc.setDie(1, 2, new Die(5, Colour.BLUE));
        wpc.setDie(2, 3, new Die(3, Colour.PURPLE));

    }

    /**
     * Tests the normal use of the tool card
     */
    @Test
    public void test1(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(0);
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
     * The starting cell is empty -> throws exception
     */
    @Test
    public void test2(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(4);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: the cell is empty.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not adjacent to other dice -> throws exception
     */
    @Test
    public void test3(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(4);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: die must be adjacent to another die.", e.getMessage());
        }
    }

    /**
     * Recipient cell has the same orthogonally adjacent die
     */
    @Test
    public void test4(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(3);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: same die orthogonally adjacent.", e.getMessage());
        }
    }

    /**
     * Value restriction violated -> throws exception
     */
    @Test
    public void test5(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(2);
        param.addParameter(0);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not empty -> throws exception
     */
    @Test
    public void test6(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(4);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            assertEquals("Error: cell not empty.", e.getMessage());
        }
    }

    //Test to check favor tokens in TestGrozingPliers

}
