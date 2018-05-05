package it.polimi.se2018.Model.WPC;

public class WPC { //WindowPatternCard
    public final static int NUMROW = 4;
    public final static int NUMCOL = 5;
    private Cell[][] board;
    private int favorTokens;
    private String name;

    public WPC() {
        board = new Cell[NUMROW][NUMCOL];
        for(int i = 0; i < NUMROW; i++){
            for(int j = 0; j < NUMCOL; j++){
                board[i][j] = new Cell();
            }
        }
    }
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
