package it.polimi.se2018.Model.WPC;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;

public class Cell {
    private Die die;
    private Colour colourRestriction;
    private int valueRestriction;

    public Cell() {
        die = new Die();
    }

    public void setDie(Die d) { this.die = d;}
    public Die getDie() { return die; }

    public void setColourR(Colour c){
        this.colourRestriction = c;
    }
    public Colour getColourR() { return colourRestriction; }

    public void setValueR(int v) {
        this.valueRestriction = v;
    }
    public int  getValueR() { return valueRestriction; }
}
