package it.polimi.se2018.controller.toolcard;

import it.polimi.se2018.controller.toolcard.Effects.ModifyValueBy1Effect;
import it.polimi.se2018.controller.toolcard.Effects.MoveDieEffect;
import it.polimi.se2018.exceptions.MoveNotAllowedException;
import it.polimi.se2018.model.PlayerMoveParameters;

public class TCFactory {

    public void cardAction(int tcNumber, PlayerMoveParameters param) throws MoveNotAllowedException{
        TC tc = new TC();
        switch(tcNumber){
            case 1:
                //
                tc = new ModifyValueBy1Effect(1,param);
                //
                break;
            case 2:
                tc = new MoveDieEffect(2,param);
                break;
            case 3:
                tc = new MoveDieEffect(3,param);
                break;
            case 4:
                tc = new MoveDieEffect(4,param);
                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            case 8:

                break;
            case 9:

                break;
            case 10:

                break;
            case 11:

                break;
            case 12:

                break;

        }
    }
}
