package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.LensCutter;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for the toolcard LensCutter
 * @author Leonardo Guerra
 */

public class TestLensCutter {
    private Model model;
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private LensCutter card;
    private Player player;
    private List<List<Die>> beforeRT;
    private ArrayList<ArrayList<Die>> expectedRT;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDP;

    @Before
    public void setUp(){
        beforeRT = new ArrayList<>(10);
        for(int i=0;i<=5;i++) {
            beforeRT.add(new ArrayList<Die>());
        }
        beforeRT.get(0).add(new Die(3, Colour.BLUE));
        beforeRT.get(0).add(new Die(5,Colour.RED));
        beforeRT.get(0).add(new Die(2,Colour.YELLOW));
        beforeRT.get(1).add(new Die(4,Colour.GREEN));
        beforeRT.get(2).add(new Die(2,Colour.PURPLE));
        beforeRT.get(4).add(new Die(1,Colour.BLUE));
        beforeRT.get(5).add(new Die(4,Colour.YELLOW));
        beforeRT.get(5).add(new Die(6,Colour.YELLOW));

        expectedRT = new ArrayList<>(10);
        for(int i=0;i<=5;i++) {
            expectedRT.add(new ArrayList<>());
        }
        expectedRT.set(0,new ArrayList<>());
        expectedRT.get(0).add(new Die(3, Colour.BLUE));
        expectedRT.get(0).add(new Die(5,Colour.RED));
        expectedRT.get(0).add(new Die(6,Colour.PURPLE));
        expectedRT.set(1,new ArrayList<>());
        expectedRT.get(1).add(new Die(4,Colour.GREEN));
        expectedRT.set(2,new ArrayList<Die>());
        expectedRT.get(2).add(new Die(2,Colour.PURPLE));
        expectedRT.set(4,new ArrayList<Die>());
        expectedRT.get(4).add(new Die(1,Colour.BLUE));
        expectedRT.set(5,new ArrayList<Die>());
        expectedRT.get(5).add(new Die(4,Colour.YELLOW));
        expectedRT.get(5).add(new Die(6,Colour.YELLOW));


        beforeDP = new ArrayList<Die>();
        beforeDP.add(new Die(6,Colour.PURPLE));
        beforeDP.add(new Die(6,Colour.YELLOW));
        beforeDP.add(new Die(6,Colour.PURPLE));

        expectedDP = new ArrayList<Die>();
        expectedDP.add(new Die(6,Colour.PURPLE));
        expectedDP.add(new Die(6,Colour.YELLOW));
        expectedDP.add(new Die(2,Colour.YELLOW));

        card = LensCutter.getInstance();
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
        model.addPlayer(player);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(2);
        param.setRoundTrack(beforeRT);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try {
            card.cardAction(param);
            assertEquals(param.getRoundTrack(), expectedRT);
            assertEquals(param.getDraftPool(), expectedDP);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Draft pool cell empty - throws exception
     */
    @Test
    public void test2(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(3);
        param.addParameter(5);
        param.addParameter(0);
        param.setRoundTrack(beforeRT);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: draft pool cell is empty.", e.getMessage());
        }
    }

    /**
     * Round track cell empty - throws exception
     */
    @Test
    public void test3(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(0);
        param.setRoundTrack(beforeRT);
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: round track cell is empty.", e.getMessage());
        }
    }

    /**
     * Tests if the player has enough favor tokens
     */
    @Test
    public void testEnoughFT(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setFavorTokens(4);
        model.addPlayer(player);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(2);
        param.setRoundTrack(beforeRT);
        param.setDraftPool(beforeDP);
        model.setParameters(param);
        try {
            card.cardAction(param);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        param2.addParameter(1);
        param2.addParameter(1);
        param2.addParameter(0);
        try {
            card.cardAction(param2);
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(), model);
        param3.addParameter(0);
        param3.addParameter(2);
        param3.addParameter(0);
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
