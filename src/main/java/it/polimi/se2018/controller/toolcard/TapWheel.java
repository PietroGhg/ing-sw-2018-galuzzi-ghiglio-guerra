package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

/**
 * Class for ToolCard TapWheel
 * @author Leonardo Guerra
 */

public class TapWheel implements ToolCard{    //Taglierina Manuale

    private static TapWheel instance;
    private TapWheel(){};
    public static TapWheel getInstance(){
        if (instance==null) instance = new TapWheel();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        WPC temp = player.getWpc();

        int rtNumber = param.getParameter(0);
        int rtIndex = param.getParameter(1);
        int rowDie1 = param.getParameter(2);
        int colDie1 = param.getParameter(3);
        int rowCell1 = param.getParameter(4);
        int colCell1 = param.getParameter(5);

        ArrayList<ArrayList<Die>> rt = param.getRoundTrack();
        rc.checkRTCellNotEmpty(rt,rtNumber,rtIndex);
        rc.checkMatchingColour(temp.getCell(rowDie1,colDie1).getDie(),rt.get(rtNumber).get(rtIndex));
        Die d1 = new Die(temp.getCell(rowDie1, colDie1).getDie().getDieValue(), temp.getCell(rowDie1, colDie1).getDie().getDieColour());
        temp.removeDie(rowDie1,colDie1);

        rc.checkAdjacent(temp,rowCell1,colCell1);
        rc.checkEmptiness(temp,rowCell1,colCell1);
        rc.checkSameDie(temp,rowCell1,colCell1,d1);
        rc.checkValueRestriction(temp,rowCell1,colCell1,d1);
        rc.checkColourRestriction(temp,rowCell1,colCell1,d1);
        temp.setDie(rowCell1,colCell1,d1);

        //Second die moving
        if(param.paramCount()==10){
            int rowDie2 = param.getParameter(6);
            int colDie2 = param.getParameter(7);
            int rowCell2 = param.getParameter(8);
            int colCell2 = param.getParameter(9);
            rc.checkMatchingColour(temp.getCell(rowDie2,colDie2).getDie(),rt.get(rtNumber).get(rtIndex));
            Die d2 = new Die(temp.getCell(rowDie2, colDie2).getDie().getDieValue(), temp.getCell(rowDie2, colDie2).getDie().getDieColour());
            temp.removeDie(rowDie2,colDie2);

            rc.checkAdjacent(temp,rowCell2,colCell2);
            rc.checkEmptiness(temp,rowCell2,colCell2);
            rc.checkSameDie(temp,rowCell2,colCell2,d2);
            rc.checkValueRestriction(temp,rowCell2,colCell2,d2);
            rc.checkColourRestriction(temp,rowCell2,colCell2,d2);
            temp.setDie(rowCell2,colCell2,d2);
        }

        player.setWpc(temp);

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
