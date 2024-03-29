package it.polimi.se2018.model;

import it.polimi.se2018.controller.toolcard.FluxBrush;
import it.polimi.se2018.controller.toolcard.GrozingPliers;
import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.model.objectivecards.ObjectiveCardFactory;
import it.polimi.se2018.model.objectivecards.privateobjectivecard.PrivateObjectiveCard;
import it.polimi.se2018.model.objectivecards.publicobjectivecard.PublicObjectiveCard;
import it.polimi.se2018.model.wpc.WpcGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Class used to extract the public and private objectives, and the boards the player will choose from.
 * Singleton because it needs to keep track of what cards have already been extracted.
 * @author Pietro Ghiglio
 */
public class Extractor {
    public static final int NUM_PR_CARDS = 5;
    private static final int NUM_PU_CARDS = 10;
    private static final int NUM_PUCARDS_EXTRACTED = 3;
    public static final int NUM_WPCS_EXTRACTED = 4;
    private static final int NUM_TOOLCARDS_EXTRACTED=3;
    private ArrayList<Integer> prCards;
    private ArrayList<Integer> puCards;
    private ArrayList<Integer> wpcs;
    private List<String> toolCards;
    private int numWpcs;
    private static Extractor instance;

    private Extractor(){
        List<String> staticToolCards = Arrays.asList("CopperFoilBurnisher", "CorkBackedStraightedge", "EglomiseBrush",
                "FluxBrush", "FluxRemover", "GlazingHammer", "GrindingStone", "GrozingPliers", "Lathekin", "LensCutter", "RunningPliers",
                "TapWheel");
        WpcGenerator gen = WpcGenerator.getInstance();
        prCards = new ArrayList<>(NUM_PR_CARDS);
        for(int i = 0; i < NUM_PR_CARDS; i++)  prCards.add(i);

        puCards = new ArrayList<>(NUM_PU_CARDS);
        for(int i = 0; i < NUM_PU_CARDS; i++) puCards.add(i);

        numWpcs = gen.getNumWpcs();
        wpcs = new ArrayList<>(numWpcs);

        toolCards = new ArrayList<>();
        toolCards.addAll(staticToolCards);


        //the boards are stored in files whose names are 1.xml ... 24.xml,
        for(int i = 1; i <= numWpcs; i++) wpcs.add(i);
    }

    public static Extractor getInstance(){
        if (instance == null) instance = new Extractor();
        return instance;
    }

    /**
     * Extracts the IDs of the possible boards that a player will choose
     * @return the IDs
     */
    public int[] extractWpcs(){
        int[] ris = new int[NUM_WPCS_EXTRACTED];
        Random random = new Random();
        for(int i = 0; i < NUM_WPCS_EXTRACTED; i++){
            //randomizes an index between 0 and wpcs.size() - 1
            int randomIndex = random.nextInt(wpcs.size()) ;
            //sets the value in the randomized index to the result
            ris[i] = wpcs.get(randomIndex);
            //removes the extracted value from the total of the boards
            wpcs.remove(randomIndex);
        }

        return ris;
    }

    public int extractOneWpc(){
        Random random = new Random();
        int randIndex = random.nextInt(wpcs.size());
        int ris = wpcs.get(randIndex);
        wpcs.remove(randIndex);
        return ris;
    }

    /**
     * Extracts a PrivateObjectiveCard, sets it into the corresponding player
     * @param p the player
     * @return the name of the PrivateObjectiveCard
     */
    public String extractPrCard(Player p){
        Random random = new Random();
        ObjectiveCardFactory objectiveCardFactory = new ObjectiveCardFactory();
        PrivateObjectiveCard card;
        int randNum = random.nextInt(prCards.size());
        try {
            int cardID = prCards.get(randNum);
            prCards.remove(randNum);
            card = objectiveCardFactory.getPrivateObjectiveCard(cardID);
            p.addPrCard(card);
            return card.getName();
        }
        catch(InputNotValidException e){
            //Since randNum is a value between 0 and 4, this exception should never be thrown
            return "Error";
        }
    }

    public List<PublicObjectiveCard> extractPuCards(){
        Random random = new Random();
        ObjectiveCardFactory objectiveCardFactory = new ObjectiveCardFactory();
        ArrayList<PublicObjectiveCard> ris = new ArrayList<>();

        try {
            for(int i = 0; i < NUM_PUCARDS_EXTRACTED; i++){
                int randNum = random.nextInt(puCards.size());
                int cardID = puCards.get(randNum);
                puCards.remove(randNum);
                PublicObjectiveCard card = objectiveCardFactory.getPublicObjectiveCard(cardID);
                ris.add(card);
            }
        }
        catch(InputNotValidException e){
            return new ArrayList<>();
        }

        return ris;
    }

    public List<String> extractToolCards(){
        List<String> ris = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<NUM_TOOLCARDS_EXTRACTED; i++) {
            int randomIndex = random.nextInt(toolCards.size());
            ris.add(  toolCards.get(randomIndex) );
            toolCards.remove(randomIndex);
        }
        return ris;


    }


    /**
     * @return The number of wpcs from which the boards can be extracted
     */
    public int currNumWpcs(){ return wpcs.size(); }

    public int getNumWpcs() { return this.numWpcs; }

    public int getNumPuCards() { return prCards.size(); }

    /**
     * Method used in order to avoid conflicts will running all the tests
     */
    public static void resetInstance() { instance = new Extractor(); }

}
