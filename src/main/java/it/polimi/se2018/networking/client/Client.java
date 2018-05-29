package it.polimi.se2018.networking.client;

import it.polimi.se2018.view.cli.View;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private View view;
    private ServerConnection connection;
    private Socket socket;

    public Client(){
        Scanner input = new Scanner(System.in);
        System.out.println("Insert Server IP Address.");
        String serverIP = input.nextLine();
        System.out.println("Insert Server Port Number.");
        int port = input.nextInt();


        try {
            socket = new Socket(serverIP, port);
            view = new View();
            connection = new SocketServerConnection(socket);
            view.register(connection);
            new Thread(connection).start();
            //new Thread(view).start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Client();
    }
}
