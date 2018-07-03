package it.polimi.se2018.networking.client.socket;

import it.polimi.se2018.controller.vcmessagecreator.VCMessageCreator;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.client.ServerConnection;
import it.polimi.se2018.utils.Printer;
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.cli.View;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for the Socket client
 */
public class SockClient {
    private View view;
    private Printer outToScreen = new Printer();
    private static final Logger LOGGER = Logger.getLogger(SockClient.class.getName());
    private VCMessageCreator vcMessageCreator;
    private ModelRepresentation modelRep;
    private ServerConnection connection;
    private Socket socket;

    public SockClient(){
        Scanner input = new Scanner(System.in);
        outToScreen.println("Insert Server IP Address.");
        String serverIP = input.nextLine();
        outToScreen.println("Insert Server Port Number.");
        int port = input.nextInt();


        try {
            socket = new Socket(serverIP, port);
            connection = new SocketServerConnection(socket);

            try{
                modelRep = new ModelRepresentation();
                view = new View(insertName(socket), modelRep);
                vcMessageCreator = new VCMessageCreator(view, modelRep);
            }
            catch(GameStartedException e){
                outToScreen.println("A game is already started");
                return;
            }
            catch(UserNameTakenException e){
                outToScreen.println("Username already taken");
                return;
            }

            connection.register(view);
            view.register(connection);
            view.rawRegister(vcMessageCreator);
            SocketServerConnection sockConn = (SocketServerConnection) connection;
            new Thread(sockConn).start();
        }
        catch(IOException e){
            LOGGER.log(Level.SEVERE, e.toString());
        }
    }

    private String insertName(Socket socket) throws GameStartedException, UserNameTakenException, IOException {

        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner inFromSock = new Scanner(socket.getInputStream());
        Scanner inFromKey = new Scanner(System.in);
        String name;
        String response;

        outToScreen.println(inFromSock.nextLine());
        name = inFromKey.nextLine();
        out.println(name);
        out.flush();
        response = inFromSock.nextLine();
        if(response.equals("A game is already started")) throw new GameStartedException();
        if(response.equals("Username already taken")) throw new UserNameTakenException();
        outToScreen.println(response);
        return name;
    }

    public static void main(String[] args){
        new SockClient();
    }
}
