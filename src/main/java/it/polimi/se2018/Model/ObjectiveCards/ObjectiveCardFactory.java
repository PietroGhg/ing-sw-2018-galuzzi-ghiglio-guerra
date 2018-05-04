package it.polimi.se2018.Model.ObjectiveCards;

import it.polimi.se2018.Model.ObjectiveCards.PrivateObjectiveCard.PrivateObjectiveCard;
import it.polimi.se2018.Model.ObjectiveCards.PublicObjectiveCard.PublicObjectiveCard;

public interface ObjectiveCardFactory {
    public PrivateObjectiveCard getPrivateCard();
    public PublicObjectiveCard getPublicCard();
}
