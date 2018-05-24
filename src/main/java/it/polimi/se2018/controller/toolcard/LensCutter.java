package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

import java.util.ArrayList;

/**
 * Class for ToolCard LensCutter
 * @author Leonardo Guerra
 */

public class LensCutter implements ToolCard{  //Taglierina Circolare

    private static LensCutter instance;
    private LensCutter(){};
    public static LensCutter getInstance(){
        if (instance==null) instance = new LensCutter();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        ArrayList<ArrayList<Die>> rt = param.getRoundTrack();
        ArrayList<Die> dp = param.getDraftPool();

        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        int dpIndex = param.getParameter(0);
        int rtNumber = param.getParameter(1);
        int rtIndex = param.getParameter(2);

        rc.checkRTCellNotEmpty(rt,rtNumber,rtIndex);
        rc.checkDPCellNotEmpty(dp,rtIndex);

        //Swap the chosen die on the draft pool with another chosen die on the round track
        Die temp = new Die(param.getDraftPool().get(dpIndex));
        dp.set(dpIndex, rt.get(rtNumber).get(rtIndex));
        rt.get(rtNumber).set(rtIndex,temp);

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
