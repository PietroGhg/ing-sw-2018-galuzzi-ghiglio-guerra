package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for publicobjectivecard LightShades
 * @author Leonardo Guerra
 */

public class LightShades extends PublicObjectiveCard {  //Sfumature chiare
    @Override
    /**
     * Method for the computation of a partial score:
     * sets of 1 and 2 values anywhere
     * @param wpc player board, on which the score is calculated
     */
    public int getScore(WPC wpc){
        int score=0;
        int sets=20; //random value, chosen to be sure it is big enough to avoid errors
        /*
        Check, for each set of value 1 and 2, how many couples are
        in the whole board. For every set the score is increased by 2
        */
        for (int val=1; val<=2; val++){
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
