package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for privateobjectivecard ShadesOfPurple
 * @author Leonardo Guerra
 */

public class ShadesOfPurple extends PrivateObjectiveCard {
    public ShadesOfPurple(){
        super("Shades of Purple");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the purple dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        return privateScore(wpc, Colour.PURPLE);
    }
}
