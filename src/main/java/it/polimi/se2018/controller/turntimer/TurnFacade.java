package it.polimi.se2018.controller.turntimer;

import it.polimi.se2018.controller.TurnTimer;
import it.polimi.se2018.model.table.Model;

public class TurnFacade {
    private Model model;

    public TurnFacade(Model model){
        this.model = model;
    }

    public void sendMVTimesUpMessage(){
        model.sendMVTimesUpMessage();
    }

    public void nextTurn(TurnTimer timer) {
        model.nextTurn(timer);
    }
}
