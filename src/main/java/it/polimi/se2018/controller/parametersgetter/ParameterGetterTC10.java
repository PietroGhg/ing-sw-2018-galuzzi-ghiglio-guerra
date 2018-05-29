package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.view.cli.View;

public class ParameterGetterTC10 implements ParametersGetter {

    @Override
    public void getParameters(View view) { //??

        view.getDraftPoolIndex("Insert the DraftPool index. ");
        view.getCoordinates("Insert the coordinates of the Die to move. ");
    }
}
