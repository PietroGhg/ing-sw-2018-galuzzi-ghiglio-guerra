package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.view.cli.View;

public class ParameterGetterTC5 implements ParametersGetter {

    @Override
    public void getParameters(View view) {
        view.getDraftPoolIndex("Insert the DraftPool index. ");
        view.getRoundTrackPosition("Insert the RoundTrack position");

    }
}
