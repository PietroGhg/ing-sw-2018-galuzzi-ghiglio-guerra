package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;

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

    public void cardAction(Model model) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        PlayerMoveParameters param = model.getParameters();
        int playerID = param.getPlayerID();
        Player player = model.getPlayer(playerID);
        rc.checkEnoughFavorTokens(player, instance);

        WPC wpc = player.getWpc();

        int row = param.getParameter(0);
        int col = param.getParameter(1);
        int increment = param.getParameter(2);

        rc.checkNotEmpty(wpc,row,col);
        if (increment == +1) wpc.getCell(row, col).getDie().increase();
        if (increment == -1) wpc.getCell(row, col).getDie().decrease();


        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
