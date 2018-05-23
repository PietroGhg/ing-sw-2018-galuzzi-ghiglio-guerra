package it.polimi.se2018.controller;

import it.polimi.se2018.controller.toolcard.ToolCard;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.Cell;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

public class RestrictionChecker {
    public static final String ADJACENT_ERROR = "Error: die must be adjacent to another die.";
    public static final String FIRSTMOVE_ERROR = "Error: first move, die must be on the border.";
    public static final String ENOUGHFAVORTOKENS_ERROR = "Error: not enough favor tokens.";
    public static final String COLOURRESTRICTION_ERROR = "Error: colour restriction violated.";
    public static final String VALUERESTRICTION_ERROR = "Error: colour restriction violated.";
    public static final String SAMEDIE_ERROR = "Error: same die orthogonally adjacent.";
    public static final String CELLNOTEMPTY_ERROR = "Error: the cell is empty.";
    public static final String EMPTY_ERROR = "Error: cell not empty.";
    public static final String RTCELLNOTEMPTY_ERROR = "Error: round track cell is empty.";
    public static final String DPCELLNOTEMPTY_ERROR = "Error: draft pool cell is empty.";

    /**
     *
     * @param p player
     * @param tc Tool Card
     * @throws MoveNotAllowedException if the player has not enough Favor Tokens to use the Tool Card
     * @author Leonardo Guerra
     */
    public void checkEnoughFavorTokens(Player p, ToolCard tc) throws MoveNotAllowedException{
        int t = p.getFavorTokens();
        int needed = tc.getFavorTokensNeeded();
        if(t < needed) throw new MoveNotAllowedException(ENOUGHFAVORTOKENS_ERROR);
    }

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
                throw new MoveNotAllowedException(ADJACENT_ERROR);

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
            throw new MoveNotAllowedException(FIRSTMOVE_ERROR);
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
            throw new MoveNotAllowedException(COLOURRESTRICTION_ERROR);
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
            throw new MoveNotAllowedException(VALUERESTRICTION_ERROR);
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
                    throw new MoveNotAllowedException(SAMEDIE_ERROR);
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
        if(wpc.getCell(row,col).isEmpty()) throw new MoveNotAllowedException(CELLNOTEMPTY_ERROR);
    }

    /**
     * Checks that a cell is empty
     * @param wpc the board
     * @param row coordinates
     * @param col coordinates
     * @throws MoveNotAllowedException if the cell is not empty
     */
    public void checkEmptiness (WPC wpc, int row, int col) throws MoveNotAllowedException {
        if (!(wpc.getCell(row, col).isEmpty())) throw new MoveNotAllowedException(EMPTY_ERROR);
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

    /**
     * Ckecks that a cell of the Round Track contains a die
     * @param roundTrack the round track
     * @param turn number of turn on the round track
     * @param index of the die in the chosen turn
     * @throws MoveNotAllowedException if the cell is empty
     */
    public void checkRTCellNotEmpty(ArrayList<ArrayList<Die>> roundTrack, int turn, int index) throws MoveNotAllowedException{
        if (!(roundTrack.get(turn).get(index)==null)) throw new MoveNotAllowedException(RTCELLNOTEMPTY_ERROR);
    }

    /**
     * Checks that a cell of the Draft Pool contains a die
     * @param draftPool
     * @param index
     * @throws MoveNotAllowedException
     */
    public void checkDPCellNotEmpty(ArrayList<Die> draftPool, int index) throws MoveNotAllowedException{
        if(draftPool.get(index)==null) throw new MoveNotAllowedException(DPCELLNOTEMPTY_ERROR);
    }

}
