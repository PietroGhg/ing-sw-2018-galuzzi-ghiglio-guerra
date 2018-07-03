package it.polimi.se2018.utils;

import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;

/**
 * Class to handle the user's input
 */
public interface RawInputObserver {
    public void rawUpdate(RawInputMessage message);
}
