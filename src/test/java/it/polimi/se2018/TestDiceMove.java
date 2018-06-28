package it.polimi.se2018;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.controller.VCDieMessage;
import it.polimi.se2018.model.*;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestDiceMove {
    private Model model;
    private Controller controller;
    private ArrayList<Die> dp;
    private Player p1;
    private Player p2;
    private WPC expected;

    @Before
    public void setup(){
        dp = new ArrayList<>();
        dp.add(new Die(1,Colour.BLUE));
        dp.add(new Die(6, Colour.YELLOW));
        model = new Model();
        controller = new Controller(model, 10, 10);
        Extractor.resetInstance();
        WpcGenerator gen = WpcGenerator.getInstance();
        WPC wpc1 = gen.getWPC(1);
        WPC wpc2 = gen.getWPC(2);
        wpc1.setDie(0,0, new Die(4, Colour.YELLOW));
        wpc2.setDie(0,0, new Die(1, Colour.YELLOW));
        p1 = new Player(1);
        p2 = new Player(2);
        p1.setWpcOnly(wpc1);
        p2.setWpcOnly(wpc2);
        model.addPlayer(p1);
        model.addPlayer(p2);

        expected = gen.getWPC(1);
        expected.setDie(0,0, new Die(4, Colour.YELLOW));
        expected.setDie(1,1, new Die(1, Colour.BLUE));
        model.startGame();
        model.setDraftPool(dp);
    }


    @Test
    public void test(){
        VCDieMessage message = new VCDieMessage(p1.getPlayerID());
        message.addParameter(0);
        message.addParameter(1);
        message.addParameter(1);

        assertNotEquals(expected, p1.getWpc());

        controller.update(message);

        assertEquals(expected, p1.getWpc());
    }

}
