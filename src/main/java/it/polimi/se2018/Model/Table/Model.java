package it.polimi.se2018.Model.Table;

import it.polimi.se2018.Model.Die;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.Turn;

import java.util.ArrayList;

public class Model implements Observable { //Singleton?
    private ArrayList<Die> draftPool;
    private ArrayList<Player> players;
    private int roundMatrix[][];
    private DiceBag diceBag;
    private Turn turn;
    private RoundTrack roundTrack;
    // metodi vari ed eventuali
}
