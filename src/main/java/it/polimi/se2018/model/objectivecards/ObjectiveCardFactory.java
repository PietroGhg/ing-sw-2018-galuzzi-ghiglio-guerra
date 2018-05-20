package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;

public interface ObjectiveCardFactory {
    PrivateObjectiveCard getPrivateCard();
    PublicObjectiveCard getPublicCard();
}
