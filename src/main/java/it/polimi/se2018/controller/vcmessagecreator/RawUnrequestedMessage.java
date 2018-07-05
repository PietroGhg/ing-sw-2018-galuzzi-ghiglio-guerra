package it.polimi.se2018.controller.vcmessagecreator;

/**
 * Class for the unrequested message
 */
public class RawUnrequestedMessage implements RawInputMessage{

    private String input;

    @Override
    public void accept(VCMessageCreator c) {
        c.visit(this);
    }

    public RawUnrequestedMessage(String input){

        this.input = input;
    }

    public String getInput(){
        return input;
    }
}
