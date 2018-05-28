package it.polimi.se2018.networking.client;

import it.polimi.se2018.view.cli.View;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private View view;
    private ServerConnection connection;

    public Client(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert Server IP Address.");
        String serverIP = input.nextLine();
        System.out.println("Insert Server Port Number.");
        int port = input.nextInt();
        try {
            Socket socket = new Socket(serverIP, port);
            connection = new SocketServerConnection(socket);
            view.register(connection);
            new Thread(connection).start();
            //new Thread(view).start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
