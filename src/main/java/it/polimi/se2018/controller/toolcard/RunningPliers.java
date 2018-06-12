package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

/**
 * Class for ToolCard RunningPliers
 * @author Leonardo Guerra
 */

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
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        //Checks if the current is the first turn of the player

        if(param.turnNumber(param.getPlayer().getPlayerID())==2)
        { throw new MoveNotAllowedException("Error: this card can be played in the first turn only."); }

        /* TODO: far saltare il secondo turno */

        int dpIndex = param.getParameter(0);
        int cellRow = param.getParameter(1);
        int cellCol = param.getParameter(2);

        ArrayList<Die> dp = param.getDraftPool();
        rc.checkDPCellNotEmpty(dp,dpIndex);
        Die temp = new Die(dp.get(dpIndex));
        WPC wpc = player.getWpc();

        //Restrictions check
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkValueRestriction(wpc,cellRow,cellCol,temp);
        rc.checkColourRestriction(wpc,cellRow,cellCol,temp);
        rc.checkAdjacent(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,temp);

        wpc.setDie(cellRow,cellCol,temp);
        dp.remove(dpIndex);

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
