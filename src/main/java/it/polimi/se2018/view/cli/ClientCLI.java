package it.polimi.se2018.view.cli;

import it.polimi.se2018.networking.client.rmi.RMIClient;
import it.polimi.se2018.networking.client.socket.SockClient;
import it.polimi.se2018.utils.Printer;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for the Command Line Interface Client
 */
public class ClientCLI {
    public ClientCLI(){
        Printer printer = Printer.getInstance();
        Scanner scanner = new Scanner(System.in);
        int srChoice;
        printer.println("Choose between socket or rmi connection. [1: socket, 2: rmi]");
        do{
            try {
                srChoice = scanner.nextInt();
            }
            catch(InputMismatchException e){
                srChoice = 0;
            }
        }while(!(srChoice == 1 || srChoice == 2));

        if(srChoice == 1) new SockClient();
        if(srChoice == 2) new RMIClient();
    }

    public static void main (String args[]){
        new ClientCLI();
    }
}
