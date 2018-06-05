package it.polimi.se2018.view;

import java.util.List;

public class MVTC6Message extends MVGameMessage {
    private String message;
    private List<int[]> validCoordinates;

    public MVTC6Message(int playerID, String message, List<int[]> validCoordinates){
        super(message, playerID);
        this.message = message;
        this.validCoordinates = validCoordinates;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public String getMessage(){ return message; }
    public List<int[]> getValidCoordinates(){ return validCoordinates; }
}
