package it.polimi.se2018.model;

import it.polimi.se2018.model.wpc.WpcGenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class used to extract the public and private objectives, and the boards the player will choose from.
 * Singleton because it needs to keep track of what cards have already been extracted.
 * @author Pietro Ghiglio
 */
public class Extractor {
    private final int numPrCards = 5;
    private final int numPuCards = 11;
    public static final int NUM_WPCS_EXTRACTED = 4;
    private ArrayList<Integer> prCards;
    private ArrayList<Integer> puCards;
    private ArrayList<Integer> wpcs;
    private int numWpcs;

    private static Extractor instance;

    private Extractor(){
        WpcGenerator gen = new WpcGenerator();
        prCards = new ArrayList<>(numPrCards);
        for(int i = 0; i < numPrCards; i++)  prCards.add(i);

        puCards = new ArrayList<>(numPuCards);
        for(int i = 0; i < numPuCards; i++) puCards.add(i);

        numWpcs = gen.getNumWpcs();
        wpcs = new ArrayList<>(numWpcs);
        for(int i = 0; i < numWpcs; i++) wpcs.add(i);
    }

    public static Extractor getInstance(){
        if (instance == null) instance = new Extractor();
        return instance;
    }

    /**
     * extracts the IDs of the possible boards that a player will choose
     * @return the IDs
     */
    public int[] extractWpcs(){
        int[] ris = new int[NUM_WPCS_EXTRACTED];
        Random random = new Random();
        for(int i = 0; i < NUM_WPCS_EXTRACTED; i++){
            //randomizes an index between 0  and wpcs.size()-1
            int j = random.nextInt(wpcs.size());
            //assegnates the value in the randomized index to the result
            ris[i] = wpcs.get(j);
            //removes the extracted value from the total of the boards
            wpcs.remove(j);
        }
        return ris;
    }

    /**
     * @return The number of wpcs from which the boards can be extracted
     */
    public int currNumWpcs(){ return wpcs.size(); }

    public int getNumWpcs() { return this.numWpcs; }

}
