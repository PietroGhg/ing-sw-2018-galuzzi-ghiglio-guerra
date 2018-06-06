package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.List;
import java.util.Random;

/**
 * Class for ToolCard FluxBrush
 * @author Leonardo Guerra
 */

public class FluxBrush implements ToolCard{   //Pennello per Pasta Salda

    private static FluxBrush instance;
    private FluxBrush(){};
    public static FluxBrush getInstance(){
        if (instance==null) instance = new FluxBrush();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);
        WPC wpc = player.getWpc();

        int dpIndex = param.getParameter(0);

        //Generation of a random value

        Die temp = param.getDraftPool().get(dpIndex);
        temp.roll();
        param.getDraftPool().remove(dpIndex);

        List<int[]> validCoordinates = wpc.isPlaceable(temp);





        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1) {
            favorTokensNeeded = 2;
        }

    }
}
