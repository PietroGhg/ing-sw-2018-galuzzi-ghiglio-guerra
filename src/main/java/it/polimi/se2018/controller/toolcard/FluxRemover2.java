package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

public class FluxRemover2 {
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();

        int cellRow = param.getParameter(0);
        int cellCol = param.getParameter(1);

        //Prendere il dado ottenuto alla fine di FluxRemover
        Die d = new Die();
        //Die d = param.getDie();  (di FluxRemover: floatingDie?)

        WPC wpc = player.getWpc();
        //Check all the restrictions
        rc.checkFirstMove(wpc,cellRow,cellCol);
        rc.checkEmptiness(wpc,cellRow,cellCol);
        rc.checkValueRestriction(wpc,cellRow,cellCol,d);
        rc.checkColourRestriction(wpc,cellRow,cellCol,d);
        rc.checkAdjacent(wpc,cellRow,cellCol);
        rc.checkSameDie(wpc,cellRow,cellCol,d);

        wpc.setDie(cellRow,cellCol,d);
    }
}
