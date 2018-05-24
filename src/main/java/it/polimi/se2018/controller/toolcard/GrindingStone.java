package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

/**
 * Class for ToolCard GrindingStone
 * @author Leonardo Guerra
 */

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
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        WPC wpc = player.getWpc();
        ArrayList<Die> dp = param.getDraftPool();
        int dpIndex = param.getParameter(0);
        int cellRow = param.getParameter(1);
        int cellCol = param.getParameter(2);
        rc.checkDPCellNotEmpty(dp,dpIndex);

        //Flip the die to its opposite side
        dp.get(dpIndex).setOppositeDieValue();

        /*
        Non è richiesto, ma è sottinteso che poi il dado vada piazzato:
        */
        //move the die from the DraftPool to the board
        Die temp = new Die(param.getDraftPool().get(dpIndex));
        //Restriction check, adjacency restriction not checked
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,temp);
        rc.checkAdjacent(wpc,cellRow,cellCol);
        rc.checkValueRestriction(wpc,cellRow,cellCol,temp);
        rc.checkColourRestriction(wpc,cellRow,cellCol,temp);
        wpc.setDie(cellRow,cellCol,temp);
        dp.get(dpIndex).remove();

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
