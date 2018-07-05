package it.polimi.se2018.controller.vcmessagecreator;

/**
 * Class for the requested message
 */
public class RawRequestedMessage implements RawInputMessage{

    private int value;

    @Override
    public void accept(VCMessageCreator c) {
        c.visit(this);
    }

    public RawRequestedMessage(int input){
        this.value = input;
    }

    public int getValue(){ return value; }
}
