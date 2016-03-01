import javafx.scene.paint.Color;
import sun.misc.IOUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;
	private boolean accepted;
	
    public Server() throws IOException {
        accepted = false;
    }


    /**
     * Create a separate thread to read input
     */
    public void run() {
        while(accepted) {
            readInput();
        }
    }

    /**
     * Connect to a given server and start the game for the client if connection was successful
     * @param IPAddress IP address of the server
     * @param portAddress Port address of the server
     * @return true if the connection attempt to the server was succesful
     */
    public boolean connect(String IPAddress, int portAddress){
        try {
            socket = new Socket(IPAddress, portAddress);
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            accepted = true;
            System.out.println("Successfully connected to the server");
            System.out.println("Starting game");
            startGame();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to connect to the address: " + IPAddress + ":" + portAddress + " | Starting a server");
            return false;
        }
    }

    /**
     * Create a server
     * @param IPAddress IP address of the server
     * @param portAddress Port address of the server
     */
    public void initializeServer(String IPAddress, int portAddress){
        try {
            serverSocket = new ServerSocket(portAddress, 8, InetAddress.getByName(IPAddress));
            System.out.println("Server Created");
            System.out.println("Listening for other players");
            System.out.println("Program will not respond until someone joins");
            listenForServerRequest();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error creating server");
            System.out.println("Is the Address in use?");
        }
    }

    /**
     * Listens for any incoming connection requests after creating a server and start the game for the client after
     * a player connects to the server
     */
    public void listenForServerRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(socket.getInputStream());
            System.out.println("Player has joined the server");
            System.out.println("Starting game");
            accepted = true;
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error when listening for requests");
        }
    }

    /**
     * Reads input from the data stream and executes commands from the input
     */
    public void readInput() {
        try {
            // Read input from the server
            int length = dataInputStream.readInt();
            byte[] data = new byte[length];
            dataInputStream.readFully(data);
            String command = new String(data, "UTF-8");
            // Split the input into an array of words
            String[] commands = command.split("\\s+");
            if (commands[0].equals("move")) {
                int x = Integer.parseInt(commands[1]);
                int y = Integer.parseInt(commands[2]);
                GameController.movePawn(x, y);
            }
            else if (commands[0].equals("wall")) {
                int x1 = Integer.parseInt(commands[1]);
                int y1 = Integer.parseInt(commands[2]);
                int x2 = Integer.parseInt(commands[4]);
                int y2 = Integer.parseInt(commands[5]);
                int x3 = Integer.parseInt(commands[7]);
                int y3 = Integer.parseInt(commands[8]);
                int x4 = Integer.parseInt(commands[10]);
                int y4 = Integer.parseInt(commands[11]);
                PositionWallLocation b1 = PositionWallLocation.valueOf(commands[3]);
                PositionWallLocation b2 = PositionWallLocation.valueOf(commands[6]);
                PositionWallLocation b3 = PositionWallLocation.valueOf(commands[9]);
                PositionWallLocation b4 = PositionWallLocation.valueOf(commands[12]);
                GameController.placeWall(x1, y1, b1, x2, y2, b2, x3, y3, b3, x4, y4, b4);
            }
            System.out.println(command);
            System.out.println("Input read");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading input");
        }
    }

    /**
     * Sends the position of the pawn to the server
     * @param x x position of the pawn
     * @param y y position of the pawn
     */
    public void sendPawnPosition(int x, int y) {
        try {
            String command = new String("move " + x + " " + y);
            byte[] data = command.getBytes("UTF-8");
            dataOutputStream.writeInt(data.length);
            dataOutputStream.write(data);
            System.out.println("Pawn position sent");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending pawn position");
        }
    }


    public void sendWallPosition(int pos1X, int pos1Y, PositionWallLocation pos1Border, int pos2X, int pos2Y,
                                 PositionWallLocation pos2Border, int pos3X, int pos3Y, PositionWallLocation pos3Border,
                                 int pos4X, int pos4Y, PositionWallLocation pos4Border) {
        try {
            String command = new String("wall " + pos1X + " " + pos1Y + " " + pos1Border + " " + pos2X + " " + pos2Y +
                    " " + pos2Border + " " + pos3X + " " + pos3Y + " " + pos3Border + " " + pos4X + " " + pos4Y + " "
                    + pos4Border);
            byte[] data = command.getBytes("UTF-8");
            dataOutputStream.writeInt(data.length);
            dataOutputStream.write(data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.out.println("Error encoding wall position command");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending wall position");
        }
    }

    /**
     * Starts the game if two players are connected to the server
     */
    public void startGame() {
        if (accepted) {
            GameController.startGame();
        } else {
            System.out.println("Need a player to join");
        }
    }

}


