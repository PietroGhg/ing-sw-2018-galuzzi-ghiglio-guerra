package it.polimi.se2018.model.table;

import it.polimi.se2018.model.wpc.WpcGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to extract the public and private objectives, and the boards the player will choose from
 * Singleton because it needs to keep track of what cards have already been extracted.
 * @author Pietro Ghiglio
 */
public class Extractor {
    private final int numPrCards = 5;
    private final int numPuCards = 11;
    private final int numWpcsExtracted = 4;
    private ArrayList<Integer> prCards;
    private ArrayList<Integer> puCards;
    private ArrayList<Integer> wpcs;
    private WpcGenerator gen;

    private Extractor instance;

    private Extractor(){
        prCards = new ArrayList<>(numPrCards);
        for(int i = 0; i < numPrCards; i++)  prCards.add(i);

        puCards = new ArrayList<>(numPuCards);
        for(int i = 0; i < numPuCards; i++) puCards.add(i);

        gen = new WpcGenerator();
        int numWpcs = gen.getNumWpcs();
        wpcs = new ArrayList<>(numWpcs);
        for(int i = 0; i < numWpcs; i++) wpcs.add(i);
    }

    public Extractor getInstance(){
        if (instance == null) return new Extractor();
        else return instance;
    }

    /**
     * extracts the IDs of the possible boards that a player will choose
     * @return the IDs
     */
    public int[] extractWpcs(){
        int[] ris = new int[numWpcsExtracted];
        Random random = new Random();
        for(int i = 0; i < numWpcsExtracted; i++){
            ris[i] = random.nextInt(wpcs.size());
        }
        return ris;
    }

}
