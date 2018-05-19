package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.MoveNotAllowedException;

import java.util.Objects;
import java.util.Random;

public class Die {
    private Integer value;
    private Colour colour;

    public Die(int v, String c){
        value = Integer.valueOf(v);
        colour = Colour.valueOf(c);
    }

    public Die(){
        value = null;
        colour = null;
    }

    public Die(Die d) {
        try {
            value = new Integer(d.getDieValue());
        }
        catch(NullPointerException e){
            value = null;
        }
        try {
            colour = d.getDieColour();
        }
        catch(NullPointerException e){
            colour = null;
        }
    }

    public Die(int v, Colour c){
        value = Integer.valueOf(v);
        colour = c;
    }

    public Die(Colour c){
        colour = c;
    }

    public Integer getDieValue() { return value; }
    public Colour getDieColour() { return colour; }

    public void remove(){
        value = null;
        colour = null;
    }

    public void roll(){
        Random rand = new Random();
        value = rand.nextInt(6) + 1; //generates a number between 0 and 5 and adds 1 [1;6]
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Die die = (Die) o;
        return Objects.equals(value, die.value) &&
                colour == die.colour;
    }

    public void increase() throws MoveNotAllowedException {
        if (value < 6) value++;
        else throw new MoveNotAllowedException("Error: cannot increase value 6.");
    }

    public void decrease() throws MoveNotAllowedException {
        if(value > 1) value--;
        else throw new MoveNotAllowedException("Error: cannot decrease value 1.");
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, colour);
    }
}
