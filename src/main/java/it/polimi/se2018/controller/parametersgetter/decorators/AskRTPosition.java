package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.cli.View;

public class AskRTPosition extends PGDecorator {
    public AskRTPosition(ParameterGetter pg){
        super(pg, "Insert roundtrack position");
    }

    @Override
    public void getParameters(View view){
        pg.getParameters(view);
        view.getRoundTrackPosition(s);
    }
}
