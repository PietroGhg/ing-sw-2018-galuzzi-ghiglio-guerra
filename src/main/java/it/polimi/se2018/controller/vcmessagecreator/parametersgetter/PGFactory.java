package it.polimi.se2018.controller.vcmessagecreator.parametersgetter;

import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.decorators.*;
import it.polimi.se2018.exceptions.InputNotValidException;

//*


public class PGFactory{
    private static final String GET_CELL_COORD = "Insert the coordinates of the recipient cell. ";
    private static final String GET_DIE_COORD = "Insert the coordinates of the Die to move. ";
    private ParameterGetter pg2;
    private ParameterGetter pg3;


    public ParameterGetter get(int id) throws InputNotValidException {
        ParameterGetter pg = new ParameterGetter();
        ParameterGetter pg4;
        ParameterGetter pg5;
        switch (id) {
            case(1):
                pg = new AskDPIndex(pg);
                pg2 = new AskIncrement(pg);
                pg3 = new AskCoordinates(pg2, GET_CELL_COORD);
                return pg3;
            case(2):
                return moveDie(pg);
            case(3):
                return moveDie(pg);
            case(4):
                pg = new ParameterGetter();
                pg2 = new AskCoordinates(pg, "Insert the coordinates of the first Die to move. ");
                pg3 = new AskCoordinates(pg2, "Insert the coordinates of the first recipient cell. ");
                pg4 = new AskCoordinates(pg3, "Insert the coordinates of the second Die to move. ");
                pg5 = new AskCoordinates(pg4, "Insert the coordinates of the second recipient cell. ");
                return pg5;
            case(5):
                pg = new AskDPIndex(pg);
                pg2 = new AskRTPosition(pg);
                return pg2;
            case(6):
                return pg;
            case(7):
                return pg;
            case(8):
                return dpThenDie(pg);
            case(9):
                return dpThenDie(pg);
            case(10):
                return dpThenDie(pg);
            case(11):
                return pg;
            case(12):
                pg = new AskCoordinates(pg, "Insert the RoundTrack position");
                pg2 = new AskCoordinates(pg, GET_DIE_COORD);
                pg3 = new AskCoordinates(pg2, GET_CELL_COORD);
                pg4 = new AskCoordinates2(pg3);
                return pg4;



            default: throw new InputNotValidException();
        }
    }

    private ParameterGetter moveDie(ParameterGetter pg){
        pg2 = new AskCoordinates(pg, GET_DIE_COORD);
        pg3 = new AskCoordinates(pg2, GET_CELL_COORD);
        return pg3;
    }

    private ParameterGetter dpThenDie(ParameterGetter pg){
        pg2 = new AskDPIndex(pg);
        pg3 = new AskCoordinates(pg2,GET_DIE_COORD);
        return pg3;
    }

}

