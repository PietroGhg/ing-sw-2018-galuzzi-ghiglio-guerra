package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;

import java.util.ArrayList;

/**
 * Class for ToolCard FluxRemover
 * @author Leonardo Guerra
 */

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
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        int dpIndex = param.getParameter(0);
        ArrayList<Die> dp = param.getDraftPool();
        Die temp = new Die(dp.get(dpIndex));
        temp.setDieValue(null);
        dp.get(dpIndex).remove();

        /*
        Rimettere il dado nella DraftPool, pescarne un altro,
        scegliere il valore, scegliere se piazzarlo o rimetterlo nella DraftPool
        */

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
