package it.polimi.se2018.Model.Table;

import it.polimi.se2018.Model.Colour;
import it.polimi.se2018.Model.Die;

import java.util.ArrayList;
import java.util.Random;

public class DiceBag {
    private ArrayList<Die> bag;
    private final int DICETOTAL = 90;

    //instanzia i 90 dadi
    public DiceBag(){
        bag = new ArrayList<>();
        for(Colour c : Colour.values()){
            for(int i = 0; i < DICETOTAL/Colour.values().length; i++){
                bag.add(new Die(c));
            }
        }
    }

    public ArrayList<Die> extractDice(int nPlayers){
        ArrayList<Die> result = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < nPlayers + 1; i++){
            int index = rand.nextInt(bag.size());
            Die die = bag.get(index);
            die.roll();
            result.add(die);
            bag.remove(index);
        }
        return result;
    }
}
