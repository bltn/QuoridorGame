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

	private BufferedReader in;
	private PrintWriter out;

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
			System.out.println("Trying to connect to a client socket");
			Socket clientSocket = serverSocket.accept();
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			System.out.println("Client socket opened");
			initThread();
		} catch (IOException e) {
			System.out.println("IOException caught on the client side.");
			System.out.println(e.getMessage());
		}
	}

	public void listenForClientInput() {
		try {
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				String[] commands = inputLine.split("\\s+");
				if (commands[0].equals("print")) {
					System.out.println("Printing, printing " + "second: " + commands[1]);
				}
				else {
					System.out.println("It wasn't print.");
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
