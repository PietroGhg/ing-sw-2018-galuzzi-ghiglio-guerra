package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

import java.util.ArrayList;

public class FluxBrush2 implements ToolCard{
    private static FluxBrush2 instance;

    public static FluxBrush2 getInstance(){
        if (instance == null) instance = new FluxBrush2();
        return instance;
    }

    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        //Se mettiamo il dado ottenuto da FluxBrush e quello ottenuto da FluxRemover
            //in floatingDie, possiamo usare la stessa classe per continuare
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();

        int cellRow = param.getParameter(0);
        int cellCol = param.getParameter(1);

        //Prendere il dado ottenuto alla fine di FluxBrush
        ArrayList<Die> dp = param.getDraftPool();
        Die d = dp.get(dp.size() - 1);

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

    public int getFavorTokensNeeded(){ return 0; }
}
