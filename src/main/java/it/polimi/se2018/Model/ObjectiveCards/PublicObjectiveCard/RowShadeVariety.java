package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

public class RowShadeVariety extends PublicObjectiveCard {  //Sfumature diverse - Riga
    /* Rows with no repeated values */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean fullRow;
        boolean allDifferent;
        /*
        For each row, check if there are at least two dice with the same
        value: if the checked row has all the dice of different values
        the resultant score of the row is 5, otherwise that score is 0
        */
        for(int i=0; i<WPC.NUMROW; i++){
            fullRow=true;
            // Check the row is full of dice
            for(int j=0; j<WPC.NUMCOL; j++){
                if(wpc.getCell(i,j).isEmpty()) fullRow=false;
            }
            if(fullRow==true){
                allDifferent=true;
                for(int j=0; j<(WPC.NUMCOL)-1; j++){
                    /* till NUMROW-1: the check is done till the last with the second-last */
                    for(int z=j+1; z<WPC.NUMCOL; z++){
                        /* check starts from the next element  */
                        if (wpc.getCell(i, j).getDie().getDieValue().equals(wpc.getCell(i, z).getDie().getDieValue()))
                            allDifferent = false;
                    }
                }
                if(allDifferent==true) score = score + 5;
            }
        }
        return score;
    }
}
