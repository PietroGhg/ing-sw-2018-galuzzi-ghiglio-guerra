package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.WPC.WPC;

public class ColourVariety extends PublicObjectiveCard { //Colori diversi
    /* Sets of one of each colour anywhere */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean allColours=false;
        /*
        Check, for each colour, if there's at least one element
        in the whole board with that colour: if one of the colours has
        false check, the resulting score is 0. Else, the score is 4.
        */
        for (Colour c : Colour.values()){
            allColours = false;
            for (int i=0; i<WPC.NUMCOL; i++) {
                for (int j = 0; j < WPC.NUMROW; j++) {
                    if (wpc.getCell(i, j).getDie().getDieColour().equals(c)) allColours = true;
                }
            }
            if(allColours==false) return score;
        }
        if(allColours==true) score=4;
        return score;
    }
}
