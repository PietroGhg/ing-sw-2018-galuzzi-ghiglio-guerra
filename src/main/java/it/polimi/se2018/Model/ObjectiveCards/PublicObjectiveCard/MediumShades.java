package it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard;

import it.polimi.se2018.Model.WPC.WPC;
import it.polimi.se2018.Model.WPC.WpcGenerator;

public class MediumShades extends PublicObjectiveCard { //Sfumature medie
    /* Sets of 3 and 4 values anywhere */
    @Override
    public int getScore(WPC wpc){
        int score=0;
        boolean allValues=false;
        /*
        Check, for each value, if there's at least one element
        in the whole board with that value: if one of the values has false check,
        the resulting score is 0. Otherwise, the score is 2.
        */
        for (int val=3; val<=4; val++){
            allValues = false;
            for (int i=0; i<WPC.NUMROW; i++) {
                for (int j = 0; j < WPC.NUMCOL; j++) {
                    if (wpc.getCell(i,j).getDie().getDieValue().equals(Integer.valueOf(val))) allValues = true;
                }
            }
            if(allValues==false) return score;
        }
        if(allValues==true) score=2;
        return score;
    }

    public static void main (String[] args) {
        WpcGenerator gen = new WpcGenerator();
        WPC wpc = gen.getWPC(1);
        MediumShades ms = new MediumShades();
        System.out.println(ms.getScore(wpc));
    }
}
