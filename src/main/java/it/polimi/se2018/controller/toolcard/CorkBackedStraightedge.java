package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public class CorkBackedStraightedge implements ToolCard{  //Riga in Sughero

    private static CorkBackedStraightedge instance;
    private CorkBackedStraightedge(){};
    public static CorkBackedStraightedge getInstance(){
        if (instance==null) instance = new CorkBackedStraightedge();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) {

    }
}
