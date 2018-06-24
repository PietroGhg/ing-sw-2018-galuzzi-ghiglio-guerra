package it.polimi.se2018.controller.vcmessagecreator;

public interface RawInputMessage {
    void accept(VCMessageCreator c);
    void accept(VCGUIMessageCreator c);
}
