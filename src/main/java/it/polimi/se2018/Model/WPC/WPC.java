package it.polimi.se2018.Model.WPC;

public class WPC { //WindowPatternCard
    private Cell[][] board;
    private int favorTokens;
    private String name;
    public Cell getCell (int row, int col){
        return board[row][col];
    }

    public void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void setName(String name) {
        this.name = name;
    }
}
