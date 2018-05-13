package it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.WPC.WPC;

/**
 * Class for PrivateObjectiveCard ShadesOfPurple
 * @author Leonardo Guerra
 */

public class ShadesOfPurple extends PrivateObjectiveCard {
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the purple dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        int score=0;
        Colour c = Colour.PURPLE;
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
