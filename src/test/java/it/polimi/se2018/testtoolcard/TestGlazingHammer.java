package it.polimi.se2018.testtoolcard;

import it.polimi.se2018.controller.toolcard.GlazingHammer;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestGlazingHammer {
    private Model model;
    private PlayerMoveParameters param;
    private GlazingHammer card;
    private ArrayList<Die> beforeDP;
    private ArrayList<Die> expectedDP;
    private ArrayList<Die> emptyDP;

    @Before
    public void setUP(){
        beforeDP = new ArrayList<Die>();
        //

        expectedDP = new ArrayList<Die>();
        //

        emptyDP = new ArrayList<Die>();

        card = GlazingHammer.getInstance();
    }

    @Test
    public void test1(){
        //normale: stesso numero di colori
    }
}
