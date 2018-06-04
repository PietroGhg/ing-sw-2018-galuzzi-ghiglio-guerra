package it.polimi.se2018.model.wpc;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;

import java.util.Arrays;
import java.util.Objects;

import static it.polimi.se2018.model.Die.prettyDie;

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

    /**
     * Copy constructor for wpc
     * @param wpc
     */
    public WPC(WPC wpc){
        board = new Cell[NUMROW][NUMCOL];
        favorTokens = wpc.getFavorTokens();
        name = wpc.getName();
        for(int i = 0; i < WPC.NUMROW; i++){
            for(int j = 0; j < WPC.NUMCOL; j++){
                board[i][j] = new Cell(wpc.getCell(i, j));
            }
        }
    }

    public Cell getCell (int row, int col){
        return board[row][col];
    }

    public void setFavorTokens(int favorTokens) {
        this.favorTokens = favorTokens;
    }

    public void setDie(int row, int col, Die d){
        board[row][col].setDie(d);
    }

    public void removeDie(int row, int col) { board[row][col].getDie().remove(); }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavorTokens() {
        return favorTokens;
    }

    public String getName() {
        return name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WPC wpc = (WPC) o;
        boolean same = true;
        for(int i = 0; i < NUMROW && same; i++){
            for(int j = 0; j < NUMCOL && same; j++){
                same = board[i][j].equals(wpc.getCell(i,j));
            }
        }
        return getFavorTokens() == wpc.getFavorTokens() &&
                same &&
                Objects.equals(getName(), wpc.getName());
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < NUMROW; i++){

            for(int j = 0; j < NUMCOL; j++){
                //builder.append(i + "," + j +": " );
                if(board[i][j].isEmpty()) {
                    if(board[i][j].getColourR() != null) builder.append(board[i][j].getColourR().letter());
                    else if(board[i][j].getValueR() != null) builder.append(prettyDie(board[i][j].getValueR()));
                    else builder.append(" ");
                }
                else {
                    builder.append(board[i][j].getDie().toString());
                }
                builder.append("|");
            }
            builder.append("\n");
        }

        return builder.toString();
    }


    @Override
    public int hashCode() {

        int result = Objects.hash(getFavorTokens(), getName());
        result = 31 * result + Arrays.hashCode(board);
        return result;
    }
}
