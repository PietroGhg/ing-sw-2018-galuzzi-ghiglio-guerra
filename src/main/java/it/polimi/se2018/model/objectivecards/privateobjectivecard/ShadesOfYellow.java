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
        int score=0;
        Colour c = Colour.YELLOW;
        for (int i=0; i<WPC.NUMROW; i++) {
            for (int j = 0; j<WPC.NUMCOL; j++) {
                if(!(wpc.getCell(i,j).isEmpty())) {
                    if (wpc.getCell(i, j).getDie().getDieColour().equals(c)) {
                        score = score + wpc.getCell(i, j).getDie().getDieValue();
                    }
                }
            }
        }
        return score;
    }
}
