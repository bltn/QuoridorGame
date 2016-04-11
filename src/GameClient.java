import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
import javafx.scene.control.Alert;
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

    private Alert alert;

	public GameClient(GUI gui) {
		this.gui = (NetworkedBoardGUI) gui;
		guiCanBeLaunched = false;
		guiIsLaunched = false;
		idIsAssigned = false;
        alert = new Alert(Alert.AlertType.CONFIRMATION, "");
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
                showAlert("Connected to server");
			} catch (Exception e) {
				System.out.println(e.getMessage());
                showAlert("Error connecting to server");
			}
		}
	}

	public void sendMove(int x, int y) {
		out.println("move " + x + " " + y + " " + playerID);
	}

	public void sendWallMove(int topLeftX, int topLeftY, PositionWallLocation topLeftBorder, int pos2X, int pos2Y, PositionWallLocation pos2Border,
			int pos3X, int pos3Y, PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {

		out.println("wall " + topLeftX + " " + topLeftY + " " + topLeftBorder + " " + pos2X + " " + pos2Y + " " + pos2Border + " " + pos3X + " " +
			pos3Y + " " + pos3Border + " " + pos4X + " " + pos4Y + " " + pos4Border + " " + playerID);
	}

	public void sendWallRemoval(int topLeftX, int topLeftY, PositionWallLocation topLeftBorder, int pos2X, int pos2Y, PositionWallLocation pos2Border,
			int pos3X, int pos3Y, PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {

		out.println("remove-wall " + topLeftX + " " + topLeftY + " " + topLeftBorder + " " + pos2X + " " + pos2Y + " " + pos2Border + " " +
		    pos3X + " " + pos3Y + " " + pos3Border + " " + pos4X + " " + pos4Y + " " + pos4Border + " " + playerID);
	}

	public void requestCurrentPlayerAvailableMoves() {
		out.println("available");
	}

	public void requestInitialPlayerPawnPositions() {
		out.println("start-coordinates");
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
					updatePlayerStats(commands);
				}
				else if (commands[0].equals("pawn")) {
					updatePlayerPawnPosition(commands);
				}
				else if (commands[0].equals("currentPlayer")) {
					updateCurrentPlayer(commands);
				}
				else if (commands[0].equals("highlight")) {
					highlightAvailablePositions(commands);
				}
				else if (commands[0].equals("error")) {
					displayErrorMessage(commands);
				}
				else if (commands[0].equals("wall")) {
					updateWallPosition(commands);
				}
				else if (commands[0].equals("reset")) {
                    gui.resetWalls();
                }
				else if (commands[0].equals("removal-signal")) {
					addWallRemovalListener(commands);
				}
				else if (commands[0].equals("coordinate")) {
					int player1X = Integer.parseInt(commands[1]);
					int player1Y = Integer.parseInt(commands[2]);
					int player2X = Integer.parseInt(commands[3]);
					int player2Y = Integer.parseInt(commands[4]);
					gui.setInitialPawnPositions(player1X, player1Y, player2X, player2Y);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private void addWallRemovalListener(String[] commands) {
		int pos1X = Integer.parseInt(commands[1]);
		int pos1Y = Integer.parseInt(commands[2]);
		PositionWallLocation pos1Border = PositionWallLocation.valueOf(commands[3]);

		int pos2X = Integer.parseInt(commands[4]);
		int pos2Y = Integer.parseInt(commands[5]);
		PositionWallLocation pos2Border = PositionWallLocation.valueOf(commands[6]);

		int pos3X = Integer.parseInt(commands[7]);
		int pos3Y = Integer.parseInt(commands[8]);
		PositionWallLocation pos3Border = PositionWallLocation.valueOf(commands[9]);

		int pos4X = Integer.parseInt(commands[10]);
		int pos4Y = Integer.parseInt(commands[11]);
		PositionWallLocation pos4Border = PositionWallLocation.valueOf(commands[12]);

		gui.addWallRemovalListener(pos1X, pos1Y, pos1Border, pos2X, pos2Y, pos2Border, pos3X, pos3Y, pos3Border, pos4X, pos4Y, pos4Border);
	}

    private void updateWallPosition(String[] commands) {
        int x = Integer.parseInt(commands[1]);
        int y = Integer.parseInt(commands[2]);
        PositionWallLocation border = PositionWallLocation.valueOf(commands[3]);
        int playerID = Integer.parseInt(commands[4]);
        gui.displayWall(x, y, border, playerID);
    }

    private void displayErrorMessage(String[] commands) {
        StringBuilder message = new StringBuilder();
        for (int i = 1; i < commands.length; i++) {
            message.append(commands[i] + " ");
        }
        gui.displayErrorMessage(message.toString());
    }

    private void highlightAvailablePositions(String[] commands) {
        int x = Integer.parseInt(commands[1]);
        int y = Integer.parseInt(commands[2]);
        gui.highlightPositionAvailability(x, y);
    }

    private void updateCurrentPlayer(String[] commands) {
        int playerID = Integer.parseInt(commands[1]);
        gui.updateActivePlayer(playerID);
    }

    private void updatePlayerPawnPosition(String[] commands) {
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

    private void updatePlayerStats(String[] commands) {
        int moveCount = Integer.parseInt(commands[1]);
        int wallCount = Integer.parseInt(commands[2]);
        int playerID = Integer.parseInt(commands[3]);
        gui.updatePlayerMoveCount(moveCount, playerID);
        gui.updatePlayerWallCount(wallCount, playerID);
    }

    private void initThread() {
		Thread thread = new Thread(this);
		thread.start();
	}

    private void showAlert(String alertText) {
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
