import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Ben Lawton
 * @author Junaid Rasheed
 *
 * ClientSocketIOThread runs a thread dedicated to listening for input from and sending input to a client socket
 * Each ClientSocketIOThread has its own dedicated client...
 * ...for a game with n clients, there needs to be n ClientSocketIOThread instances
 *
 * When it receives input from its client, it processes it and calls the appropriate controller action. Its
 * send*() methods are called by the controller to send messages to the client
 *
 */

public class ClientSocketIOThread extends Thread {

	// Client socket
	private Socket socket;

	// Client input and output streams
	private PrintWriter out;
	private BufferedReader in;

	private NetworkedGameController controller;

	public ClientSocketIOThread(Socket socket, NetworkedGameController controller) {
		super("ClientSocketInputThread");
		this.socket = socket;
		this.controller = controller;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			SystemLogger.logError(e.getMessage());
		}
	}

	/**
	 * Listen for and process input from the client socket
	 */
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
					String coordinates = "coordinate " + controller.getPlayer1X() + " " + controller.getPlayer1Y() + " " + controller.getPlayer2X() + " " + controller.getPlayer2Y();
					if(controller.getPlayer3IO() != null) {
						coordinates +=  " " + controller.getPlayer3X() + " " + controller.getPlayer3Y() + " " + controller.getPlayer4X() + " " + controller.getPlayer4Y();
					}
					out.println(coordinates);
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