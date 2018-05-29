package it.polimi.se2018.utils;

import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;

public interface RawInputObserver {
    public void rawUpdate(RawInputMessage message);
}
