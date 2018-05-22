package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.DiceBag;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.model.table.RoundTrack;

import java.util.ArrayList;
import java.util.List;

public class LensCutter implements ToolCard{  //Taglierina Circolare

    private static LensCutter instance;
    private LensCutter(){};
    public static LensCutter getInstance(){
        if (instance==null) instance = new LensCutter();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(Model model) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        ArrayList<ArrayList<Die>> rt = model.getRoundTrack().getRT();
        ArrayList<Die> dp = model.getDraftPool();

        PlayerMoveParameters param = model.getParameters();
        int playerID = param.getPlayerID();
        Player player = model.getPlayer(playerID);
        rc.checkEnoughFavorTokens(player,instance);

        int dpIndex = param.getParameter(0);
        int rtNumber = param.getParameter(1);
        int rtIndex = param.getParameter(2);

        //da completare con il corpo principale del metodo

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }

    }
}
