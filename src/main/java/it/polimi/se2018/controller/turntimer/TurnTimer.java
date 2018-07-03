package it.polimi.se2018.controller.turntimer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Timer for the turn
 */
public class TurnTimer {
    private static final Logger LOGGER = Logger.getLogger(TurnTimer.class.getName());
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

        timer.schedule(task, (long)turnDuration*1000*60);
    }

    public void cancel(){
        try {
            timer.cancel();
        }
        catch(IllegalStateException e){
            LOGGER.log(Level.SEVERE, "Trying to cancel an already canceled turntimer");
        }
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
