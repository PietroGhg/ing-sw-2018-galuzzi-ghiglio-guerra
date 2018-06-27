package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

import java.util.List;

public class MVWelcomeBackMessage extends MVGameMessage {
    String playerName;
    private String prCard;
    private String[] puCards;
    private List<String> tcInUse;

    public MVWelcomeBackMessage(int playerID, String playerName, String message){
        super(message, playerID);
        this.playerName = playerName;
    }

    @Override
    public void accept(AbstractView view){ view.visit(this); }

    public void accept(GUIcontroller gc){
        MVWelcomeBackMessage m = this;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gc.visit(m);
            }
        });
    }

    public String getPrCard() {
        return prCard;
    }

    public String[] getPuCards() {
        return puCards;
    }

    public List<String> getTcInUse() {
        return tcInUse;
    }

    public void setPrCard(String prCard) {
        this.prCard = prCard;
    }

    public void setPuCards(String[] puCards) {
        this.puCards = puCards;
    }

    public void setTcInUse(List<String> tcInUse) {
        this.tcInUse = tcInUse;
    }

    public String getPlayerName() {
        return playerName;
    }

}
