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
    private Lathekin card;
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
        wpc.setDie(2, 0, new Die(2, Colour.GREEN));
        wpc.setDie(3, 2, new Die(6, Colour.RED));
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
        param.addParameter(2);
        param.addParameter(3);
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

    
    //da finire con gli altri test

    //Test to check favor tokens in TestGrozingPliers

}