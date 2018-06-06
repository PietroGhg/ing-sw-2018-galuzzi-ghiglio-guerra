package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.controller.parametersgetter.decorators.*;
import it.polimi.se2018.exceptions.InputNotValidException;

//*


public class PGFactory{


    public ParameterGetter get(int id) throws InputNotValidException {
        ParameterGetter pg = new ParameterGetter();
        switch (id) {
            case(1):
                pg = new AskDPIndex(pg);
                pg = new AskIncrement(pg);
                pg = new AskCoordinates(pg, "Insert the coordinates of the recipient cell. ");
                return pg;
            case(2):
                return moveDie(pg);

            case(3):
                return moveDie(pg);
            case(4):
                pg = new ParameterGetter();
                pg = new AskCoordinates(pg, "Insert the coordinates of the first Die to move. ");
                pg = new AskCoordinates(pg, "Insert the coordinates of the first recipient cell. ");
                pg = new AskCoordinates(pg, "Insert the coordinates of the second Die to move. ");
                pg = new AskCoordinates(pg, "Insert the coordinates of the second recipient cell. ");
                return pg;
            case(5):
                pg = new AskDPIndex(pg);
                pg = new AskRTPosition(pg);
                return pg;
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
                pg = new AskCoordinates(pg, "Insert the coordinates of the Die to move. ");
                pg = new AskCoordinates(pg, "Insert the coordinates of the recipient cell. ");
                pg = new AskCoordinates2(pg);
                return pg;



            default: throw new InputNotValidException();
        }
    }

    private ParameterGetter moveDie(ParameterGetter pg){
        pg = new ParameterGetter();
        pg = new AskCoordinates(pg, "Insert the coordinates of the Die to move. ");
        pg = new AskCoordinates(pg, "Insert the coordinates of the recipient cell. ");
        return pg;
    }

    private ParameterGetter dpThenDie(ParameterGetter pg){
        pg = new AskDPIndex(pg);
        pg = new AskCoordinates(pg,"Insert the coordinates of the Die to move. ");
        return pg;
    }

    private ParameterGetter justDP(ParameterGetter pg){
        pg = new AskDPIndex(pg);
        return pg;
    }
}

