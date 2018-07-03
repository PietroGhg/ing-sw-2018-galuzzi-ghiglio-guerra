package it.polimi.se2018.controller.vcmessagecreator.parametersgetter.decorators;

import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.ViewInterface;

/**
 * Class which extends the parameter getter decorator for the coordinates request
 */
public class AskCoordinates extends PGDecorator {
    public AskCoordinates(ParameterGetter pg, String s){
        super(pg, s);
    }

    @Override
    public void getParameters(ViewInterface view){
        pg.getParameters(view);
        view.getCoordinates(s);
    }
}
