package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

public class ColumnColourVariety extends PublicObjectiveCard {  //Colori diversi - Colonna
    /* Columns with no repeated colours */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean allDifferent=true;
        /*
        For each column, check if there are at least two dice with the same
        colour: if at least one column has all the dice of different colours
        the resultant score is 5, otherwise the score is 0
        */
        for(int i=0; i<WPC.NUMCOL; i++){
            allDifferent=true;
            for(int j=0; j<(WPC.NUMROW)-1; i++){
                /* fino a NUMROW-1 perchè il confronto va fatto fino al penultimo elemento con l'ultimo */
                for(int z=j+1; z<WPC.NUMROW; z++){
                    if(wpc.getCell(i,j).getDie().getDieColour().equals(wpc.getCell(i,z).getDie().getDieColour()))
                        allDifferent=false;
                }
            }
            if(allDifferent==true) score=5;
        }
        return score;
    }
}
