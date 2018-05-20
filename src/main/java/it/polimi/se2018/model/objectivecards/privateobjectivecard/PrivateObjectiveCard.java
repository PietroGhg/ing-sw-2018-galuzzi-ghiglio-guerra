package it.polimi.se2018.model.objectivecards.privateobjectivecard;

import it.polimi.se2018.model.objectivecards.ObjectiveCard;

public abstract class PrivateObjectiveCard extends ObjectiveCard {
    private String name;
    public PrivateObjectiveCard(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
