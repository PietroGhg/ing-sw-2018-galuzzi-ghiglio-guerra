package it.polimi.se2018;
import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.WPC.WPC;
import it.polimi.se2018.Model.WPC.WpcGenerator;
import org.junit.*;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class TestWPCGenerator {
    private WPC testWPC;
    private  WPC testWPC2;

    //sets up the wpc
    @Before
    public void setupTest(){
        testWPC = new WPC();
        testWPC.getCell(0, 0).setValueR(4);
        testWPC.getCell(0,2).setValueR(2);
        testWPC.getCell(0, 3).setValueR(5);
        testWPC.getCell(0, 4).setColourR(Colour.GREEN);
        testWPC.getCell(1,2).setValueR(6);
        testWPC.getCell(1,3).setColourR(Colour.GREEN);
        testWPC.getCell(1, 4).setValueR(2);
        testWPC.getCell(2,1 ).setValueR(3);
        testWPC.getCell(2,2).setColourR(Colour.GREEN);
        testWPC.getCell(2,3).setValueR(4);
        testWPC.getCell(3,0).setValueR(5);
        testWPC.getCell(3,1).setColourR(Colour.GREEN);
        testWPC.getCell(3,2).setValueR(1);
        testWPC.setName("Virtus");
        testWPC.setFavorTokens(5);

        testWPC2 = new WPC();
        testWPC2.getCell(0, 0).setValueR(4);
        testWPC2.getCell(0,2).setValueR(2);
        testWPC2.getCell(0, 3).setValueR(5);
        testWPC2.getCell(0, 4).setColourR(Colour.GREEN);
        testWPC2.getCell(1,2).setValueR(6);
        testWPC2.getCell(1,3).setColourR(Colour.GREEN);
        testWPC2.getCell(1, 4).setValueR(2);
        testWPC2.getCell(2,1 ).setValueR(3);
        testWPC2.getCell(2,2).setColourR(Colour.GREEN);
        testWPC2.getCell(2,3).setValueR(4);
        testWPC2.getCell(3,0).setValueR(5);
        testWPC2.getCell(3,1).setColourR(Colour.GREEN);
        testWPC2.getCell(3,2).setValueR(1);
        testWPC2.setName("Virtus");
        testWPC2.setFavorTokens(5);
    }

    @Test
    public void testWPC(){
        WpcGenerator gen = new WpcGenerator();
        WPC wpc = gen.getWPC(1);
        assertEquals(testWPC, testWPC2);
        assertEquals(testWPC, wpc);
    }
}
