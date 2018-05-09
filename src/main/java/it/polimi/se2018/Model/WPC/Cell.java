package it.polimi.se2018.Model.WPC;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;

import java.util.Objects;

public class Cell {
    private Die die;
    private Colour colourRestriction;
    private Integer valueRestriction;

    public Cell() {
        die = new Die();
    }

    public void setDie(Die d) { this.die = d;}
    public Die getDie() { return die; }

    public void setColourR(Colour c){
        this.colourRestriction = c;
    }
    public Colour getColourR() { return colourRestriction; }

    public void setValueR(Integer v) { this.valueRestriction = v; }

    public Integer  getValueR() { return valueRestriction; }

    public boolean isEmpty() {
        return die.getDieValue() == null && die.getDieColour() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return Objects.equals(getDie(), cell.getDie()) &&
                colourRestriction == cell.colourRestriction &&
                Objects.equals(valueRestriction, cell.valueRestriction);
    }

    @Override
    public int hashCode() {

        return Objects.hash(getDie(), colourRestriction, valueRestriction);
    }
}
