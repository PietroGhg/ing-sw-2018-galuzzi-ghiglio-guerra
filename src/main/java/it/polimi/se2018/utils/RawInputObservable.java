package it.polimi.se2018.utils;

import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;

/**
 * Class to handle the user's input
 */
public interface RawInputObservable {
    public void rawRegister(RawInputObserver message);
    public void rawNotify(RawInputMessage message);
}
