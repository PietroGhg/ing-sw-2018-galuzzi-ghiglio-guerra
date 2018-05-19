package it.polimi.se2018.utils.messages;

import java.util.ArrayList;

public class VCGameMessage {
    private int playerID;
    private int toolcardID;
    private boolean isToolCardMove;
    private boolean isDiceMove;
    private boolean isEndTurn;
    private ArrayList<Integer> parameters;

    public VCGameMessage(int playerID, int toolcardID, ArrayList<Integer> parameters){
        this.playerID = playerID;
        this.toolcardID = toolcardID;

        if(toolcardID == 0) isToolCardMove = false;
        else isToolCardMove = true;

        this.parameters = parameters;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getToolcardID() {
        return toolcardID;
    }

    public boolean isToolCardMove(){
        return isToolCardMove;
    }

    public boolean isDiceMove(){
        return isDiceMove;
    }

    public boolean isEndTurn(){
        return isEndTurn;
    }

    public ArrayList<Integer> getParameters() {
        return parameters;
    }
}
