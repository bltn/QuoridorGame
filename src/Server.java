import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Server extends Thread {
	private ServerSocket serverSocket;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	private boolean accepted;
	
    public Server() throws IOException {
        accepted = false;
    }


    public void run() {
    }

    public boolean connect(String IPAddress, int portAddress){
        try {
            socket = new Socket(IPAddress, portAddress);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
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
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            System.out.println("Player has joined the server");
            System.out.println("Starting game");
            accepted = true;
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        if (accepted) {
        } else {
        }
    }
}


