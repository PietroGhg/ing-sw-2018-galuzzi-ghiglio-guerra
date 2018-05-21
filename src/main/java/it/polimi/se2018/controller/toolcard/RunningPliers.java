package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

public class RunningPliers implements ToolCard{   //Tenaglia a Rotelle

    private static RunningPliers instance;
    private RunningPliers(){};
    public static RunningPliers getInstance(){
        if (instance==null) instance = new RunningPliers();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(Model model) {

    }
}
