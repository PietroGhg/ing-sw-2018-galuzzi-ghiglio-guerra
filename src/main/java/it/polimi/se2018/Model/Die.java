package it.polimi.se2018.Model;

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

    public Die(int v, Colour c){
        value = Integer.valueOf(v);
        colour = c;
    }

    public Die(Colour c){
        colour = c;
    }

    public Integer getDieValue() { return value; }
    public Colour getDieColour() { return colour; }

    public void extract(){
        Random rand = new Random();
        value = rand.nextInt(6) + 1; //generates a number between 0 and 5 and adds 1 [1;6]
    }
}
