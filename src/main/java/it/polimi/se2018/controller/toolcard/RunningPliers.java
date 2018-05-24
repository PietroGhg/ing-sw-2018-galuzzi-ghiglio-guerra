package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
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
    public void cardAction(Model model) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        PlayerMoveParameters param = model.getParameters();
        int playerID = param.getPlayerID();
        Player player = model.getPlayer(playerID);
        rc.checkEnoughFavorTokens(player,instance);

        /*
        Check che sia appena finito il primo turno
        Far saltare il secondo turno ("1" nella matrice dei turni?)
        */

        int dpIndex = param.getParameter(0);
        int cellRow = param.getParameter(1);
        int cellCol = param.getParameter(2);

        ArrayList<Die> dp = model.getDraftPool();
        rc.checkDPCellNotEmpty(dp,dpIndex);
        Die temp = new Die(dp.get(dpIndex));
        WPC wpc = player.getWpc();

        //Check restrictions
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkAdjacent(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,temp);
        rc.checkValueRestriction(wpc,cellRow,cellCol,temp);
        rc.checkColourRestriction(wpc,cellRow,cellCol,temp);
        wpc.setDie(cellRow,cellCol,temp);
        dp.get(dpIndex).remove();

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
