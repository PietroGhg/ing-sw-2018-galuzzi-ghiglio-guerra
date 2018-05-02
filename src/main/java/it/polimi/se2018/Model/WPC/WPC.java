package it.polimi.se2018.Model.WPC;

public class WPC { //WindowPatternCard
    private Cell[][] board;
    private int favorTokens;
    public Cell getCell (int row, int col){
        return board[row][col];
    }
}
