package it.polimi.se2018.model.objectivecards.publicobjectivecard;

import it.polimi.se2018.model.objectivecards.ObjectiveCard;
import it.polimi.se2018.model.wpc.WPC;

public abstract class PublicObjectiveCard extends ObjectiveCard {
    private String name;

    public PublicObjectiveCard(String name){
        this.name = name;
    }

    public abstract int getScore(WPC wpc);

    public String getName(){
        return name;
    }

}
