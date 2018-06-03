package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

/**
 * Class for ToolCard GrozingPliers
 * @author Leonardo Guerra
 */

public class GrozingPliers implements ToolCard{   //Pinza Sgrossatrice

    private static GrozingPliers instance;
    private GrozingPliers(){};
    public static GrozingPliers getInstance(){
        if (instance==null) instance = new GrozingPliers();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player, instance);

        WPC wpc = player.getWpc();
        ArrayList<Die> dp = param.getDraftPool();

        int dpIndex = param.getParameter(0);
        int increment = param.getParameter(1);
        int row = param.getParameter(2);
        int col = param.getParameter(3);

        rc.checkDPCellNotEmpty(dp,dpIndex);
        Die d = new Die(dp.get(dpIndex));

        if (increment == +1) d.increase();
        if (increment == -1) d.decrease();

        rc.checkEmptiness(wpc,row,col);
        rc.checkFirstMove(wpc,row,col);
        rc.checkAdjacent(wpc,row,col);
        rc.checkValueRestriction(wpc,row,col,d);
        rc.checkColourRestriction(wpc,row,col,d);
        rc.checkSameDie(wpc,row,col,d);

        wpc.setDie(row,col,d);

        player.setWpcOnly(wpc);
        dp.remove(dpIndex);

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
