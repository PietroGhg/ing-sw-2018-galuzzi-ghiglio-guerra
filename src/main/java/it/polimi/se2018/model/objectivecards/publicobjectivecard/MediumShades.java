package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for publicobjectivecard MediumShades
 * @author Leonardo Guerra
 */

public class MediumShades extends PublicObjectiveCard { //Sfumature medie
    public MediumShades(){
        super("MediumShades");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sets of 3 and 4 values anywhere
     * @param wpc player board, on which the score is calculated
     */
    public int getScore(WPC wpc){
        return differentShades(wpc,3); //n=3, n+1=4
    }
}
