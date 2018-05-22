package it.polimi.se2018.controller.messages;

import java.util.ArrayList;

public class VCGameMessage {
    private int playerID;  //CLI: to ask
    private int toolcardID; //CLI: to ask
    private boolean isToolCardMove;  //CLI: true if selected move, one time per turn
    private boolean isDiceMove; //CLI: true if selected move, one time per turn
    private boolean isEndTurn;  //CLI: true if selected move, go next turn
    private ArrayList<Integer> parameters; //CLI: take care about the right order of parameters (Tool Cards)

    public VCGameMessage(int playerID, int toolcardID, ArrayList<Integer> parameters){//CLI: need make the user complete
        this.playerID = playerID;                                                     //CLI: this parameters
        this.toolcardID = toolcardID;

        if(toolcardID == 0) isToolCardMove = false;
        else isToolCardMove = true;

        this.parameters = parameters;
    }

    public int getPlayerID() {
        return playerID;
    } //CLI: to ask ID player

    public int getToolcardID() {
        return toolcardID;
    } //CLI: to ask ID for ToolCard

    public boolean isToolCardMove(){
        return isToolCardMove;
    }//CLI: selected move

    public boolean isDiceMove(){
        return isDiceMove;
    }//CLI: selected move

    public boolean isEndTurn(){
        return isEndTurn;
    }//CLI: selected move

    public ArrayList<Integer> getParameters() {
        return parameters;
    }//CLI: to carry the entire array
}
