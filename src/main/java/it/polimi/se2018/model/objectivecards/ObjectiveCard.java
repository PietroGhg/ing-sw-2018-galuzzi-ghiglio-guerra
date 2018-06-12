package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.wpc.WPC;

public abstract class ObjectiveCard {
    private String cardName;
    private String description;
    public abstract int getScore(WPC wpc);
    protected int differentShades(WPC wpc, int n){
        int score=0;
        int sets=20; //random value, chosen to be sure it is big enough to avoid errors
        /*
        Check, for each set of value 1 and 2, how many couples are
        in the whole board. For every set the score is increased by 2
        */
        for (int val=n; val<=n+1; val++){
            int count=0;
            for (int i=0; i<WPC.NUMROW; i++) {
                for (int j = 0; j < WPC.NUMCOL; j++) {
                    if(!(wpc.getCell(i,j).isEmpty())) {
                        if(wpc.getCell(i, j).getDie().getDieValue().equals(val)) count++;
                    }
                }
            }
            if(count<sets) sets=count;
        }
        score = sets * 2;
        return score;
    }
}

//TODO: creare metodo in PublicObjectiveCard per ridurre codice ripetuto (?)

//TODO: unire i getScore di Deep/Medium/Light Shades in PublicObjectiveCard per ridurre codice ripetuto