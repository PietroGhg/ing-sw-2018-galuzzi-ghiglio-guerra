package it.polimi.se2018.controller.vcmessagecreator.parametersgetter;

import it.polimi.se2018.view.ViewInterface;

public class ParameterGetterDie extends ParameterGetter {

    @Override
    public void getParameters(ViewInterface view) {
        view.getDraftPoolIndex();
        view.getCoordinates("Insert coordinates of the cell");
    }
}
