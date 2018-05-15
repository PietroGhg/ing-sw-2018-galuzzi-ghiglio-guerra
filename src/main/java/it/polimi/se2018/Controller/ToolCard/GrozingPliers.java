package it.polimi.se2018.Controller.ToolCard;

import it.polimi.se2018.Exceptions.MoveNotAllowedException;
import it.polimi.se2018.Model.Player;
import it.polimi.se2018.Model.PlayerMoveParameters;
import it.polimi.se2018.Model.WPC.WPC;

public class GrozingPliers implements ToolCard{   //Pinza Sgrossatrice

    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        WPC temp = new WPC( param.getPlayer().getWpc() );
        int row = param.getParameter(0);
        int col = param.getParameter(1);
        int increment = param.getParameter(2);

        if (increment == +1) temp.getCell(row, col).getDie().increase();
        if (increment == -1) temp.getCell(row, col).getDie().decrease();

        param.getPlayer().setWpc(temp);
    }
}
