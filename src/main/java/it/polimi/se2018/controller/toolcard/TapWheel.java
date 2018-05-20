package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public class TapWheel implements ToolCard{    //Taglierina Manuale

    private static TapWheel instance;
    private TapWheel(){};
    public static TapWheel getInstance(){
        if (instance==null) instance = new TapWheel();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) {

    }
}
