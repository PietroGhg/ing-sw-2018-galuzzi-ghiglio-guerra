package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.wpc.WPC;

public class Lathekin implements ToolCard{

    private static Lathekin instance;
    private Lathekin(){};
    public static Lathekin getInstance(){
        if (instance==null) instance = new Lathekin();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    public void cardAction(Model model) throws MoveNotAllowedException {
        RestrictionChecker rc = new RestrictionChecker();
        PlayerMoveParameters param = model.getParameters();
        int playerID = param.getPlayerID();
        Player player = model.getPlayer(playerID);
        rc.checkEnoughFavorTokens(player,instance);

        //creates a copy because the second die may be moved in place of the first one moved
        WPC temp = new WPC(player.getWpc());
        //First die to move
        int rowDie1 = param.getParameter(0);
        int colDie1 = param.getParameter(1);
        //Recipient cell for the first die
        int rowCell1 = param.getParameter(2);
        int colCell1 = param.getParameter(3);
        //Second die to move
        int rowDie2 = param.getParameter(4);
        int colDie2 = param.getParameter(5);
        //Recipient cell for the second die
        int rowCell2 = param.getParameter(6);
        int colCell2 = param.getParameter(7);



        rc.checkNotEmpty(temp,rowDie1,colDie1);
        Integer v1 = temp.getCell(rowDie1, colDie1).getDie().getDieValue();
        Colour c1 = temp.getCell(rowDie1, colDie1).getDie().getDieColour();

        rc.checkNotEmpty(temp,rowDie2,colDie2);
        Integer v2 = temp.getCell(rowDie2, colDie2).getDie().getDieValue();
        Colour c2 = temp.getCell(rowDie2, colDie2).getDie().getDieColour();

        Die d1 = new Die(v1, c1);
        Die d2 = new Die(v2, c2);

        //Restrictions check for the first die
        rc.checkEmptiness(temp, rowCell1, colCell1);
        rc.checkColourRestriction(temp, rowCell1, colCell1, d1);
        rc.checkValueRestriction(temp, rowCell1, colCell1, d1);
        rc.checkAdjacent(temp, rowCell1, colCell1);
        rc.checkSameDie(temp,rowCell1,colCell1,d1);
        //First die moving
        temp.setDie(rowCell1, colCell1, d1);
        temp.removeDie(rowDie1, colDie1);

        //Restrictions check for the second die
        rc.checkEmptiness(temp, rowCell2, colCell2);
        rc.checkColourRestriction(temp, rowCell2, colCell2, d2);
        rc.checkValueRestriction(temp, rowCell2, colCell2, d2);
        rc.checkAdjacent(temp, rowCell2, colCell2);
        rc.checkSameDie(temp,rowCell2,colCell2,d2);
        //Second die moving
        temp.setDie(rowCell2, colCell2, d2);
        temp.removeDie(rowDie2,colDie2);

        player.setWpc(temp);

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
