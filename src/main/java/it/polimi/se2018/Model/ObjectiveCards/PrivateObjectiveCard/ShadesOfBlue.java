package it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.ColumnColourVariety;
import it.polimi.se2018.Model.WPC.WPC;
import it.polimi.se2018.Model.WPC.WpcGenerator;

/**
 * Class for PrivateObjectiveCard ShadesOfBlue
 * @author Leonardo Guerra
 */

public class ShadesOfBlue extends PrivateObjectiveCard {
    @Override
    /**
     * Method for the computation of a partial score:
     * sum of the value of the blue dice
     * @param wpc player board, on which the score is calculated
     */
    public int getScore (WPC wpc) {
        int score=0;
        Colour c = Colour.BLUE;
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

    /*
    public static void main (String[] args) {
        WpcGenerator gen = new WpcGenerator();
        WPC wpc = gen.getWPC(1);
        wpc.setDie(0, 1, new Die(4, Colour.GREEN));
        wpc.setDie(0, 2, new Die(2, Colour.RED));
        wpc.setDie(0, 3, new Die(5, Colour.YELLOW));
        wpc.setDie(0, 4, new Die(6, Colour.PURPLE));
        wpc.setDie(1, 0, new Die(3, Colour.RED));
        wpc.setDie(1, 1, new Die(1, Colour.BLUE));
        wpc.setDie(1, 3, new Die(1, Colour.GREEN));
        wpc.setDie(1, 4, new Die(2, Colour.YELLOW));
        wpc.setDie(2, 0, new Die(5, Colour.PURPLE));
        wpc.setDie(2, 1, new Die(6, Colour.GREEN));
        wpc.setDie(2, 3, new Die(6, Colour.PURPLE));
        wpc.setDie(2, 4, new Die(3, Colour.RED));
        wpc.setDie(3, 0, new Die(4, Colour.BLUE));
        wpc.setDie(3, 1, new Die(3, Colour.YELLOW));
        wpc.setDie(3, 2, new Die(4, Colour.GREEN));
        wpc.setDie(3, 3, new Die(2, Colour.BLUE));
        wpc.setDie(3, 4, new Die(4, Colour.GREEN));
        ShadesOfBlue sb = new ShadesOfBlue();
        System.out.println(sb.getScore(wpc));
    }
    */
}
