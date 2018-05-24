package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for ToolCard CorkBackedStraightedge
 * @author Leonardo Guerra
 */

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
    public void cardAction(Model model) throws MoveNotAllowedException {
        RestrictionChecker rc = new RestrictionChecker();
        PlayerMoveParameters param = model.getParameters();
        int playerID = param.getPlayerID();
        Player player = model.getPlayer(playerID);
        rc.checkEnoughFavorTokens(player,instance);

        WPC wpc = player.getWpc();
        int dpIndex = param.getParameter(0);
        int cellRow = param.getParameter(1);
        int cellCol = param.getParameter(2);

        rc.checkDPCellNotEmpty(model.getDraftPool(),dpIndex);
        /*
        la cella di destinazione NON DEVE essere adiacente ad altre celle con dadi
        */
        //move the die from the DraftPool to the board
        Die temp = new Die(model.getDraftPool().get(dpIndex));
        //Restriction check, adjacency restriction not checked
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,temp);
        rc.checkValueRestriction(wpc,cellRow,cellCol,temp);
        rc.checkColourRestriction(wpc,cellRow,cellCol,temp);
        wpc.setDie(cellRow,cellCol,temp);
        model.getDraftPool().get(dpIndex).remove();

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
