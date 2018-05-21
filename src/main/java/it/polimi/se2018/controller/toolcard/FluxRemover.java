package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

public class FluxRemover implements ToolCard{ //Diluente per Pasta Salda

    private static FluxRemover instance;
    private FluxRemover(){};
    public static FluxRemover getInstance(){
        if (instance==null) instance = new FluxRemover();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(Model model) {

    }
}
