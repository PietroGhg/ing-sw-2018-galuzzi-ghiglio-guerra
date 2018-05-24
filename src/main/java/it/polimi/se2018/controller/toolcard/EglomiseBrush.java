package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for ToolCard EglomiseBrush
 * @author Leonardo Guerra
 */

public class EglomiseBrush implements ToolCard{   //Pennello per Eglomise

    private static EglomiseBrush instance;
    private EglomiseBrush(){};
    public static EglomiseBrush getInstance(){
        if (instance==null) instance = new EglomiseBrush();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException {
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        WPC wpc = player.getWpc();

        //Die to move
        int row1 = param.getParameter(0);
        int col1 = param.getParameter(1);
        //Recipient cell
        int row2 = param.getParameter(2);
        int col2 = param.getParameter(3);

        rc.checkNotEmpty(wpc,row1,col1);
        Integer v1 = wpc.getCell(row1,col1).getDie().getDieValue();
        Colour c1 = wpc.getCell(row1,col1).getDie().getDieColour();
        Die d = new Die(v1,c1);

        // Restrictions check, colour restriction not checked
        rc.checkEmptiness(wpc,row2,col2);
        rc.checkValueRestriction(wpc, row2, col2, d);
        rc.checkAdjacent(wpc, row2, col2);
        rc.checkSameDie(wpc,row2,col2,d);

        wpc.setDie(row2, col2, d);
        wpc.removeDie(row1,col1);

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
