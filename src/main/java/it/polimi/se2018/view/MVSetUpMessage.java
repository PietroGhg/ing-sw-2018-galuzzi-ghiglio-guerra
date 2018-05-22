package it.polimi.se2018.view;

public class MVSetUpMessage extends MVMessage{
    private String[] wpcs;

    public MVSetUpMessage(int playerID, String[] wpcs){
        this.playerID = playerID;
        this.wpcs = wpcs;
    }

    public void accept(AbstractView view){ view.visit(this); }

    public int getPlayerID(){
        return playerID;
    }

    public String[] getWpcs(){
        return wpcs;
    }

}
