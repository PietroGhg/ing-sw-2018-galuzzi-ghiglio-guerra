package it.polimi.se2018.exceptions;

/**
 * Exception caught if, in case of connection request, the player is not in the disconnected players list
 */
public class GameStartedException extends Exception {
    private static final String MESSAGE = "Error: a game is already running.";

    @Override
    public String getMessage(){ return MESSAGE; }
}
