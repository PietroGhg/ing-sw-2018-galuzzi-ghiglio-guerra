package it.polimi.se2018.model;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.utils.Printer;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

/**
 * Class for the die
 */
public class Die implements Serializable {
    private Integer value;
    private Colour colour;

    public Die(int v, String c){
        value = v;
        colour = Colour.valueOf(c);
    }

    public Die(){
        value = null;
        colour = null;
    }

    public Die(Die d) {
        try {
            value = d.getDieValue();
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
        value = v;
        colour = c;
    }

    public Die(Colour c){
        colour = c;
    }

    public Integer getDieValue() { return value; }

    public void setDieValue(Integer newValue) { this.value = newValue; }

    public void setOppositeDieValue(){
        value = 7 - value;
    }

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

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        String num = prettyDie(value);
        builder.append(Colour.RESET);
        builder.append(colour.escape());
        builder.append(num);
        builder.append(Colour.RESET);
        return builder.toString();
    }

    public static String prettyDie(int n){
        if(Printer.isjAnsiActive()) return String.valueOf(n);
        switch (n){
            case(1): return "\u2680";
            case(2): return "\u2681";
            case(3): return "\u2682";
            case(4): return "\u2683";
            case(5): return "\u2684";
            case(6): return "\u2685";
            default: return "Error";
        }
    }
}
