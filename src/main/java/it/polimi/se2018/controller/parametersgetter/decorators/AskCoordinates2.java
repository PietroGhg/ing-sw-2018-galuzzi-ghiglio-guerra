package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.cli.View;

public class AskCoordinates2 extends PGDecorator {
    public AskCoordinates2(ParameterGetter pg){
        super(pg, "");
    }

    public void getParameters(View view){
        pg.getParameters(view);
        view.getCoordinates2();
    }
}
