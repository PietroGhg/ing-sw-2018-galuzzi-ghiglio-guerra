package it.polimi.se2018.model.objectivecards;

import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.*;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.*;

public class ObjectiveCardFactory {
    public PrivateObjectiveCard getPrivateObjectiveCard(int id) throws InputNotValidException{
        switch(id){
            case(0): return new ShadesOfBlue();
            case(1): return new ShadesOfGreen();
            case(2): return new ShadesOfRed();
            case(3): return new ShadesOfYellow();
            default: throw new InputNotValidException();
        }
    }

    public PublicObjectiveCard getPublicObjectiveCard(int id) throws InputNotValidException {
        switch(id){
            case(0): return new ColourDiagonals();
            case(1): return new ColourVariety();
            case(2): return new ColumnColourVariety();
            case(3): return new ColumnShadeVariety();
            case(4): return new DeepShades();
            case(5): return new LightShades();
            case(6): return new MediumShades();
            case(7): return new RowColourVariety();
            case(8): return new RowShadeVariety();
            case(9): return new ShadeVariety();
            default: throw new InputNotValidException();
        }
    }
}
