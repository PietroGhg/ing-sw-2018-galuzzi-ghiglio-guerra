package it.polimi.se2018.controller.vcmessagecreator.parametersgetter.decorators;

import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.ViewInterface;

public class AskRTPosition extends PGDecorator {
    public AskRTPosition(ParameterGetter pg){
        super(pg, "Insert roundtrack position");
    }

    @Override
    public void getParameters(ViewInterface view){
        pg.getParameters(view);
        view.getRoundTrackPosition(s);
    }
}
