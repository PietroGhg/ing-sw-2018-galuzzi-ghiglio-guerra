package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.view.cli.View;

import java.util.Scanner;

public class ParameterGetterTC12 implements ParametersGetter {

    @Override
    public void getParameters(View view) {
        view.getRoundTrackPosition("Insert the RoundTrack position");
        view.getCoordinates("Insert the coordinates of the Die to move. ");
        view.getCoordinates("Insert the coordinates of the recipient cell. ");
        view.getCoordinates2("Insert the coordinates of the Die to move. ");

        }
    }

