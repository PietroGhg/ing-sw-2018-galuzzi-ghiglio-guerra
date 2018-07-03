package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.objectivecards.ObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Abstract class for private objective card
 */
public abstract class PrivateObjectiveCard implements ObjectiveCard {
    private String name;
    public PrivateObjectiveCard(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    /**
     * Private objective card method privateScore
     * @param wpc player's board
     * @param c the colour the consider
     * @return the private objective card score
     */
    /*package-private*/int privateScore(WPC wpc, Colour c){
        int score=0;
        for (int i=0; i<WPC.NUMROW; i++) {
            for (int j = 0; j<WPC.NUMCOL; j++) {
                if (!(wpc.getCell(i,j).isEmpty()) && wpc.getCell(i, j).getDie().getDieColour().equals(c)) {
                    score = score + wpc.getCell(i, j).getDie().getDieValue();
                }
            }
        }
        return score;
    }
}
