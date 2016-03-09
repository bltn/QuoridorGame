import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient extends Thread {

	private Socket serverSocket;

	private PrintWriter out;
	private BufferedReader in;

	public GameClient() {}

	public void run() {
		while (true) {
			listenForServerInput();
		}
	}

	public void connectToServer(String IPAddress, int portAddress) {
		if (portAddress <= 65535) {
			try {
				System.out.println("Trying to establish a connection with the GameServer");
				serverSocket = new Socket(IPAddress, portAddress);
				out = new PrintWriter(serverSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				System.out.println("Successfully connected to the GameServer");
				initThread();
			} catch (Exception e) {
				System.out.println("Exception caught on the client side. connect to ");
				System.out.println(e.getMessage());
			}
		}
	}

	public void sendMessageToServer(String message) {
		System.out.println("Message to be sent: " + message);
		out.println(message);
	}

	public void listenForServerInput() {
		String fromServer;

		try {
			while ((fromServer = in.readLine()) != null) {
				// process input
			}
		} catch (Exception e) {
			System.out.println("Exception caught on the client side.");
			System.out.println(e.getMessage());
		}
	}

	private void initThread() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
