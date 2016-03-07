import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer extends Thread {

	private ServerSocket serverSocket;

	private BufferedReader player1Input;
	private PrintWriter player1Output;

	private BufferedReader player2Input;
	private PrintWriter player2Output;

	public GameServer() {}

	public void run() {
		while (true) {
			listenForClientInput();
		}
	}

	public void initialiseServer(String IPAddress, int portAddress) {
		if (portAddress <= 65535) {
			try {
				serverSocket = new ServerSocket(portAddress, 8, InetAddress.getByName(IPAddress));
				System.out.println("Server created");
				listenForConnectionRequests();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was an error creating the server");
			}
		}
	}

	public void listenForConnectionRequests() {
		try {
			int socketCount = 0;
			while (socketCount < 2) {
				System.out.println("Trying to connect to a client socket");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client socket opened");
				if (socketCount == 0 && clientSocket != null) {
					player1Output = new PrintWriter(clientSocket.getOutputStream(), true);
					player1Input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					System.out.println("Client socket # 1 I/O streams opened");
				}
				else if (socketCount == 1 && clientSocket != null) {
					player2Output = new PrintWriter(clientSocket.getOutputStream(), true);
					player2Input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					System.out.println("Client socket # 2 I/O streams opened");
				}
				socketCount++;
			}
			initThread();
		} catch (IOException e) {
			System.out.println("IOException caught on the client side.");
			System.out.println(e.getMessage());
		}
	}

	public void listenForClientInput() {
		try {
			String inputLine;

			while ((inputLine = player1Input.readLine()) != null) {
				String[] commands = inputLine.split("\\s+");
				if (commands[0].equals("hey!")) {
					player1Output.println("Thanks for that, player 1!");
					player2Output.println("Thanks for that, player 2!");
				}
			}
		} catch (IOException e) {
			System.out.println("IOException caught on the client side.");
			System.out.println(e.getMessage());
		}
	}

	private void initThread() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
