package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for publicobjectivecard ColumnColourVariety
 * @author Leonardo Guerra
 */

public class ColumnColourVariety extends PublicObjectiveCard {  //Colori diversi - Colonna
    public ColumnColourVariety(){
        super("ColumnColourVariety");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * Columns with no repeated colours
     * @param wpc player board, on which the score is calculated
     */
    public int getScore(WPC wpc){
        int score=0;
        boolean fullCol;
        boolean allDifferent;
        /*
        For each column, check if there are at least two dice with the same
        colour: if the column is complete and has all the dice with a different colour
        the resultant score of that column is 5, otherwise that score is 0
        */
        for(int j=0; j<WPC.NUMCOL; j++){
            fullCol=true;
            // Check the column is full of dice
            for(int i=0; i<WPC.NUMROW; i++){
                if(wpc.getCell(i,j).isEmpty()) fullCol=false;
            }
            if(fullCol){
                allDifferent=true;
                for(int i=0; i<(WPC.NUMROW)-1; i++){
                /* till NUMROW-1: the check is done till the last with the second-last */
                    for(int z=i+1; z<WPC.NUMROW; z++){
                    /* check starts from the next element */
                        if (wpc.getCell(i, j).getDie().getDieColour().equals(wpc.getCell(z, j).getDie().getDieColour()))
                            allDifferent = false;
                    }
                }
                if(allDifferent) score = score + 5;
            }
        }
        return score;
    }
}
