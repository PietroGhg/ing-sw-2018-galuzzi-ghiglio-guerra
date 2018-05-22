package it.polimi.se2018.model.table;

import it.polimi.se2018.model.wpc.WpcGenerator;

import java.util.ArrayList;

public class Extractor {
    private final int numPrCards = 5;
    private final int numPuCards = 11;
    private static ArrayList<Integer> prCards;
    private static ArrayList<Integer> puCards;
    private static ArrayList<Integer> wpcs;
    private WpcGenerator gen;

    public Extractor(){
        prCards = new ArrayList<>(numPrCards);
        puCards = new ArrayList<>(numPuCards);
        gen = new WpcGenerator();
        wpcs = new ArrayList<>(gen.getNumWpcs());
    }

}
