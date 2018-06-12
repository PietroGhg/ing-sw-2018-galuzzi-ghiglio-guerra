package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for ToolCard CopperFoilBurnisher
 * @author Leonardo Guerra
 */

public class CopperFoilBurnisher extends ToolCard{ //Alesatore per Lamina di Rame

    private static CopperFoilBurnisher instance;
    private CopperFoilBurnisher(){};
    public static CopperFoilBurnisher getInstance(){
        if (instance==null) instance = new CopperFoilBurnisher();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        WPC temp = new WPC(player.getWpc());

        //Die to move
        int row1 = param.getParameter(0);
        int col1 = param.getParameter(1);
        //Recipient cell
        int row2 = param.getParameter(2);
        int col2 = param.getParameter(3);

        rc.checkNotEmpty(temp,row1,col1);
        Integer v = temp.getCell(row1,col1).getDie().getDieValue();
        Colour c = temp.getCell(row1,col1).getDie().getDieColour();
        Die d = new Die(v,c);
        temp.removeDie(row1,col1);

        // Restrictions check, value restriction not checked
        rc.checkEmptiness(temp,row2,col2);
        rc.checkColourRestriction(temp, row2, col2, d);
        rc.checkAdjacent(temp, row2, col2);
        rc.checkSameDie(temp,row2,col2,d);

        temp.setDie(row2, col2, d);
        player.setWpcOnly(temp);

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
