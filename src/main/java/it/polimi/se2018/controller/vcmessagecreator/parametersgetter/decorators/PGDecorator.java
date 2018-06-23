package it.polimi.se2018.controller.vcmessagecreator.parametersgetter.decorators;

import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.ParameterGetter;

public class PGDecorator extends ParameterGetter {
    protected String s;
    /*package-private*/ ParameterGetter pg;

    /*package-private*/ PGDecorator(ParameterGetter pg, String s){
        this.pg = pg;
        this.s = s;
    }
}
