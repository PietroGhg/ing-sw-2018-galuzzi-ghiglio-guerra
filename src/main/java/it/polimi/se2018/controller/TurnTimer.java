package it.polimi.se2018.controller;

import it.polimi.se2018.controller.turntimer.TurnFacade;

import java.util.Timer;
import java.util.TimerTask;

public class TurnTimer {
    private TimerTask task;
    private Timer timer;
    private TurnFacade model;
    private int turnDuration;

    public TurnTimer(int turnDuration, TurnFacade model){
        this.turnDuration = turnDuration;
        this.model = model;
        timer = new Timer();
        task = new MyTask(this);
    }

    public void reset(){
        task.cancel();
        task = new MyTask(this);

        timer.schedule(task, (long)turnDuration*1000);
    }

    public void cancel(){
        timer.cancel();
    }

    private class MyTask extends TimerTask {
        TurnTimer timer;
        public MyTask(TurnTimer timer){
            this.timer = timer;
        }
        @Override
        public void run(){
            model.sendMVTimesUpMessage();
            model.nextTurn(timer);
        }
    }


}
