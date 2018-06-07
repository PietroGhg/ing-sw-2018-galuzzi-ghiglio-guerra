package it.polimi.se2018.model.table;

import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class DiceBag implements Serializable{
    private ArrayList<Die> bag;
    private final int DICETOTAL = 90;
    private static DiceBag instance;

    //instanzia i 90 dadi
    private DiceBag(){
        bag = new ArrayList<>();
        for(Colour c : Colour.values()){
            for(int i = 0; i < DICETOTAL/Colour.values().length; i++){
                bag.add(new Die(0,c));
            }
        }
    }

    public ArrayList<Die> getDiceBag() { return bag; }

    public void setDiceBag(ArrayList<Die> bag) { this.bag = bag; }

    public static DiceBag getInstance() {
        if(instance == null) instance = new DiceBag();
        return instance;
    }

    public ArrayList<Die> extractDice(int nPlayers){
        ArrayList<Die> result = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < 2*nPlayers + 1; i++){
            int index = rand.nextInt(bag.size());
            Die die = bag.get(index);
            die.roll();
            result.add(die);
            bag.remove(index);
        }
        return result;
    }

    public int getRandomIndex(){
        Random random = new Random();
        return random.nextInt(bag.size());
    }

    public Die getDie(int index){ return bag.get(index); }

    public void removeDie(int index){
        bag.remove(index);
    }

    public static void resetInstance(){
        instance = new DiceBag();
    }

    /**
     * Method needed to allow correct deserialization of a Singleton
     * @return the instance
     */
    public Object readResolve() throws ObjectStreamException{
        return getInstance();
    }
}
