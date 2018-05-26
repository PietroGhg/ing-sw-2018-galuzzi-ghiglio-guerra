package it.polimi.se2018.view;

/**
 * Contains the strings representing the extracted boards and an array containing the IDs of the boards.
 * @author Pietro Ghiglio
 */
public class MVSetUpMessage extends MVAbstractMessage {
    private String[] wpcs;
    private int[] ids;

    public MVSetUpMessage(int playerID, String[] wpcs,int[] indexes){
        this.playerID = playerID;
        this.wpcs = wpcs;
        this.ids = indexes;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public int getPlayerID(){
        return playerID;
    }

    public int[] getIDs(){ return ids; }

    public String[] getWpcs(){
        return wpcs;
    }

}
