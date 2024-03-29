package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.List;

/**
 * Class for ToolCard FluxRemover
 * @author Leonardo Guerra
 */

public class FluxRemover extends ToolCard{ //Diluente per Pasta Salda

    private static FluxRemover instance;
    private FluxRemover(){
        super("FluxRemover");
    }
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

        // 0: dpIndex, 1: dbIndex, 2: dieValue
        // eventually, 3: RowCell, 4: ColCell
        int dpIndex = param.getParameter(0);
        int dbIndex = param.getParameter(1);
        int dieValue = param.getParameter(2);

        List<Die> dp = param.getDraftPool();
        List<Die> db = param.getDiceBag();
        Die temp = new Die(dp.get(dpIndex));
        dp.remove(dpIndex);

        db.add(temp);

        Die newDie = db.get(dbIndex);
        newDie.setDieValue(dieValue);
        db.remove(dbIndex);

        if(param.getParameters().size()==5){
            int rowCell = param.getParameter(3);
            int colCell = param.getParameter(4);
            WPC tempWpc = new WPC(player.getWpc());

            //restriction already checked
            tempWpc.setDie(rowCell,colCell,newDie);
            player.setWpc(tempWpc);
        }
        else {
            dp.add(newDie);
        }

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
