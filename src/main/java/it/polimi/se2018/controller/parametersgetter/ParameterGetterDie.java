package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.view.cli.View;

public class ParameterGetterDie extends ParameterGetter {

    @Override
    public void getParameters(View view) {
        view.getDraftPoolIndex();
        view.getCoordinates("Insert coordinates of the cell");
    }
}
