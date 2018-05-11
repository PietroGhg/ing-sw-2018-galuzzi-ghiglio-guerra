package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;

public class DeepShades extends PublicObjectiveCard {   //Sfumature scure
    /* Sets of 5 and 6 values anywhere */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        int sets=20; //random value, chosen to be sure it is big enough to avoid errors
        /*
        Check, for each set of value 1 and 2, how many couples are
        in the whole board. For every set the score is increased by 2
        */
        for (int val=5; val<=6; val++){
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
        score = sets * 2;
        return score;
    }
}
