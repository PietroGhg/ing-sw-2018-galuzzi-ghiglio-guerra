package it.polimi.se2018.controller.parametersgetter;

import it.polimi.se2018.exceptions.InputNotValidException;

public class PGFactory{


    public ParametersGetter get(int id) throws InputNotValidException {
        switch (id) {
            case(1): return new ParameterGetterTC1();
            case(2): return new ParameterGetterTC2();
            case(3): return new ParameterGetterTC3();
            case(4): return new ParameterGetterTC4();
            case(5): return new ParameterGetterTC5();
            case(6): return new ParameterGetterTC6();
            case(7): return new ParameterGetterTC7();
            case(8): return new ParameterGetterTC8();
            case(9): return new ParameterGetterTC9();
            case(10): return new ParameterGetterTC10();
            case(11): return new ParameterGetterTC11();
            case(12): return new ParameterGetterTC12();
            //case(): return new ParameterGetterTC6pt2();
            //case(): return new ParameterGetterTC11pt2();



            // throw new InputNotValidException();
        }
        return null;
    }
}

