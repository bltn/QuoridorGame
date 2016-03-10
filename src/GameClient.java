import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.stage.Stage;

public class GameClient extends Thread {

	private Socket serverSocket;

	private PrintWriter out;
	private BufferedReader in;

	private NetworkedBoardGUI gui;

	private boolean guiCanBeLaunched;
	private boolean guiIsLaunched;

	private int playerID;
	private boolean idIsAssigned;

	public GameClient(GUI gui) {
		this.gui = (NetworkedBoardGUI) gui;
		guiCanBeLaunched = false;
		guiIsLaunched = false;
		idIsAssigned = false;
	}

	public void run() {
		while (true) {
			listenForServerInput();
		}
	}

	public void setPlayerID(int id) {
		if (!idIsAssigned) {
			this.playerID = id;
			idIsAssigned = true;
		}
	}

	public boolean guiCanBeLaunched() {
		return guiCanBeLaunched;
	}

	public GUI getGUI() {
		return gui;
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
				System.out.println("Exception caught on the client side.");
				System.out.println(e.getMessage());
			}
		}
	}

	public void sendMove(int x, int y) {
		out.println("move " + x + " " + y + " " + playerID);
	}

	public void sendMessageToServer(String message) {
		System.out.println("Message to be sent: " + message);
		out.println(message);
	}

	public boolean guiIsLaunched() {
		return guiIsLaunched;
	}

	public void setGUILaunched(boolean booted) {
		this.guiIsLaunched = booted;
	}

	public void listenForServerInput() {
		String fromServer;

		try {
			while ((fromServer = in.readLine()) != null) {
				String[] commands = fromServer.split("\\s+");
				if (commands[0].equals("bootGUI")) {
					guiCanBeLaunched = true;
				}
				else if (commands[0].equals("setID")) {
					int id = Integer.parseInt(commands[1]);
					setPlayerID(id);
					System.out.println("Client ID: " + playerID);
				}
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
