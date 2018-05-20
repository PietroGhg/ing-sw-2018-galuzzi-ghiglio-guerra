package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public class GrindingStone implements ToolCard{   //Tampone Diamantato

    private static GrindingStone instance;
    private GrindingStone(){};
    public static GrindingStone getInstance(){
        if (instance==null) instance = new GrindingStone();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException {

    }
}
