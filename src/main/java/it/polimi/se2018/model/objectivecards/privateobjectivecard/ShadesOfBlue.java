package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for privateobjectivecard ShadesOfBlue
 * @author Leonardo Guerra
 */

public class ShadesOfBlue extends PrivateObjectiveCard {
    public ShadesOfBlue(){
        super("Shades of Blue");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the blue dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        return privateScore(wpc, Colour.BLUE);
    }
}
