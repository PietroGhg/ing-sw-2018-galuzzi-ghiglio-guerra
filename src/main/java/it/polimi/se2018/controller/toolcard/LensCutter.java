package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public class LensCutter implements ToolCard{  //Taglierina Circolare

    private static LensCutter instance;
    private LensCutter(){};
    public static LensCutter getInstance(){
        if (instance==null) instance = new LensCutter();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) {

    }
}
