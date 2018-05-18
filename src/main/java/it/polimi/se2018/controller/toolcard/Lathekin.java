package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

public class Lathekin implements ToolCard{
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException {
        WPC temp = new WPC(param.getPlayer().getWpc());
        //First die to move
        int row1 = param.getParameter(0);
        int col1 = param.getParameter(1);
        //Recipient cell for the first die
        int row2 = param.getParameter(2);
        int col2 = param.getParameter(3);
        //Second die to move
        int row3 = param.getParameter(4);
        int col3 = param.getParameter(5);
        //Recipient cell for the second die
        int row4 = param.getParameter(6);
        int col4 = param.getParameter(7);

        RestrictionChecker rc = new RestrictionChecker();

        Integer v1 = temp.getCell(row1, col1).getDie().getDieValue();
        Colour c1 = temp.getCell(row1, col1).getDie().getDieColour();
        Integer v2 = temp.getCell(row3, col3).getDie().getDieValue();
        Colour c2 = temp.getCell(row3, col3).getDie().getDieColour();
        Die d1 = new Die(v1, c1);
        Die d2 = new Die(v2, c2);

        // Restriction check for the first die
        rc.checkEmptiness(temp, row2, col2);
        rc.checkColourRestriction(temp, row2, col2, d1);
        rc.checkValueRestriction(temp, row2, col2, d1);
        rc.checkAdjacent(temp, row2, col2);
        rc.checkSameDie(temp,row2,col2,d1);
        //First die moving
        temp.setDie(row2, col2, d1);
        temp.removeDie(row1, col1);

        // Restriction check for the second die
        rc.checkEmptiness(temp, row4, col4);
        rc.checkColourRestriction(temp, row4, col4, d2);
        rc.checkValueRestriction(temp, row4, col4, d2);
        rc.checkAdjacent(temp, row4, col4);
        rc.checkSameDie(temp,row4,col4,d2);
        //Second die moving
        temp.setDie(row4, col4, d2);
        temp.removeDie(row3,col3);

        param.getPlayer().setWpc(temp);
    }
}
