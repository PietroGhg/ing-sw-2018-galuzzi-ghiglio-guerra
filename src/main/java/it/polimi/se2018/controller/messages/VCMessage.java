package it.polimi.se2018.controller.messages;

import it.polimi.se2018.controller.Controller;

import java.io.Serializable;

public interface VCMessage extends Serializable {
    public void accept(Controller c);
}
