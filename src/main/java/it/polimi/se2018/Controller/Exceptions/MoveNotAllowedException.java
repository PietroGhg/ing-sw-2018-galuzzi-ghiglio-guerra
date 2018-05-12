package it.polimi.se2018.Controller.Exceptions;

public class MoveNotAllowedException extends Exception {
    String message;
    public MoveNotAllowedException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
