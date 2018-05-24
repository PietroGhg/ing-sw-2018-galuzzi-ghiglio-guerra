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

        WPC wpc = player.getWpc();

        int rtNumber = param.getParameter(0);
        int rtIndex = param.getParameter(1);
        int rowDie1 = param.getParameter(2);
        int colDie1 = param.getParameter(3);
        int rowCell1 = param.getParameter(4);
        int colCell1 = param.getParameter(5);

        ArrayList<ArrayList<Die>> rt = param.getRoundTrack();
        rc.checkRTCellNotEmpty(rt,rtNumber,rtIndex);
        rc.checkMatchingColour(wpc.getCell(rowDie1,colDie1).getDie(),rt.get(rtNumber).get(rtIndex));
        Die d1 = new Die(wpc.getCell(rowDie1, colDie1).getDie().getDieValue(), wpc.getCell(rowDie1, colDie1).getDie().getDieColour());
        rc.checkAdjacent(wpc,rowCell1,colCell1);
        rc.checkEmptiness(wpc,rowCell1,colCell1);
        rc.checkSameDie(wpc,rowCell1,colCell1,d1);
        rc.checkValueRestriction(wpc,rowCell1,colCell1,d1);
        rc.checkColourRestriction(wpc,rowCell1,colCell1,d1);
        wpc.setDie(rowCell1,colCell1,d1);
        wpc.removeDie(rowDie1,colDie1);

        //Second die moving
        if(param.paramCount()==10){
            int rowDie2 = param.getParameter(6);
            int colDie2 = param.getParameter(7);
            int rowCell2 = param.getParameter(8);
            int colCell2 = param.getParameter(9);
            rc.checkMatchingColour(wpc.getCell(rowDie2,colDie2).getDie(),rt.get(rtNumber).get(rtIndex));
            Die d2 = new Die(wpc.getCell(rowDie2, colDie2).getDie().getDieValue(), wpc.getCell(rowDie2, colDie2).getDie().getDieColour());
            rc.checkAdjacent(wpc,rowCell2,colCell2);
            rc.checkEmptiness(wpc,rowCell2,colCell2);
            rc.checkSameDie(wpc,rowCell2,colCell2,d2);
            rc.checkValueRestriction(wpc,rowCell2,colCell2,d2);
            rc.checkColourRestriction(wpc,rowCell2,colCell2,d2);
            wpc.setDie(rowCell2,colCell2,d2);
            wpc.removeDie(rowDie2,colDie2);
            //spostare un altro dado
        }

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
