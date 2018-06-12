package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for publicobjectivecard DeepShades
 * @author Leonardo Guerra
 */

public class DeepShades extends PublicObjectiveCard {   //Sfumature scure
    public DeepShades(){
        super("Deep Shades");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sets of 5 and 6 values anywhere
     * @param wpc player board, on which the score is calculated
     */
    public int getScore(WPC wpc){
        return differentShades(wpc,5); //n=5, n+1=6
    }
}
