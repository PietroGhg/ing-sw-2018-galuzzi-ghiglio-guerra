package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for privateobjectivecard ShadesOfRed
 * @author Leonardo Guerra
 */

public class ShadesOfRed extends PrivateObjectiveCard {
    public ShadesOfRed(){
        super("ShadesOfRed");
    }
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the red dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        return privateScore(wpc, Colour.RED);
    }
}
