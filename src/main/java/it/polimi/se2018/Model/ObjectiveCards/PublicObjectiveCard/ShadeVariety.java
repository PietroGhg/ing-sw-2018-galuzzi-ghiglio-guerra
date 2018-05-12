package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

/**
 * Class for PublicObjectiveCard ShadeVariety
 * @author Leonardo Guerra
 */

public class ShadeVariety extends PublicObjectiveCard { //Sfumature diverse
    @Override
    /**
     * Method for the computation of a partial score:
     * sets of one of each value anywhere
     * @param wpc player board, on which the score is calculated
     */
    public int getScore(WPC wpc){
        int score=0;
        int sets=20; //random value, chosen to be sure it is big enough to avoid errors
        /*
        Check, for each colour, how many complete sets of colours are
        in the whole board. For every set the score is increased by 4
        */
        for (int val=1; val<=6; val++){
            int count=0;
            for (int i=0; i<WPC.NUMROW; i++) {
                for (int j = 0; j < WPC.NUMCOL; j++) {
                    if(!(wpc.getCell(i,j).isEmpty())) {
                        if(wpc.getCell(i, j).getDie().getDieValue().equals(val)) count++;
                    }
                }
            }
            if(count<sets) sets=count;
        }
        score = sets * 4;
        return score;
    }
}
