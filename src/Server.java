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


    public void run() {
        readInput();
    }

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

    public void readInput() {
        try {
            int x = dataInputStream.readInt();
            int y = dataInputStream.readInt();
            GameController.movePawn(x, y);
        } catch (IOException e) {
            System.out.println("Error reading input");
        }
    }

    public void sendXPosition(int x) {
        try {
            dataOutputStream.writeInt(x);
            System.out.println("X position sent");
        } catch (IOException e) {
            System.out.println("Error sending X position");
        }
    }

    public void sendYPosition(int y) {
        try {
            dataOutputStream.writeInt(y);
            System.out.println("Y position sent");
        } catch (IOException e) {
            System.out.println("Error sending Y position");
        }
    }

    public void startGame() {
        if (accepted) {
            GameController.startGame();
        } else {
            System.out.println("Need a player to join");
        }
    }
}


