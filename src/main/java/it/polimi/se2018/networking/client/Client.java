package it.polimi.se2018.networking.client;

import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.view.cli.View;

import java.io.IOException;
import java.io.PrintStream;
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

            try{
                insertName(socket);
            }
            catch(GameStartedException e){
                System.out.println("A game is already started");
                return;
            }
            catch(UserNameTakenException e){
                System.out.println("Username already taken");
                return;
            }

            connection.register(view);
            new Thread(connection).start();
            //new Thread(view).start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void insertName(Socket socket) throws GameStartedException, UserNameTakenException {
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            Scanner inFromSock = new Scanner(socket.getInputStream());
            Scanner inFromKey = new Scanner(System.in);
            String name;
            String response;

            System.out.println(inFromSock.nextLine());
            name = inFromKey.nextLine();
            out.println(name);
            out.flush();

            response = inFromSock.nextLine();

            if(response.equals("A game is already started")) throw new GameStartedException();
            if(response.equals("Username already taken")) throw new UserNameTakenException();
            System.out.println(response);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Client();
    }
}
