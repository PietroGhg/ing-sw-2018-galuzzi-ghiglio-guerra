package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.TapWheel;
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
 * Test for the tool card TapWheel
 * @author Leonardo Guerra
 */

public class TestTapWheel {
    private PlayerMoveParameters param;
    private PlayerMoveParameters param2;
    private PlayerMoveParameters param3;
    private TapWheel card;
    private Model model;
    private Player player;
    private WPC before;
    private WPC expected;
    private WPC expected2;
    private List<List<Die>> roundTrack;

    @Before
    public void setUp(){
        WpcGenerator gen = new WpcGenerator();
        before = gen.getWPC(3); //Bellesguard
        filler(before);

        expected = gen.getWPC(3);
        fillerExpected(expected);

        expected2 = gen.getWPC(3);
        fillerExpected2(expected2);

        roundTrack = new ArrayList<>(10);
        for(int i=0;i<=4;i++) {
            roundTrack.add(new ArrayList<>());
        }
        roundTrack.get(0).add(new Die(2, Colour.RED));
        roundTrack.get(1).add(new Die(5, Colour.BLUE));
        roundTrack.get(1).add(new Die(3, Colour.RED));
        roundTrack.get(2).add(new Die(6, Colour.GREEN));
        roundTrack.get(2).add(new Die(2, Colour.RED));
        roundTrack.get(2).add(new Die(1, Colour.GREEN));
        roundTrack.get(4).add(new Die(3, Colour.GREEN));
        roundTrack.get(4).add(new Die(6, Colour.PURPLE));

        card = TapWheel.getInstance();
    }

    public void filler(WPC wpc){
        wpc.setDie(0, 1, new Die(6, Colour.RED));
        wpc.setDie(0, 4, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 0, new Die(2, Colour.GREEN));
        wpc.setDie(1, 2, new Die(4, Colour.BLUE));
        wpc.setDie(2, 0, new Die(1, Colour.PURPLE));
        wpc.setDie(2, 3, new Die(2, Colour.GREEN));
        wpc.setDie(3, 2, new Die(6, Colour.RED));
    }

    public void fillerExpected(WPC wpc){
        wpc.setDie(0, 1, new Die(6, Colour.RED));
        wpc.setDie(0, 4, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 0, new Die(2, Colour.GREEN));
        wpc.setDie(1, 2, new Die(4, Colour.BLUE));
        wpc.setDie(2, 0, new Die(1, Colour.PURPLE));
        wpc.setDie(2, 2, new Die(6, Colour.RED));
        wpc.setDie(2, 3, new Die(2, Colour.GREEN));
    }

    public void fillerExpected2(WPC wpc){
        wpc.setDie(0, 2, new Die(6, Colour.RED));
        wpc.setDie(0, 4, new Die(3, Colour.YELLOW));
        wpc.setDie(1, 0, new Die(2, Colour.GREEN));
        wpc.setDie(1, 2, new Die(4, Colour.BLUE));
        wpc.setDie(2, 0, new Die(1, Colour.PURPLE));
        wpc.setDie(2, 2, new Die(6, Colour.RED));
        wpc.setDie(2, 3, new Die(2, Colour.GREEN));
    }


    //One die
    /**
     * Tests the normal use of the tool card, moving 1 die
     */
    @Test
    public void testOne1(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        param.setRoundTrack(roundTrack);
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
     * Round track cell empty -> throws exception
     */
    @Test
    public void testOne2() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(3);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: round track cell is empty.", e.getMessage());
        }
    }

    /**
     * Chosen die colour is different from the chosen round track die colour -> throws exception
     */
    @Test
    public void testOne3() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(3);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: die colour is different.", e.getMessage());
        }
    }

    /**
     * Starting cell is empty -> throws exception
     */
    @Test
    public void testOne4() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(3);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: the cell is empty.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not empty -> throws exception
     */
    @Test
    public void testOne5() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: cell not empty.", e.getMessage());
        }
    }

    /**
     * Value restriction violated -> throws exception
     */
    @Test
    public void testOne6() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(1);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * Colour restriction violated -> throws exception
     */
    @Test
    public void testOne7() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(0);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: colour restriction violated.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not adjacent to other dice -> throws exception
     */
    @Test
    public void testOne8() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(0);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e){
            System.out.println(e.getMessage());
            assertEquals("Error: die must be adjacent to another die.", e.getMessage());
        }
    }

    /**
     * Recipient cell has the same orthogonally adjacent die -> throws exception
     */
    @Test
    public void testOne9() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(4);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        } catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: same die orthogonally adjacent.", e.getMessage());
        }
    }

    //Two dice
    /**
     * Tests the normal use of the tool card, moving 2 dice
     */
    @Test
    public void testTwo1(){
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        //RoundTrack cell
        param.addParameter(2);
        param.addParameter(1);
        //First die
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        //Second die
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(2);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            assertEquals(player.getWpc(),expected2);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }
    }

    /**
     * Chosen die colour is different from the chosen round track die colour -> throws exception
     */
    @Test
    public void testTwo2() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(3);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: die colour is different.", e.getMessage());
        }
    }

    /**
     * Starting cell is empty -> throws exception
     */
    @Test
    public void testTwo3() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(4);
        param.addParameter(1);
        param.addParameter(3);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: the cell is empty.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not empty -> throws exception
     */
    @Test
    public void testTwo4() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(1);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(4);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: cell not empty.", e.getMessage());
        }
    }

    /**
     * Value restriction violated -> throws exception
     */
    @Test
    public void testTwo5() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(3);
        param.addParameter(3);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: value restriction violated.", e.getMessage());
        }
    }

    /**
     * Colour restriction violated -> throws exception
     */
    @Test
    public void testTwo6() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(0);
        param.addParameter(0);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: colour restriction violated.", e.getMessage());
        }
    }

    /**
     * Recipient cell is not adjacent to other dice -> throws exception
     */
    @Test
    public void testTwo7() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(3);
        param.addParameter(4);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
            card.cardAction(param);
            fail();
        }
        catch (MoveNotAllowedException e) {
            System.out.println(e.getMessage());
            assertEquals("Error: die must be adjacent to another die.", e.getMessage());
        }
    }

    /**
     * Recipient cell has the same orthogonally adjacent die -> throws exception
     */
    @Test
    public void testTwo8() {
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(5);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(4);
        param.addParameter(0);
        param.addParameter(2);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(1);
        param.addParameter(0);
        param.addParameter(1);
        param.addParameter(4);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);

        try {
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
        TapWheel.resetInstance();
        model = new Model();
        player = new Player(1);
        player.setWpc(before);
        player.setFavorTokens(3);
        model.addPlayer(player);
        param = new PlayerMoveParameters(player.getPlayerID(), model);
        param.addParameter(2);
        param.addParameter(1);
        param.addParameter(3);
        param.addParameter(2);
        param.addParameter(2);
        param.addParameter(2);
        param.setRoundTrack(roundTrack);
        model.setParameters(param);
        try {
            card.cardAction(param);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param2 = new PlayerMoveParameters(player.getPlayerID(), model);
        param2.addParameter(2);
        param2.addParameter(0);
        param2.addParameter(2);
        param2.addParameter(3);
        param2.addParameter(1);
        param2.addParameter(4);
        try {
            card.cardAction(param2);
        }
        catch(MoveNotAllowedException e){
            System.out.println(e.getMessage());
            fail();
        }

        param3 = new PlayerMoveParameters(player.getPlayerID(), model);
        param3.addParameter(4);
        param3.addParameter(1);
        param3.addParameter(2);
        param3.addParameter(0);
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
