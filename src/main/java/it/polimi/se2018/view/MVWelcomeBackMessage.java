package it.polimi.se2018.view;

import it.polimi.se2018.view.gui.GUIcontroller;
import javafx.application.Platform;

import java.util.List;

/**
 * Class for the messages from the Model to the View, sent when a player reconnects.
 * Contains also the string representing the extracted cards.
 */
public class MVWelcomeBackMessage extends MVGameMessage {
    private String playerName;
    private String prCard;
    private String[] puCards;
    private List<String> tcInUse;

    public MVWelcomeBackMessage(int playerID, String playerName, String message){
        super(message, playerID);
        this.playerName = playerName;
    }

    @Override
    public void accept(AbstractView view){ view.visit(this); }

    @Override
    public void accept(GUIcontroller gc){
        MVWelcomeBackMessage m = this;
        Platform.runLater(() -> gc.visit(m));
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
