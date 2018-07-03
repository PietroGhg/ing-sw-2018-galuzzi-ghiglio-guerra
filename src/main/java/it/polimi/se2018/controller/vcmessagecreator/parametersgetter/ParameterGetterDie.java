package it.polimi.se2018.controller.vcmessagecreator.parametersgetter;

import it.polimi.se2018.view.ViewInterface;

/**
 * Class for the parameter getter for the die
 */
public class ParameterGetterDie extends ParameterGetter {

    @Override
    public void getParameters(ViewInterface view) {
        view.getDraftPoolIndex();
        view.getCoordinates("Insert coordinates of the cell");
    }
}
