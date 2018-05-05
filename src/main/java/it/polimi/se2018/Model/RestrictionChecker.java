package it.polimi.se2018.Model;

import it.polimi.se2018.Model.WPC.Cell;
import it.polimi.se2018.Model.WPC.WPC;

public class RestrictionChecker {

    public boolean checkAdjacent (WPC wpc, int row, int col){
        try {
            return  wpc.getCell(row-1, col) != null ||
                    wpc.getCell(row+1,col) != null ||
                    wpc.getCell(row, col-1) != null ||
                    wpc.getCell(row, col+1) != null ||
                    wpc.getCell(row-1,col-1) != null ||
                    wpc.getCell(row-1,col+1) != null ||
                    wpc.getCell(row+1,col-1) != null ||
                    wpc.getCell(row+1,col+1) != null ;
        }
        catch (IndexOutOfBoundsException e){
            //exception is thrown when the checked element is on the border, nothing to do with it
        }
        return false;
    }

    //checks that the first die is placed on the border of the window
    public boolean checkFirstMove (WPC wpc, int row, int col){
        return(!isEmpty(wpc) || row == 0 || row == WPC.NUMROW-1 || col == 0 || col == WPC.NUMCOL-1 );
    }

    //checks colour restriction
    public boolean checkColourRestrictions (WPC wpc, int row, int col, Die die){
        Cell temp = wpc.getCell(row, col);
        return temp.getColourR() == die.getDieColour();
    }

    //checks value restriction
    public boolean checkValueRestriction (WPC wpc, int row, int col, Die die){
        Cell temp = wpc.getCell(row, col);
        return temp.getValueR() == die.getDieValue();
    }
    public boolean similarDice (WPC wpc, Cell cell, Die die){
        return false;
    }
    public boolean checkEmptiness (WPC wpc, Cell cell){
        return false;
    }

    //returns true if the board doesn't contain a die
    private boolean isEmpty(WPC wpc) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++){
                if(wpc.getCell(i, j) != null) return false;
            }
        }
        return true;
    }
}
