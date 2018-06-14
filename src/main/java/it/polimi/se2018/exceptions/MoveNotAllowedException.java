package it.polimi.se2018.exceptions;

public class MoveNotAllowedException extends Exception {
    private final String message;
    public MoveNotAllowedException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
