package it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.WPC.WPC;

public class ShadesOfPurple extends PrivateObjectiveCard {
    @Override
    public int getScore (WPC wpc) {
        int score=0;
        Colour c = Colour.PURPLE;
        for (int i=0; i<WPC.NUMCOL; i++) {
            for (int j = 0; j<WPC.NUMROW; j++) {
                if (wpc.getCell(i, j).getDie().getDieColour().equals(c)) {
                    score = score + wpc.getCell(i, j).getDie().getDieValue();
                }
            }
        }
        return score;
    }
}
