package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

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
    public void cardAction(Model model) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        PlayerMoveParameters param = model.getParameters();
        int playerID = param.getPlayerID();
        Player player = model.getPlayer(playerID);
        rc.checkEnoughFavorTokens(player,instance);

        int dpIndex = param.getParameter(0);

        //Generation of a random value
        Random r;
        int tot=6;
        r = new Random();
        Integer randomValue = r.nextInt(tot)+1;

        Die temp = model.getDraftPool().get(dpIndex);
        temp.setDieValue(randomValue);
        model.getDraftPool().get(dpIndex).remove();

        // check if it can be placed on the board
        /*
        //...se non si può piazzare:
        model.getDraftPool().add(temp);

        //...se si può piazzare, dopo aver chiesto i nuovi parametri:
        int cellRow = param.getParameter(1?);
        int cellCol = param.getParameter(2?);

        WPC wpc = player.getWpc();
        //Check all the restrictions
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkAdjacent(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol);
        rc.checkValueRestriction(wpc,cellRow,cellCol);
        rc.checkColourRestriction(wpc,cellRow,cellCol);

        wpc.setDie(cellRow,cellCol,temp);
        */

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1) {
            favorTokensNeeded = 2;
        }

    }
}
