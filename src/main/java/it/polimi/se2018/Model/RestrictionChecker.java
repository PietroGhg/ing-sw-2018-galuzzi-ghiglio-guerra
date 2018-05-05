package it.polimi.se2018.Model;

import it.polimi.se2018.Model.WPC.Cell;
import it.polimi.se2018.Model.WPC.WPC;

public class RestrictionChecker {
    public boolean checkAdjacent (WPC wpc, Cell cell){

    }
    public boolean checkFirstMove (WPC wpc, int row, int col){
        if(!isEmpty(wpc) || row == 0 || row == WPC.NUMROW-1 || col == 0 || col == WPC.NUMCOL-1 ) return true;
        else return false;
    }
    public boolean checkWPCRestrictions (WPC wpc, Cell cell, Die die){

    }
    public boolean similarDice (WPC wpc, Cell cell, Die die){

    }
    public boolean checkEmptiness (WPC wpc, Cell cell){

    }

    private boolean isEmpty(WPC wpc) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++){
                if(wpc.getCell(i, j) != null) return false;
            }
        }
        return true;
    }
}
