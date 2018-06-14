package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.ViewInterface;

public class AskIncrement extends PGDecorator {
    public AskIncrement(ParameterGetter pg){
        super(pg, "");
    }

    @Override
    public void getParameters(ViewInterface view){
        pg.getParameters(view);
        view.getIncrement();
    }
}
