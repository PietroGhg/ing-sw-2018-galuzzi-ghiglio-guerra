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
    CopperFoilBurnisher card;
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
        wpc.setDie(0, 4, new Die(2,Colour.YELLOW));
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
        wpc.setDie(3, 3, new Die(2,Colour.YELLOW));
    }

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

    //da finire

    //Test to check favor tokens in TestGrozingPliers
}
