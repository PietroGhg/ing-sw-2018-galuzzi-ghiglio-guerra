package it.polimi.se2018.networking.server;

import it.polimi.se2018.controller.Controller;
import it.polimi.se2018.model.table.Model;
import it.polimi.se2018.networking.server.rmi.RMIServer;
import it.polimi.se2018.networking.server.socket.SocketServer;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class which contains the main method used to start the server, instantiates a new SocketServer and a new RMIServer
 * @author Pietro Ghiglio
 */
public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    private static final int RMIPORT = 1099;

    public static void main (String[] args){
        Model model;
        Controller controller;
        Scanner scanner = new Scanner(System.in);
        int sockPort;
        int turnD;
        int timerD;
        LOGGER.log(Level.INFO, "Insert socket port number.");
        sockPort = scanner.nextInt();

        LOGGER.log(Level.INFO, "Insert connection timer duration. ");
        timerD = scanner.nextInt();
        LOGGER.log(Level.INFO, "Insert turn duration. ");
        turnD = scanner.nextInt();

        model = new Model();
        controller = new Controller(model, timerD, turnD);

        new SocketServer(model, controller, sockPort);
        new RMIServer(model, controller, RMIPORT);
    }
}
