package it.polimi.se2018.networking.client.rmi;

import it.polimi.se2018.controller.vcmessagecreator.VCMessageCreator;
import it.polimi.se2018.exceptions.GameStartedException;
import it.polimi.se2018.exceptions.ReconnectionException;
import it.polimi.se2018.exceptions.UserNameTakenException;
import it.polimi.se2018.networking.server.rmi.ClientConnectionAdapter;
import it.polimi.se2018.networking.server.rmi.RMIClientConnection;
import it.polimi.se2018.utils.Printer;
import it.polimi.se2018.utils.rmi.SockToRMIObserverAdapter;
import it.polimi.se2018.view.MVAbstractMessage;
import it.polimi.se2018.view.cli.ModelRepresentation;
import it.polimi.se2018.view.cli.View;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIClient {
    private static final Logger LOGGER = Logger.getLogger(RMIClient.class.getName());
    private RMIClientConnection serverService;
    private View view;
    private VCMessageCreator vcMessageCreator;
    private ModelRepresentation modelRep;
    private Printer out;


    public RMIClient(){
        Scanner in = new Scanner(System.in);
        out = new Printer();
        out.println("Insert player name. ");
        String playerName = in.nextLine();

        try {
            serverService = (RMIClientConnection)Naming.lookup("//localhost/sagradarmi");
            RMIServerConnection serverConnection = new RMIServerConnImpl(serverService);
            RMIServerConnection serverConnInt = (RMIServerConnection) UnicastRemoteObject.exportObject(serverConnection,0);
            serverService.handleRequest(playerName, serverConnInt); //throws exceptions
            modelRep = new ModelRepresentation();
            view = new View(playerName, modelRep);
            vcMessageCreator = new VCMessageCreator(view, modelRep);
            view.rawRegister(vcMessageCreator);
            view.register(new ServerConnectionAdapter(serverConnection));
            serverConnection.register(new SockToRMIObserverAdapter<>(view));
            serverService.checkEnoughPlayers();
        }
        catch(NotBoundException|MalformedURLException|RemoteException e){
            LOGGER.log(Level.SEVERE, e.toString());
        }
        catch(UserNameTakenException e){
            out.println("Username already taken.");
        }
        catch(GameStartedException e){
            out.println("Game already started. ");
        }
    }

    public static void main(String[] args){
        new RMIClient();
    }
}
