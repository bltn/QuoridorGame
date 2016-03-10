import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
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

	public int getPlayerID() {
		return playerID;
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
				serverSocket = new Socket(IPAddress, portAddress);
				out = new PrintWriter(serverSocket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				initThread();
			} catch (Exception e) {
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
				}
				else if (commands[0].equals("stats")) {
					int moveCount = Integer.parseInt(commands[1]);
					int wallCount = Integer.parseInt(commands[2]);
					int playerID = Integer.parseInt(commands[3]);
					gui.updatePlayerMoveCount(moveCount, playerID);
					gui.updatePlayerWallCount(wallCount, playerID);
				}
				else if (commands[0].equals("pawn")) {
					int x = Integer.parseInt(commands[1]);
					int y = Integer.parseInt(commands[2]);
					int playerID = Integer.parseInt(commands[3]);
					Platform.runLater(new Runnable(){
						@Override
						public void run() {
							gui.updatePlayerPawnPosition(x, y, playerID);
						}
					});
				}
				else if (commands[0].equals("currentPlayer")) {
					int playerID = Integer.parseInt(commands[1]);
					gui.updateActivePlayer(playerID);
				}
				else if (commands[0].equals("error")) {
					StringBuilder message = new StringBuilder();
					for (int i = 1; i < commands.length; i++) {
						message.append(commands[i] + " ");
					}
					gui.displayErrorMessage(message.toString());
				}
			}
		} catch (Exception e) {
			System.out.println("GC");
			System.out.println(e.getMessage());
		}
	}

	private void initThread() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
