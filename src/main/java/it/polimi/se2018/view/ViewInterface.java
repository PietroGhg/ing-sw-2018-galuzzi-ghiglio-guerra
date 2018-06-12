package it.polimi.se2018.view;

import it.polimi.se2018.controller.VCAbstractMessage;
import it.polimi.se2018.controller.vcmessagecreator.RawInputMessage;
import it.polimi.se2018.utils.RawInputObserver;

import java.util.List;

public interface ViewInterface {
    int getPlayerID();
    void getCoordinates(String m);
    void getCoordinates2();
    void getValidCoordinates(List<int[]> validCoordinates);
    void getIncrement();
    void getDraftPoolIndex();
    void getRoundTrackPosition(String s);
    void newDieValue();
    void displayMessage(String message);

    void showRoundTrack();
    void showMyBoard();
    void showBoards();
    void showDraftPool();

    void notifyController(VCAbstractMessage message);
    void rawRegister(RawInputObserver observer);
    void rawNotify(RawInputMessage message);
}
