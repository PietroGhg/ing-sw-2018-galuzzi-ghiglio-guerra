package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.view.cli.View;

public class ParameterGetterTC4 implements ParametersGetter {

    @Override
    public void getParameters(View view) {
        view.getCoordinates("Insert the coordinates of the first Die to move. ");
        view.getCoordinates("Insert the coordinates of the first recipient cell. ");
        view.getCoordinates("Insert the coordinates of the second Die to move. ");
        view.getCoordinates("Insert the coordinates of the second recipient cell. ");
    }
}
