package it.polimi.se2018.networking.client;

import it.polimi.se2018.utils.Printer;
import it.polimi.se2018.view.cli.ClientCLI;
import it.polimi.se2018.view.gui.GUImain;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args){
        Printer out = new Printer();
        Scanner in = new Scanner(System.in);
        int choice;

        do{
            out.println("Choose between CLI or GUI. [1 cli, 2 gui]");
            try{
                choice = in.nextInt();
            }
            catch(InputMismatchException e){
                choice = 0;
            }
        }while(!(choice == 1 || choice == 2));

        if(choice == 1) new ClientCLI();
        else GUImain.main(null);
    }
}
