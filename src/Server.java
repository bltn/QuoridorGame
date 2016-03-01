import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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
        readInput();
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
            startGame();
        } catch (Exception e) {
            System.out.println("Unable to connect to the address: " + IPAddress + ":" + portAddress + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Listening for other players");
        System.out.println("Program will not respond until someone joins");
        listenForServerRequest();
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
        }
    }

    /**
     * Reads input from the data stream
     */
    public void readInput() {
        try {
            int x = dataInputStream.readInt();
            int y = dataInputStream.readInt();
            GameController.movePawn(x, y);
        } catch (IOException e) {
            System.out.println("Error reading input");
        }
    }

    /**
     * Sends the X position of a player
     * @param x X position of a player
     */
    public void sendXPosition(int x) {
        try {
            dataOutputStream.writeInt(x);
            System.out.println("X position sent");
        } catch (IOException e) {
            System.out.println("Error sending X position");
        }
    }

    /**
     * Sends the y position of a player
     * @param y y position of a player
     */
    public void sendYPosition(int y) {
        try {
            dataOutputStream.writeInt(y);
            System.out.println("Y position sent");
        } catch (IOException e) {
            System.out.println("Error sending Y position");
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


