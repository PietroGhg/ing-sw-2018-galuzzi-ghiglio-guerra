package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for ToolCard CorkBackedStraightedge
 * @author Leonardo Guerra
 */

public class CorkBackedStraightedge extends ToolCard{  //Riga in Sughero

    private static CorkBackedStraightedge instance;
    private CorkBackedStraightedge(){};
    public static CorkBackedStraightedge getInstance(){
        if (instance==null) instance = new CorkBackedStraightedge();
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
        int dpIndex = param.getParameter(0);
        int cellRow = param.getParameter(1);
        int cellCol = param.getParameter(2);

        rc.checkDPCellNotEmpty(param.getDraftPool(),dpIndex);

        //Move the die from the DraftPool to the board
        Die temp = new Die(param.getDraftPool().get(dpIndex));
        //Restrictions check, adjacent restriction not checked
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,temp);
        rc.checkValueRestriction(wpc,cellRow,cellCol,temp);
        rc.checkColourRestriction(wpc,cellRow,cellCol,temp);

        //Restriction check for the chosen die placed in a cell not adjacent to other dice
        rc.checkNotAdjacent(wpc,cellRow,cellCol);

        wpc.setDie(cellRow,cellCol,temp);
        param.getDraftPool().get(dpIndex).remove();

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
