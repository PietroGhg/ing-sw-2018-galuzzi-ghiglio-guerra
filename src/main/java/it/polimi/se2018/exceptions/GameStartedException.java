package it.polimi.se2018.exceptions;

public class GameStartedException extends Exception {
    private static final String MESSAGE = "Error: a game is already running.";

    @Override
    public String getMessage(){ return MESSAGE; }
}
