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
					movePawn(commands);
				}
				else if (commands[0].equals("available")) {
					controller.showCurrentPlayerMoves();
				}
				else if (commands[0].equals("wall")) {
					placeWall(commands);
				}
				else if (commands[0].equals("start-coordinates")) {
					out.println("coordinate " + controller.getPlayer1X() + " " + controller.getPlayer1Y() + " " + controller.getPlayer2X() + " " + controller.getPlayer2Y());
				}
				else if (commands[0].equals("remove-wall")) {
					removeWall(commands);
				}
			}
		} catch (IOException e) {
			SystemLogger.logError(e.getMessage());
		}
	}

	public void sendAvailableMove(int x, int y) {
		out.println("highlight " + x + " " + y);
	}

	public void sendRemoveWallDisplay(int topLeftX, int topLeftY, WallPlacement orientation) {
		out.println("remove-wall-display " + topLeftX + " " + topLeftY + " " + orientation);
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

	public void sendWallUpdate(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
		out.println("wall " + topLeftX + " " + topLeftY + " " + orientation + " " + playerID);
	}

	public void sendResetWalls() {
        out.println("reset");
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
			SystemLogger.logError(e.getMessage());
		}
	}

	private void init() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			SystemLogger.logError(e.getMessage());
		}
	}

    private void movePawn(String[] commands) {
        int x = Integer.parseInt(commands[1]);
        int y = Integer.parseInt(commands[2]);
        int playerID = Integer.parseInt(commands[3]);
        controller.movePawn(x, y, playerID);
    }

    private void removeWall(String[] commands) {
    	int topLeftX = Integer.parseInt(commands[1]);
        int topLeftY = Integer.parseInt(commands[2]);
        WallPlacement orientation = WallPlacement.valueOf(commands[3]);
        int playerID = Integer.parseInt(commands[4]);
        controller.removeWall(topLeftX, topLeftY, orientation, playerID);
    }

    private void placeWall(String[] commands) {
        int topLeftX = Integer.parseInt(commands[1]);
        int topLeftY = Integer.parseInt(commands[2]);
        WallPlacement orientation = WallPlacement.valueOf(commands[3]);
        int playerID = Integer.parseInt(commands[4]);
        controller.placeWall(topLeftX, topLeftY, orientation, playerID);
    }
}