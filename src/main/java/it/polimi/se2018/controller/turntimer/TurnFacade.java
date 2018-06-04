package it.polimi.se2018.controller.turntimer;

import it.polimi.se2018.model.table.Model;

public class TurnFacade {
    private Model model;

    public TurnFacade(Model model){
        this.model = model;
    }

    public void sendMVTimesUpMessage(){
        model.setMVTimesUpMessage();
    }

    public void nextTurn(TurnTimer timer) {
        model.nextTurn(timer);
    }
}
