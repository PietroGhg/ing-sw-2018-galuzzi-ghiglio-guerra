package it.polimi.se2018.controller;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.wpc.Cell;
import it.polimi.se2018.model.wpc.WPC;

public class RestrictionChecker {

    /**
     *
     * @param wpc the board
     * @param row coordinate of the cell
     * @param col coordinate of the cell
     * @throws MoveNotAllowedException if the restriction is violated
     * @author Pietro Ghiglio
     */
    public void checkAdjacent (WPC wpc, int row, int col) throws MoveNotAllowedException{

            if( !( !checkCell(wpc,row-1, col) ||
                    !checkCell(wpc,row+1, col) ||
                    !checkCell(wpc, row, col-1) ||
                    !checkCell(wpc, row, col+1) ||
                    !checkCell(wpc, row-1,col-1) ||
                    !checkCell(wpc, row-1,col+1) ||
                    !checkCell(wpc, row+1,col-1) ||
                    !checkCell(wpc, row+1,col+1) ) )
                throw new MoveNotAllowedException("Die must be adjacent to another die.");

    }

    //method that handles cells on the border of the matrix
    //returns false if the cell contains a die
    private boolean checkCell(WPC wpc, int row, int col){
        try {
            return wpc.getCell(row, col).isEmpty();
        }
        catch (IndexOutOfBoundsException e){
            return true;
        }
    }

    /**
     * Checks the first move restriction: if the board is empty, the die must be placed on the border
     * @param wpc the board
     * @param row coordinate of the cell
     * @param col coordinate of the cell
     * @throws MoveNotAllowedException if the restriction is violated
     */
    public void checkFirstMove (WPC wpc, int row, int col) throws  MoveNotAllowedException{
        if (!(!isEmpty(wpc) || row == 0 || row == WPC.NUMROW-1 || col == 0 || col == WPC.NUMCOL-1 ))
            throw new MoveNotAllowedException("First move: die must be on the border.");
    }

    /**
     * Checks colour restriction
     * @param wpc the board
     * @param row coordinates of the cell
     * @param col coordinates of the cell
     * @param die die that needs to be placed
     * @throws MoveNotAllowedException
     */
    public void checkColourRestriction (WPC wpc, int row, int col, Die die) throws MoveNotAllowedException{
        Cell temp = wpc.getCell(row, col);
        if (temp.getColourR() != die.getDieColour())
            throw new MoveNotAllowedException("Color restriction violated.");
    }

    /**
     * Checks value restriction
     * @param wpc the board
     * @param row coordinates of the cell
     * @param col coordinates of the cell
     * @param die die that needs to be placed
     * @throws MoveNotAllowedException if the restriction is violated
     */
    public void checkValueRestriction (WPC wpc, int row, int col, Die die) throws MoveNotAllowedException{
        Cell temp = wpc.getCell(row, col);
        if(! (die.getDieValue().equals(temp.getValueR())))
            throw new MoveNotAllowedException("Value restriction violated.");
    }

    /**
     * Checks that there's no die with the same value and colour orthogonally adjacent to the one that
     * needs to be placed
     * @param wpc the board
     * @param row coordinate
     * @param col coordinate
     * @param die die that needs to be placed
     * @throws MoveNotAllowedException if the restriction is violated
     */
    public void checkSameDie(WPC wpc, int row, int col, Die die) throws MoveNotAllowedException{
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
            if(temp[i] != null) {
                if (temp[i].getDieValue() == die.getDieValue() &&
                        temp[i].getDieColour().equals(die.getDieColour()))
                    throw new MoveNotAllowedException("Same die orthogonally adjacent.");
            }
            }

    }

    /**
     * Checks that a cell contains a die
     * @param wpc the boards
     * @param row coordinates
     * @param col coordinates
     * @throws MoveNotAllowedException if the cell does not contain a die
     */
    public void checkNotEmpty (WPC wpc, int row, int col) throws MoveNotAllowedException{
        if(wpc.getCell(row,col).isEmpty()) throw new MoveNotAllowedException("Error: cell is empty.");
    }

    /**
     * Checks that a cell is empty
     * @param wpc the board
     * @param row coordinates
     * @param col coordinates
     * @throws MoveNotAllowedException if the cell is not empty
     */
    public void checkEmptiness (WPC wpc, int row, int col) throws MoveNotAllowedException {
        if (!(wpc.getCell(row, col).isEmpty())) throw new MoveNotAllowedException("Error: cell not empty.");
    }

    //returns true if the board doesn't contain a die
    private boolean isEmpty(WPC wpc) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++){
                if(!wpc.getCell(i,j).isEmpty()) return false;
            }
        }
        return true;
    }

}
