package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public class GlazingHammer implements ToolCard{//Martelletto

    private static GlazingHammer instance;
    private GlazingHammer(){};
    public static GlazingHammer getInstance(){
        if (instance==null) instance = new GlazingHammer();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{

    }
}
