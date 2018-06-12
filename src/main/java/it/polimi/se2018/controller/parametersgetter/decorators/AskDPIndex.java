package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;
import it.polimi.se2018.view.ViewInterface;
import it.polimi.se2018.view.cli.View;

public class AskDPIndex extends PGDecorator {
    public AskDPIndex(ParameterGetter pg){
        super(pg, "Insert Draft Pool index.");
    }

    @Override
    public void getParameters(ViewInterface view){
        pg.getParameters(view);
        view.getDraftPoolIndex();
    }
}
