package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

public class ShadeVariety extends PublicObjectiveCard { //Sfumature diverse
    /* Sets of one of each value anywhere */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean allValues=false;
        /*
        Check, for each value, that there's at least one element
        in the whole board with that value: if one of the values has false check,
        the resulting score is 0. Otherwise, the score is 4.
        */
        for (int val=1; val<=6; val++){
            allValues = false;
            for (int i=0; i<WPC.NUMROW; i++) {
                for (int j = 0; j < WPC.NUMCOL; j++) {
                    if (wpc.getCell(i,j).getDie().getDieValue()==val) allValues = true;
                }
            }
            if(allValues==false) return score;
        }
        if(allValues==true) score=4;
        return score;
    }
}
