package it.polimi.se2018.exceptions;

public class MoveNotAllowedException extends Exception {
    private String message;
    public MoveNotAllowedException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
