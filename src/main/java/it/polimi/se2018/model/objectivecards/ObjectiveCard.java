package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.model.wpc.WPC;

public abstract class ObjectiveCard {
    private String cardName;
    private String description;
    public abstract int getScore(WPC wpc);
}

//TODO: creare metodo in PublicObjectiveCard per ridurre codice ripetuto (?)

//TODO: unire i getScore di Deep/Medium/Light Shades in PublicObjectiveCard per ridurre codice ripetuto