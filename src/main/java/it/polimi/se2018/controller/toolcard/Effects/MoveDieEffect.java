package it.polimi.se2018.controller.toolcard.Effects;

import it.polimi.se2018.controller.RestrictionChecker;
import it.polimi.se2018.controller.toolcard.TC;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.Colour;
import it.polimi.se2018.model.Die;
import it.polimi.se2018.model.Player;
import it.polimi.se2018.model.PlayerMoveParameters;
import it.polimi.se2018.model.wpc.WPC;

public class MoveDieEffect extends TC{
    public MoveDieEffect(int tcNumber, PlayerMoveParameters param) throws MoveNotAllowedException{
        RestrictionChecker rc = new RestrictionChecker();
        Player player = param.getPlayer();
        WPC temp = player.getWpc();

        //Die to move
        int row1 = param.getParameter(0);
        int col1 = param.getParameter(1);
        //Recipient cell
        int row2 = param.getParameter(2);
        int col2 = param.getParameter(3);

        rc.checkNotEmpty(temp,row1,col1);
        Integer v1 = temp.getCell(row1,col1).getDie().getDieValue();
        Colour c1 = temp.getCell(row1,col1).getDie().getDieColour();
        Die d = new Die(v1,c1);
        temp.removeDie(row1,col1);

        // Restrictions check, colour restriction not checked
        rc.checkEmptiness(temp,row2,col2);
        if(tcNumber!=3) {
            rc.checkValueRestriction(temp, row2, col2, d);
        }
        if(tcNumber!=2){
            rc.checkColourRestriction(temp,row2,col2,d);
        }
        rc.checkAdjacent(temp, row2, col2);
        rc.checkSameDie(temp,row2,col2,d);

        temp.setDie(row2, col2, d);

        if(tcNumber==4){
            //Second die to move
            int rowDie2 = param.getParameter(4);
            int colDie2 = param.getParameter(5);
            //Recipient cell for the second die
            int rowCell2 = param.getParameter(6);
            int colCell2 = param.getParameter(7);

            rc.checkNotEmpty(temp,rowDie2,colDie2);
            Integer v2 = temp.getCell(rowDie2, colDie2).getDie().getDieValue();
            Colour c2 = temp.getCell(rowDie2, colDie2).getDie().getDieColour();
            Die d2 = new Die(v2, c2);
            temp.removeDie(rowDie2,colDie2);

            //Restrictions check for the second die
            rc.checkEmptiness(temp, rowCell2, colCell2);
            rc.checkColourRestriction(temp, rowCell2, colCell2, d2);
            rc.checkValueRestriction(temp, rowCell2, colCell2, d2);
            rc.checkAdjacent(temp, rowCell2, colCell2);
            rc.checkSameDie(temp,rowCell2,colCell2,d2);
            //Second die moving
            temp.setDie(rowCell2, colCell2, d2);
        }

        player.setWpcOnly(temp);
    }
}
