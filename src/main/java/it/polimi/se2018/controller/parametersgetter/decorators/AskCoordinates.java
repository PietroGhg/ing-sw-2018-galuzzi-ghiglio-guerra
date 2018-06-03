package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.cli.View;

public class AskCoordinates extends PGDecorator {
    public AskCoordinates(ParameterGetter pg, String s){
        super(pg, s);
    }
    public void getParameters(View view){
        pg.getParameters(view);
        view.getCoordinates(s);
    }
}