package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for publicobjectivecard LightShades
 * @author Leonardo Guerra
 */

public class LightShades extends PublicObjectiveCard {  //Sfumature chiare
    public LightShades(){
        super("Light Shades");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sets of 1 and 2 values anywhere
     * @param wpc player board, on which the score is calculated
     */
    public int getScore(WPC wpc){
        return differentShades(wpc,1); //n=1, n+1=2
    }
}
