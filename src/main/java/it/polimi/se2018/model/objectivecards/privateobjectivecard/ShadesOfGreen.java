package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for privateobjectivecard ShadesOfGreen
 * @author Leonardo Guerra
 */

public class ShadesOfGreen extends PrivateObjectiveCard {
    public ShadesOfGreen(){
        super("ShadesOfGreen");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the green dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        return privateScore(wpc, Colour.GREEN);
    }
}
