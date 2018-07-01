package it.polimi.se2018;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.Cell;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCell {
    private Cell cell;

    @Test
    public void test(){
        cell = new Cell();
        assertEquals(true,cell.isEmpty());
        cell.setValueR(1);
        cell.setColourR(Colour.BLUE);
        Cell cell2 = new Cell();
        assertEquals(false, cell2.equals(cell));
        cell2.setValueR(1);
        cell2.setColourR(Colour.BLUE);
        assertEquals(true, cell2.equals(cell));
    }
}
