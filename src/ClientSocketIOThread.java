import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketIOThread extends Thread {

	private Socket socket = null;
	private Socket sisterSocket = null;

	private PrintWriter out;
	private BufferedReader in;

	private PrintWriter sisterOut;

	private NetworkedGameController controller;

	public ClientSocketIOThread(Socket socket, NetworkedGameController controller) {
		super("ClientSocketInputThread");
		this.socket = socket;
		this.controller = controller;
		init();
	}

	public void run() {
		try {
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				String[] commands = inputLine.split("\\s+");
				if (commands[0].equals("move")) {
					int x = Integer.parseInt(commands[1]);
					int y = Integer.parseInt(commands[2]);
					int playerID = Integer.parseInt(commands[3]);
					controller.movePawn(x, y, playerID);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendMessage(String message) {
		out.println(message);
	}

	public void sendStatsUpdate(int moveCount, int wallCount, int playerID) {
		out.println("stats " + moveCount + " " + wallCount + " " + playerID);
	}

	public void sendPawnUpdate(int x, int y, int playerID) {
		out.println("pawn " + x + " " + y + " " + playerID);
	}

	public void sendErrorMessage(String message) {
		out.println("error " + message);
	}

	public void sendCurrentPlayerGUIUpdate(int playerID) {
		out.println("currentPlayer " + playerID);
	}

	public Socket getSocket() {
		return this.socket;
	}

	public void setSisterSocket(Socket socket) {
		this.sisterSocket = socket;

		try {
			this.sisterOut = new PrintWriter(sisterSocket.getOutputStream(), true);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void init() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}