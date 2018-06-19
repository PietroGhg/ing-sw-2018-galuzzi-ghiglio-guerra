package it.polimi.se2018.view;

public class MVDiscMessage extends MVAbstractMessage {
    private String message;

    public MVDiscMessage(String message){
        this.message = message;
    }

    public void accept(AbstractView view){
        view.visit(this);
    }

    public String getMessage(){return message; }
}
