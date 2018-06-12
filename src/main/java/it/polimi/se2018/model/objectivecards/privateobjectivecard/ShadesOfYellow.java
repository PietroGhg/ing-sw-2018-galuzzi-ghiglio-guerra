package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for privateobjectivecard ShadesOfYellow
 * @author Leonardo Guerra
 */

public class ShadesOfYellow extends PrivateObjectiveCard {
    public ShadesOfYellow(){
        super("Shades of yellow");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the yellow dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        return privateScore(wpc, Colour.YELLOW);
    }
}
