package it.polimi.se2018;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.WPC;
import it.polimi.se2018.model.wpc.WpcGenerator;
import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Test for WpcGenerator, wpc.equals() and consequently cell.equals() and die.equals()
 */
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
        WpcGenerator gen = WpcGenerator.getInstance();
        WPC wpc = gen.getWPC(1);
        assertEquals(testWPC, testWPC2);
        assertEquals(testWPC, wpc);
    }

    @Test
    public void testNumWpc(){
        WpcGenerator gen = WpcGenerator.getInstance();
        assertEquals(24, gen.getNumWpcs()); //change the value if number of wpcs is changed
    }

    /**
     * Tests that no parsing exceptions are thrown wile loading any of the boards
     * @author Pietro Ghiglio
     */
    @Test
    public void checkWPCs(){
        WpcGenerator generator = WpcGenerator.getInstance();
        WPC temp;
        int numWpcs = generator.getNumWpcs();

        for(int i = 1; i <= numWpcs; i++){
            temp = generator.getWPC(i);
        }
        temp = generator.getWPC(1);
        filler(temp);
        System.out.println(temp.toString());

    }

    private void filler(WPC wpc){
        wpc.setDie(0, 3, new Die(3, Colour.PURPLE));
        wpc.setDie(0, 4, new Die(5, Colour.RED));
        wpc.setDie(1, 1, new Die(1, Colour.RED));
        wpc.setDie(1, 2, new Die(5, Colour.BLUE));
        wpc.setDie(2, 3, new Die(3, Colour.PURPLE));
    }
}
