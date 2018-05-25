package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;

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

        //Controllo che la DraftPool non sia vuota
        rc.checkDPNotEmpty(param.getDraftPool());

        Player player = param.getPlayer();
        rc.checkEnoughFavorTokens(player,instance);

        //Checks if the current is the second turn of the player
        if(param.turnNumber(param.getPlayer().getPlayerID())==1)
            { throw new MoveNotAllowedException("Error: this card can be played in the second turn only."); }
        //Checks if the player hasn't drafted a die yet
        if(param.dieHasBeenPlayed()) throw new MoveNotAllowedException("Error: a die is already been drafted.");

        //Randomization of the value of all the Draft Pool dice
        for (Die d : param.getDraftPool()) {
            d.roll();
        }

        player.setFavorTokens(player.getFavorTokens() - favorTokensNeeded);
        if (favorTokensNeeded == 1){
            favorTokensNeeded = 2;
        }
    }
}
