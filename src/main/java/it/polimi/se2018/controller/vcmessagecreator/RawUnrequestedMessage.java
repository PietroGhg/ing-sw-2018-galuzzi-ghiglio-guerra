package it.polimi.se2018.controller.vcmessagecreator;

public class RawUnrequestedMessage implements RawInputMessage{

    private String input;

    @Override
    public void accept(VCMessageCreator c) {
        c.visit(this);
    }

    @Override
    public void accept(VCGUIMessageCreator c) {
        c.visit(this);
    }

    public RawUnrequestedMessage(String input){

        this.input = input;
    }

    public String getInput(){
        return input;
    }
}
