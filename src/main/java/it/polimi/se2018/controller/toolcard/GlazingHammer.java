package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.table.Model;

import java.util.Random;

/**
 * Class for ToolCard GlazingHammer
 * @author Leonardo Guerra
 */

public class GlazingHammer implements ToolCard{//Martelletto

    private static GlazingHammer instance;
    private GlazingHammer(){};
    public static GlazingHammer getInstance(){
        if (instance==null) instance = new GlazingHammer();
        return instance;
    }

    private int favorTokensNeeded=1;
    public int getFavorTokensNeeded(){ return favorTokensNeeded; }

    @Override
    public void cardAction(PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        /*
        Controllo che la DraftPool non sia vuota (?):
        */
        rc.checkDPNotEmpty(param.getDraftPool());

        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        /*
        in the second turn of the round only -> rc.checkSecondTurn(player,?);
        before drafting -> rc.checkNotDrafted(player.?);
        */

        //Randomization of the value of the Draft Pool dice
        Random r;
        int tot = 6;
        r = new Random();
        for (Die d : param.getDraftPool()) {
            Integer randomValue = r.nextInt(tot) + 1;
            d.setDieValue(randomValue);
        }

        int currentFT = player.getFavorTokens() - favorTokensNeeded;
        player.setFavorTokens(currentFT);

        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
