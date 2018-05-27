package it.polimi.se2018.view.cli;

import it.polimi.se2018.exceptions.InputNotValidException;
import it.polimi.se2018.view.*;
import java.util.Scanner;

/* Manages interrogation to be asked the user
 *@author Andrea Galuzzi
 */

public class View extends AbstractView {
    private int currentplayerID;
    private int currentstate;
    private String playerName;

    public View() {


    }

    public void chooseInterrogation(MVAbstractMessage m) {


    }

    public void chooseName() {
        System.out.println("Insert your name");
        Scanner userInput = new Scanner(System.in);
        playerName = userInput.next();


    }

    public void visit(MVGameMessage message) {


    }

    public void visit(MVSetUpMessage message) {


    }

    public void visit(MVExtractedCardsMessage message) {


    }

    public void getCoordinates() {

        System.out.println("Insert row number");
        Scanner rowInput = new Scanner(System.in);
        int row = rowInput.nextInt();

        System.out.println("Insert column number ");
        Scanner columnInput = new Scanner(System.in);
        int column = columnInput.nextInt();


    }

    //the player has to choose his wpc for the game

    public void chooseWpc() throws InputNotValidException {

        int choice = 0;

        System.out.println("Choose wpc number (Form 1 to 4)");
        choice = Integer.valueOf(choice).intValue();
        if (1 < choice && choice < 4) {
            throw new InputNotValidException();
        }

    }

    //the player has to digit his name

    public void setPlayerName(){

        String playerName;

        System.out.println("Insert your name");
        Scanner input = new Scanner(System.in);
        playerName = input.nextLine();
        System.out.println("Your name is: " +playerName);


    }


}



