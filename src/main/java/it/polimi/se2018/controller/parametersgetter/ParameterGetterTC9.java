package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.view.cli.View;

public class ParameterGetterTC9 implements ParametersGetter {

    @Override
    public void getParameters(View view) {
        view.getDraftPoolIndex();
        view.getCoordinates("Insert the coordinates of the Die to move. ");
    }
}
