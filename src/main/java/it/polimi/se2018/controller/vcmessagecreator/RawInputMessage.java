package it.polimi.se2018.controller.vcmessagecreator;

/**
 * Interface for the user's input message
 */
public interface RawInputMessage {
    void accept(VCMessageCreator c);
    void accept(VCGUIMessageCreator c);
}
