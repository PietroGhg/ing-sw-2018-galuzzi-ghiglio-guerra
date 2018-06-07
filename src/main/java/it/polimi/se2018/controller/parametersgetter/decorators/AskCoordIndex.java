package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.cli.View;

public class AskCoordIndex extends PGDecorator {
    public AskCoordIndex(ParameterGetter pg){
        super(pg, "");
    }

    @Override
    public void getParameters(View view){
        view.getCoordIndex();
    }
}
