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
			System.out.println(e.getMessage());
		}
	}

	public void sendWallRemovalListenerSignal(int pos1X, int pos1Y, PositionWallLocation pos1Border, int pos2X, int pos2Y, PositionWallLocation pos2Border,
			int pos3X, int pos3Y, PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {

		out.println("removal-signal " + pos1X + " " + pos1Y + " " + pos1Border + " " + pos2X + " " + pos2Y + " " + pos2Border + " " + pos3X + " " + pos3Y + " " + pos3Border + " " + pos4X + " " + pos4Y + " " + pos4Border);
	}

	public void sendAvailableMove(int x, int y) {
		out.println("highlight " + x + " " + y);
	}

	public void sendRemoveWallDisplay(int x, int y, PositionWallLocation relativeBorder) {
		out.println("remove-wall-display " + x + " " + y + " " + relativeBorder);
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

	public void sendWallUpdate(int x, int y, PositionWallLocation border, int playerID) {
		out.println("wall " + x + " " + y + " " + border + " " + playerID);
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

    private void movePawn(String[] commands) {
        int x = Integer.parseInt(commands[1]);
        int y = Integer.parseInt(commands[2]);
        int playerID = Integer.parseInt(commands[3]);
        controller.movePawn(x, y, playerID);
    }

    private void removeWall(String[] commands) {
    	int topLeftX = Integer.parseInt(commands[1]);
        int topLeftY = Integer.parseInt(commands[2]);
        PositionWallLocation topLeftBorder = PositionWallLocation.valueOf(commands[3]);
        int pos2X = Integer.parseInt(commands[4]);
        int pos2Y = Integer.parseInt(commands[5]);
        PositionWallLocation pos2Border = PositionWallLocation.valueOf(commands[6]);
        int pos3X = Integer.parseInt(commands[7]);
        int pos3Y = Integer.parseInt(commands[8]);
        PositionWallLocation pos3Border = PositionWallLocation.valueOf(commands[9]);
        int pos4X = Integer.parseInt(commands[10]);
        int pos4Y = Integer.parseInt(commands[11]);
        PositionWallLocation pos4Border = PositionWallLocation.valueOf(commands[12]);
        int playerID = Integer.parseInt(commands[13]);
        controller.removeWall(topLeftX, topLeftY, topLeftBorder, pos2X, pos2Y, pos2Border, pos3X, pos3Y, pos3Border, pos4X, pos4Y, pos4Border, playerID);
    }

    private void placeWall(String[] commands) {
        int topLeftX = Integer.parseInt(commands[1]);
        int topLeftY = Integer.parseInt(commands[2]);
        PositionWallLocation border1 = PositionWallLocation.valueOf(commands[3]);
        int bottomLeftX = Integer.parseInt(commands[4]);
        int bottomLeftY = Integer.parseInt(commands[5]);
        PositionWallLocation border2 = PositionWallLocation.valueOf(commands[6]);
        int topRightX = Integer.parseInt(commands[7]);
        int topRightY = Integer.parseInt(commands[8]);
        PositionWallLocation border3 = PositionWallLocation.valueOf(commands[9]);
        int bottomRightX = Integer.parseInt(commands[10]);
        int bottomRightY = Integer.parseInt(commands[11]);
        PositionWallLocation border4 = PositionWallLocation.valueOf(commands[12]);
        int playerID = Integer.parseInt(commands[13]);
        controller.placeWall(topLeftX, topLeftY, border1, bottomLeftX, bottomLeftY, border2, topRightX, topRightY, border3, bottomRightX, bottomRightY, border4, playerID);
    }
}