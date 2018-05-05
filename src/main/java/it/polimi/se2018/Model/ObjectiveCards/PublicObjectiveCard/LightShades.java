package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

public class LightShades extends PublicObjectiveCard {  //Sfumature chiare
    /* Sets of 1 and 2 values anywhere */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean allValues=false;
        /*
        Check, for each value, if there's at least one element
        in the whole board with that value: if one of the values has false check,
        the resulting score is 0. Otherwise, the score is 2.
        */
        for (int val=1; val<=2; val++){
            allValues = false;
            for (int i=0; i<WPC.NUMCOL; i++) {
                for (int j = 0; j < WPC.NUMROW; j++) {
                    if (wpc.getCell(i,j).getDie().getDieValue()==val) allValues = true;
                }
            }
            if(allValues==false) return score;
        }
        if(allValues==true) score=2;
        return score;
    }
}
