package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

/**
 * Class for toolcard GrozingPliers
 * @author Leonardo Guerra
 */

public class GrozingPliers implements ToolCard{   //Pinza Sgrossatrice

    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        WPC temp = new WPC( param.getWpc() );
        RestrictionChecker rc = new RestrictionChecker();

        int row = param.getParameter(0);
        int col = param.getParameter(1);
        int increment = param.getParameter(2);

        rc.checkNotEmpty(temp,row,col);
        if (increment == +1) temp.getCell(row, col).getDie().increase();
        if (increment == -1) temp.getCell(row, col).getDie().decrease();

        param.getPlayer().setWpc(temp);
    }
}
