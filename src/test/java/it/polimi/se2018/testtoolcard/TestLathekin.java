package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.Lathekin;
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
 * Test for tool card Lathekin
 * @author Leonardo Guerra
 */

public class TestLathekin {
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private Lathekin card;
    private Model model;
    private Player player;
    private WPC before;
    private WPC expected;

    @Before
    public void setUp() {
        WpcGenerator gen = WpcGenerator.getInstance();
        before = gen.getWPC(3); //Bellesguard
        filler(before);

        expected = gen.getWPC(3);
        fillerExpected(expected);

        card = Lathekin.getInstance();
    }

    private void filler(WPC wpc) {
        wpc.setDie(0, 1, new Die(6, Colour.RED));
        wpc.setDie(0, 4, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 0, new Die(2, Colour.GREEN));
        wpc.setDie(1, 2, new Die(5, Colour.BLUE));
        wpc.setDie(2, 0, new Die(1, Colour.PURPLE));
        wpc.setDie(2, 3, new Die(2, Colour.GREEN));
        wpc.setDie(3, 2, new Die(6, Colour.RED));
    }

    //(2,0) to (3,3) and (2,3) in (2,0)
    private void fillerExpected(WPC wpc) {
        wpc.setDie(0, 1, new Die(6, Colour.RED));
        wpc.setDie(0, 4, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 0, new Die(2, Colour.GREEN));
        wpc.setDie(1, 2, new Die(5, Colour.BLUE));
        wpc.setDie(2, 0, new Die(6, Colour.RED));
        wpc.setDie(2, 3, new Die(2, Colour.GREEN));
        wpc.setDie(3, 3, new Die(1, Colour.PURPLE));
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
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
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
     * First die starting cell is empty - throws exception
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
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(1);
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
     * First recipient cell is not empty - throws exception
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
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(2);
        param.addParameter(4);
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
     * Colour restriction violated for the first die - throws exception
     */
    @Test
    public void test4(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(1);
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
     * Value restriction violated for the first die - throws exception
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
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(4);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * First recipient cell is not adjacent to other dice - throws exception
     */
    @Test
    public void test6(){
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
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(3);
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
     * First recipient cell has the same orthogonally adjacent die - throws exception
     */
    //Non trova l'infrazione della restrizione (anche per il test13)
    @Test
    public void test7(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(3);
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
     * Second die starting cell is empty - throws exception
     */
    @Test
    public void test8(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(2);
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
     * Second recipient cell is not empty - throws exception
     */
    @Test
    public void test9(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(2);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(0);
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
     * Colour restriction violated for the second die - throws exception
     */
    @Test
    public void test10(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(4);
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
     * Value restriction violated for the second die - throws exception
     */
    @Test
    public void test11(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(1);
        try{
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * First recipient cell is not adjacent to other dice - throws exception
     */
    @Test
    public void test12(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
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
     * First recipient cell has the same orthogonally adjacent die - throws exception
     */
    @Test
    public void test13(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(2);
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
     * Second recipient cell has the same orthogonally adjacent die - throws exception
     */
    @Test
    public void test14(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(4);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(4);
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
     * Second recipient cell has the same orthogonally
     * adjacent die (the first one moved) - throws exception
     */
    @Test
    public void test15(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(4);
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
        player.setWpc(before);
        player.setFavorTokens(4);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(0);
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
        param2.addParameter(2);
        param2.addParameter(1);
        param2.addParameter(0);
        param2.addParameter(0);
        param2.addParameter(3);
        try {
            card.cardAction(param2);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(), model);
        param3.addParameter(0);
        param3.addParameter(4);
        param3.addParameter(1);
        param3.addParameter(3);
        param3.addParameter(2);
        param3.addParameter(3);
        param3.addParameter(3);
        param3.addParameter(4);
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