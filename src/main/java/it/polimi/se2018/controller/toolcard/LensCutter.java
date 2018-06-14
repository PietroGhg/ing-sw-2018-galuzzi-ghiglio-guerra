package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import java.util.List;

/**
 * Class for ToolCard LensCutter
 * @author Leonardo Guerra
 */

public class LensCutter extends ToolCard{  //Taglierina Circolare

    private static LensCutter instance;
    private LensCutter(){
        super("LensCutter");
    }
    public static LensCutter getInstance(){
        if (instance==null) instance = new LensCutter();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        List<List<Die>> rt = param.getRoundTrack();
        List<Die> dp = param.getDraftPool();

        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        int dpIndex = param.getParameter(0);
        int rtNumber = param.getParameter(1);
        int rtIndex = param.getParameter(2);

        rc.checkRTCellNotEmpty(rt,rtNumber,rtIndex);
        rc.checkDPCellNotEmpty(dp,dpIndex);

        //Swap the chosen die on the draft pool with another chosen die on the round track
        Die temp = new Die(param.getDraftPool().get(dpIndex));
        dp.set(dpIndex, rt.get(rtNumber).get(rtIndex));
        rt.get(rtNumber).set(rtIndex,temp);

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
