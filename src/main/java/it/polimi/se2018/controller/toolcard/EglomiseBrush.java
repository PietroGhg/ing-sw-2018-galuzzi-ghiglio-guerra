package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

public class EglomiseBrush implements ToolCard{   //Pennello per Eglomise
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException {
        WPC temp = new WPC( param.getPlayer().getWpc() );

        //Die to move
        int row1 = param.getParameter(0);
        int col1 = param.getParameter(1);
        //Recipient cell
        int row2 = param.getParameter(2);
        int col2 = param.getParameter(3);

        RestrictionChecker rc = new RestrictionChecker();

        rc.checkNotEmpty(temp,row1,col1);
        Integer v1 = temp.getCell(row1,col1).getDie().getDieValue();
        Colour c1 = temp.getCell(row1,col1).getDie().getDieColour();
        Die d1 = new Die(v1,c1);

        // Restrictions check
        rc.checkEmptiness(temp,row2,col2);
        rc.checkValueRestriction(temp, row2, col2, d1);
        rc.checkAdjacent(temp, row2, col2);
        rc.checkSameDie(temp,row2,col2,d1);

        temp.setDie(row2, col2, d1);
        temp.removeDie(row1,col1);

        param.getPlayer().setWpc(temp);
    }
}
