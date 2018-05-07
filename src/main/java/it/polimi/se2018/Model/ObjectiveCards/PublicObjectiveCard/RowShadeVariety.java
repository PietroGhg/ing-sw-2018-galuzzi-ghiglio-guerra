package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

public class RowShadeVariety extends PublicObjectiveCard {  //Sfumature diverse - Riga
    /* Rows with no repeated values */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean allDifferent=true;
        /*
        For each row, check if there are at least two dice with the same
        value: if at least one row has all the dice of different values
        the resultant score is 5, otherwise the score is 0
        */
        for(int j=0; j<WPC.NUMROW; j++){
            allDifferent=true;
            for(int i=0; i<(WPC.NUMCOL)-1; i++){
                /* fino a NUMCOL-1 perchè il confronto va fatto fino al penultimo elemento con l'ultimo */
                for(int z=i+1; z<WPC.NUMROW; z++){
                    if(wpc.getCell(i,j).getDie().getDieValue().equals(wpc.getCell(i,z).getDie().getDieValue()))
                        allDifferent=false;
                }
            }
            if(allDifferent==true) score=5;
        }
        return score;
    }
}
