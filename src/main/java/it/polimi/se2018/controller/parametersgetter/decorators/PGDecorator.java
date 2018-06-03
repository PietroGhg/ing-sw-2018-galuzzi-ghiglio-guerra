package it.polimi.se2018.controller.parametersgetter.decorators;

import it.polimi.se2018.controller.parametersgetter.ParameterGetter;

public class PGDecorator extends ParameterGetter {
    protected String s;
    protected ParameterGetter pg;

    public PGDecorator(ParameterGetter pg, String s){
        this.pg = pg;
        this.s = s;
    }
}
