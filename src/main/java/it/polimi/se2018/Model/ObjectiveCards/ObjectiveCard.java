package it.polimi.se2018.Model.ObjectiveCards;

import it.polimi.se2018.Model.WPC.WPC;

public abstract class ObjectiveCard {
    private String cardName;
    private String description;
    public abstract int getScore(WPC wpc);
}
