package TCPServerClient;

import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import GameEngine.*;

public class Server {
    private static int portNumber;
    private String guiOption;

    public Server() {
        // choose a type of vehicles representaion
        guiOption = "";
        System.out.println(
                "You can choose emoji representation of cars or plain in case it is not supported  by your device. Emoji or plain?");
        try (Scanner scan = new Scanner(System.in)) {
            guiOption = scan.nextLine();
        } catch (Exception e) {
            System.out.println("Reading GUI type broke:" + e.toString());
        }
        communication();
    }

    public void communication() {
        try (
                // connect to a client
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));) {

            String inputLine;
            ArrayList<String> outputLine;
            //authenticate the user
            out.println("Enter your username to startthe game");
            AuthProtocol aup = new AuthProtocol();
            while ((inputLine = in.readLine()) != null) {
                outputLine = aup.processInput(inputLine);
                outputLine.stream().forEach(s -> out.println(s));
                if (outputLine.contains("Welcome!;"))
                    break;
            }
            //start a game
            GameController gc = GameController.getInstance(guiOption, 1);// <-Signleton game class
            //start conversation with client
            Protocol kkp = new Protocol(gc);
            do {
                outputLine = kkp.processInput(inputLine);
                outputLine.stream().forEach(s -> out.println(s));
            } while ((inputLine = in.readLine()) != null || gc.getPlayerController().checkStatus());
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }
        portNumber = Integer.parseInt(args[0]);
        Server myServer = new Server();

    }
}
