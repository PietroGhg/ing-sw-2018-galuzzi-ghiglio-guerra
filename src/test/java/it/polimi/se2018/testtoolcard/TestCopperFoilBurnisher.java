package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.CopperFoilBurnisher;
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
 * Test for tool card CopperFoilBurnisher
 * @author Leonardo Guerra
 */

public class TestCopperFoilBurnisher {
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private CopperFoilBurnisher card;
    private Model model;
    private Player player;
    private WPC before;
    private WPC expected;

    @Before
    public void setUp() {
        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(3); //Bellesguard
        filler(before);

        expected = gen.getWPC(3);
        fillerExpected(expected);

        card = CopperFoilBurnisher.getInstance();
    }

    private void filler(WPC wpc){
        wpc.setDie(0, 0, new Die(1,Colour.BLUE));
        wpc.setDie(0, 4, new Die(4,Colour.YELLOW));
        wpc.setDie(1, 1, new Die(3,Colour.RED));
        wpc.setDie(1, 3, new Die(5,Colour.GREEN));
        wpc.setDie(2, 2, new Die(6,Colour.PURPLE));
        wpc.setDie(3, 1, new Die(4,Colour.YELLOW));
    }

    //Die in (0,4) moved to (3,3)
    private void fillerExpected(WPC wpc){
        wpc.setDie(0, 0, new Die(1,Colour.BLUE));
        wpc.setDie(1, 1, new Die(3,Colour.RED));
        wpc.setDie(1, 3, new Die(5,Colour.GREEN));
        wpc.setDie(2, 2, new Die(6,Colour.PURPLE));
        wpc.setDie(3, 1, new Die(4,Colour.YELLOW));
        wpc.setDie(3, 3, new Die(4,Colour.YELLOW));
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
        param.addParameter(3);
        param.addParameter(3);
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
     * Starting cell is empty -> throws exception
     */
    @Test
    public void test2(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(3);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: the cell is empty.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not empty -> throws exception
     */
    @Test
    public void test3(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: cell not empty.", e.getMessage());
        }
    }

    /**
     * Colour restriction violated -> throws exception
     */
    @Test
    public void test4(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(2);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: colour restriction violated.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not adjacent to other dice -> throws exception
     */
    @Test
    public void test5(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(4);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: die must be adjacent to another die.", e.getMessage());
        }
    }

    /**
     * Recipient cell has the same orthogonally adjacent die -> throws exception
     */
    @Test
    public void test6(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(3);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: same die orthogonally adjacent.", e.getMessage());
        }
    }

    /**
     * Tests if the player has enough favor tokens
     */
    @Test
    public void testEnoughFT(){
        model = new Model();
        player = new Player(1);
        player.setWpcOnly(before);
        player.setFavorTokens(4);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(3);
        model.setParameters(param);
        //The first time should go right
        try{
            card.cardAction(param);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        param2.addParameter(1);
        param2.addParameter(3);
        param2.addParameter(0);
        param2.addParameter(2);
        model.setParameters(param2);
        //Second time should go right
        try{
            card.cardAction(param2);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(), model);
        param3.addParameter(1);
        param3.addParameter(1);
        param3.addParameter(1);
        param3.addParameter(0);
        model.setParameters(param3);
        //Third time: not enough favour tokens
        try{
            card.cardAction(param3);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: not enough favor tokens.", e.getMessage());
        }

    }
}
