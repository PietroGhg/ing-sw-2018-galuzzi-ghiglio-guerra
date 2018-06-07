package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.cli.View;

public class AskIncrement extends PGDecorator {
    public AskIncrement(ParameterGetter pg){
        super(pg, "");
    }

    @Override
    public void getParameters(View view){
        pg.getParameters(view);
        view.getIncrement();
    }
}
