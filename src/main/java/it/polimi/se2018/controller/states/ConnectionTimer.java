package it.polimi.se2018.controller.states;

import it.polimi.se2018.controller.Controller;

import java.util.TimerTask;

/**
 * Class used to wrap a TimerTask object.
 * Since it is not possible to reschedule a task that has been canceled, this class allows to have a new TimerTask with the same run() method,
 * and also allows to easily keep track of the fact that a task has been scheduled or not.
 * @author Pietro Ghiglio
 */
public class ConnectionTimer {
    private Controller c;
    private TimerTask task;
    private boolean isScheduled;

    public ConnectionTimer(Controller c){
        this.c = c;
        isScheduled = false;
    }

    public void setScheduled(boolean isScheduled){
        this.isScheduled = isScheduled;
    }

    public boolean isScheduled(){ return isScheduled; }

    public void cancel(){
        task.cancel();
        isScheduled = false;
    }

    public TimerTask newTask(){
        if(isScheduled)
            task.cancel();

        task = new TimerTask() {
            @Override
            public void run() {
                c.startGame();
            }};
        return task;
    }
}
