package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.objectivecards.ObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;

public abstract class PrivateObjectiveCard extends ObjectiveCard {
    private String name;
    public PrivateObjectiveCard(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    protected int privateScore(WPC wpc, Colour c){
        int score=0;
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
