package it.polimi.se2018.Model.WPC;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;

public class Cell {
    private Die die;
    private Colour colourRestriction;
    private int valueRestriction;

    public void setColourR(Colour c){
        colourRestriction = c;
    }

    public void setValueR (int v) {
        valueRestriction = v;
    }
}
