package it.polimi.se2018;

import it.polimi.se2018.model.Extractor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * Tests for the extractor class
 * @author Pietro Ghiglio
 */
public class TestExtractor {

    /**
     * Tests that two Extractor objects have the same reference (Extractor is a singleton)
     */
    @Test
    public void test1(){
        Extractor extractor1 = Extractor.getInstance();
        Extractor extractor2 = Extractor.getInstance();

        assertSame(extractor1, extractor2);
    }

    /**
     * Testes the extractsWpcs extracts the correct amunt of boards, and that changes in the static attributes
     * are correctly propogated in all the Extractor objects
     */
    @Test
    public void test2(){
        Extractor extractor1 = Extractor.getInstance();
        int[] ris = extractor1.extractWpcs();
        assertEquals(Extractor.NUM_WPCS_EXTRACTED, ris.length);

        //Checks that the number of boards that can currently be extracted equals the total number of boards
        //minus the number of extracted boards
        Extractor extractor2 = Extractor.getInstance();
        assertEquals(extractor2.getNumWpcs() - Extractor.NUM_WPCS_EXTRACTED, extractor2.currNumWpcs());
    }
}
