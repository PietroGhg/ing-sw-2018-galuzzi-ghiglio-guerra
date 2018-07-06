package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.RunningPliers;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.table.DiceBag;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for the toolcard RunningPliers
 * @author Leonardo Guerra
 */

public class TestRunningPliers {
    private Model model;
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private RunningPliers card;
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
        beforeDP.add(new Die(1, Colour.YELLOW));
        beforeDP.add(new Die(2, Colour.BLUE));
        beforeDP.add(new Die(3, Colour.PURPLE));
        beforeDP.add(new Die(4, Colour.YELLOW));
        beforeDP.add(new Die(5, Colour.BLUE));
        beforeDP.add(new Die(6, Colour.GREEN));

        emptyDP = new ArrayList<Die>();

        expectedDP = new ArrayList<Die>();
        expectedDP.add(new Die(1, Colour.YELLOW));
        expectedDP.add(new Die(2, Colour.BLUE));
        expectedDP.add(new Die(3, Colour.PURPLE));
        expectedDP.add(new Die(4, Colour.YELLOW));
        expectedDP.add(new Die(6, Colour.GREEN));

        WpcGenerator gen = WpcGenerator.getInstance();
        before = gen.getWPC(23); //Lux Mundi
        filler(before);

        expected = gen.getWPC(23);
        fillerExpected(expected);

        empty = gen.getWPC(23);

        card = RunningPliers.getInstance();
        Extractor.resetInstance();
    }

    public void filler(WPC wpc){
        wpc.setDie(0, 3, new Die(3, Colour.GREEN));
        wpc.setDie(0, 4, new Die(1, Colour.GREEN));
        wpc.setDie(1, 3, new Die(6, Colour.BLUE));
        wpc.setDie(2, 0, new Die(2, Colour.BLUE));
        wpc.setDie(2, 1, new Die(5, Colour.PURPLE));
        wpc.setDie(2, 2, new Die(4, Colour.RED));
        wpc.setDie(2, 3, new Die(6, Colour.YELLOW));
        wpc.setDie(3, 4, new Die(1, Colour.YELLOW));
    }

    public void fillerExpected(WPC wpc){
        wpc.setDie(0, 3, new Die(3, Colour.GREEN));
        wpc.setDie(0, 4, new Die(1, Colour.GREEN));
        wpc.setDie(1, 3, new Die(6, Colour.BLUE));
        wpc.setDie(2, 0, new Die(2, Colour.BLUE));
        wpc.setDie(2, 1, new Die(5, Colour.PURPLE));
        wpc.setDie(2, 2, new Die(4, Colour.RED));
        wpc.setDie(2, 3, new Die(6, Colour.YELLOW));
        wpc.setDie(3, 2, new Die(5, Colour.BLUE));
        wpc.setDie(3, 4, new Die(1, Colour.YELLOW));
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
        model.addPlayer(player);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(2);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try {
            card.cardAction(param);
            assertEquals(player.getWpc(),expected);
            assertEquals(param.getDraftPool(), expectedDP);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Not player's first turn - throws exception
     */
    @Test
    public void testNotFirstTurn(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(2);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        param.setDraftPool(beforeDP);
        model.setParameters(param);
        model.startGame();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();
        model.nextTurn();

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: this card can be played in the first turn only.", e.getMessage());
        }
    }

    /**
     * Player's first turn: skip the second turn
     */
    @Test
    public void testFirstTurn(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(0);
        DiceBag.resetInstance();
        model.startGame();
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try {
            card.cardAction(param);
            assertEquals(player.getSkipTurn(),true);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Draft pool cell empty - throws exception
     */
    @Test
    public void test3(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(0);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();
        param.setDraftPool(emptyDP);
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
     * First move, but die not placed on the border - throws exception
     */
    @Test
    public void test4(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(empty);
        model.addPlayer(player);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();
        param.setDraftPool(beforeDP);
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
     * Recipient cell is not empty - throws exception
     */
    @Test
    public void test5(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(5);
        param.addParameter(2);
        param.addParameter(3);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();
        param.setDraftPool(beforeDP);
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
     * Value restriction violated - throws exception
     */
    @Test
    public void test6(){
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(4);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        DiceBag.resetInstance();
        model.startGame();
        param.setDraftPool(beforeDP);
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
     * Colour restriction violated - throws exception
     */
    @Test
    public void test7(){
        DiceBag.resetInstance();
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(1);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        model.startGame();
        param.setDraftPool(beforeDP);
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
     * Recipient cell is not adjacent to other dice - throws exception
     */
    @Test
    public void test8(){
        DiceBag.resetInstance();
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(0);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        model.startGame();
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: die must be adjacent to another die.", e.getMessage());
        }
    }

    /**
     * Recipient cell has the same orthogonally adjacent die - throws exception
     */
    @Test
    public void test9(){
        DiceBag.resetInstance();
        model = new Model();
        player = new Player(1);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpc(before);
        model.addPlayer(player);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(0);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        model.startGame();
        param.setDraftPool(beforeDP);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch(MoveNotAllowedException e){
            //Exception thrown, ok
        }
    }

    /**
     * Tests if the player has enough favor tokens
     */
    @Test
    public void testEnoughFT(){
        model = new Model();
        player = new Player(1);
        player.setFavorTokens(3);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        player.setWpcOnly(before);
        model.addPlayer(player);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(2);
        model.addPlayer(new Player(2));
        model.addPlayer(new Player(3));
        model.startGame();
        param.setDraftPool(beforeDP);
        model.setParameters(param);
        try {
            card.cardAction(param);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        param2.addParameter(0);
        param2.addParameter(1);
        param2.addParameter(0);
        try {
            card.cardAction(param2);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(), model);
        param3.addParameter(3);
        param3.addParameter(3);
        param3.addParameter(0);
        try {
            card.cardAction(param3);
            fail();
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: not enough favor tokens.", e.getMessage());
        }
    }
}
