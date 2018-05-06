package it.polimi.se2018.Model;

import it.polimi.se2018.Model.WPC.Cell;
import it.polimi.se2018.Model.WPC.WPC;

public class RestrictionChecker {

    //return true if there's at least one adjacent die
    public boolean checkAdjacent (WPC wpc, int row, int col){

            return  !checkCell(wpc,row-1, col) ||
                    !checkCell(wpc,row+1, col) ||
                    !checkCell(wpc, row, col-1) ||
                    !checkCell(wpc, row, col+1) ||
                    !checkCell(wpc, row-1,col-1) ||
                    !checkCell(wpc, row-1,col+1) ||
                    !checkCell(wpc, row+1,col-1) ||
                    !checkCell(wpc, row+1,col+1) ;

    }

    //method that handles cells on the border of the matrix
    private boolean checkCell(WPC wpc, int row, int col){
        try {
            return wpc.getCell(row, col).isEmpty();
        }
        catch (IndexOutOfBoundsException e){
            return true;
        }
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

    //returns false if there's a die with same value and colour orthogonally adjacent
    public boolean sameDie (WPC wpc, int row, int col, Die die){
        Die[] temp = new Die[4];
        try {
            temp[0] = wpc.getCell(row - 1, col).getDie();
        }
        catch (IndexOutOfBoundsException e) {
            temp[0] = null;
        }
        try {
            temp[1] = wpc.getCell(row + 1, col).getDie();
        }
        catch (IndexOutOfBoundsException e) {
            temp[1] = null;
        }
        try {
            temp[2] = wpc.getCell(row, col - 1).getDie();
        }
        catch (IndexOutOfBoundsException e) {
            temp[2] = null;
        }
        try {
            temp[3] = wpc.getCell(row, col + 1).getDie();
        }
        catch (IndexOutOfBoundsException e) {
            temp[3] = null;
        }

        for(int i = 0; i< 4; i++){
            if (temp[i] != null && temp[i].getDieValue().equals(die.getDieValue()) &&
                    temp[i].getDieColour().equals(die.getDieColour())) return false;
            }
            return true;

    }

    //returns true if the cell is empty (contains no die)
    public boolean checkEmptiness (WPC wpc, int row, int col){
        return wpc.getCell(row, col).isEmpty();
    }

    //returns true if the board doesn't contain a die
    private boolean isEmpty(WPC wpc) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++){
                if(!checkEmptiness(wpc, i, j)) return false;
            }
        }
        return true;
    }

}
