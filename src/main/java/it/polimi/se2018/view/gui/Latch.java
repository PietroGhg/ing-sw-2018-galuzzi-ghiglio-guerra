package it.polimi.se2018.view.gui;

import java.util.concurrent.CountDownLatch;

public class Latch {
    private CountDownLatch cdl;

    void reset(){
        cdl = new CountDownLatch(1);
    }

    void await(){
        try{
            cdl.await();
        }
        catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }

    void countDown(){
        cdl.countDown();
    }
}