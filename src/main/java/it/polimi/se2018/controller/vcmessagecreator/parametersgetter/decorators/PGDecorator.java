package it.polimi.se2018.controller.vcmessagecreator.parametersgetter.decorators;

import it.polimi.se2018.controller.vcmessagecreator.parametersgetter.ParameterGetter;

/**
 * PGDecorator implements a design pattern in order to handle the parameters request;
 * in PGFactory a ParameterGetter is decorated according to the toolcard; the PGDecorators contain
 * a specific method for the specific decoration, but, before doing that, call the
 * method getParameters of the wrapped ParameterGetters
 */
public class PGDecorator extends ParameterGetter {
    protected String s;
    /*package-private*/ ParameterGetter pg;

    /*package-private*/ PGDecorator(ParameterGetter pg, String s){
        this.pg = pg;
        this.s = s;
    }
}
