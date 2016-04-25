import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class GameServer {

	private ServerSocket serverSocket;

	private ClientSocketIOThread player1IOThread;
	private ClientSocketIOThread player2IOThread;
	private ClientSocketIOThread player3IOThread;
	private ClientSocketIOThread player4IOThread;

	private NetworkedGameController controller;
	private int numberOfPlayers;

    private Alert alert;

	public GameServer(NetworkedGameController controller, int numberOfPlayers) {
		this.controller = controller;
		this.numberOfPlayers = numberOfPlayers;
        alert = new Alert(Alert.AlertType.CONFIRMATION, "");
    }

	public void initialiseServer(String IPAddress, int portAddress) {
		if (portAddress <= 65535) {
			try {
				serverSocket = new ServerSocket(portAddress, 8, InetAddress.getByName(IPAddress));
				SystemLogger.logInfo("Server created");
				listenForConnectionRequests();
			} catch (Exception e) {
				SystemLogger.logError(e.getMessage());
                showAlert("Error creating server. Please restart and try again.");
			}
		}
	}

	public void listenForConnectionRequests() {
		try {
            showAlert("Waiting for players to join");
			int socketCount = 0;
			while (socketCount < numberOfPlayers) {
				if (socketCount == 0) {
					player1IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
                    SystemLogger.logInfo("Client socket # 1 I/O thread booted up");
                    SystemLogger.logInfo("Player 1 joined game");
                    showAlert("Player 1 has joined");
				}
				else if (socketCount == 1) {
					player2IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
                    SystemLogger.logInfo("Client socket # 2 I/O thread booted up");
                    SystemLogger.logInfo("Player 2 joined game");
                    showAlert("Player 2 has joined");
				}
				else if (socketCount == 2) {
					player3IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
                    SystemLogger.logInfo("Client socket # 3 I/O thread booted up");
                    SystemLogger.logInfo("Player 3 joined game");
					showAlert("Player 3 has joined");
				}
				else if (socketCount == 3) {
					player4IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
                    SystemLogger.logInfo("Client socket # 4 I/O thread booted up");
                    SystemLogger.logInfo("Player 4 joined game");
					showAlert("Player 4 has joined");
				}
				socketCount++;
			}
			player1IOThread.setSisterSocket(player2IOThread.getSocket());
			player2IOThread.setSisterSocket(player1IOThread.getSocket());
			controller.setPlayer1IO(player1IOThread);
			controller.setPlayer2IO(player2IOThread);
			initThreads();
			player1IOThread.sendMessage("bootGUI");
			player2IOThread.sendMessage("bootGUI");
			player1IOThread.sendMessage("setID " + 1);
			player2IOThread.sendMessage("setID " + 2);
			if (numberOfPlayers == 4) {
				controller.setPlayer3IO(player3IOThread);
				controller.setPlayer4IO(player4IOThread);
				player3IOThread.sendMessage("bootGUI");
				player4IOThread.sendMessage("bootGUI");
				player3IOThread.sendMessage("setID " + 3);
				player4IOThread.sendMessage("setID " + 4);
			}
			SystemLogger.logInfo("Game begun");
            showAlert("Game has begun");
		} catch (IOException e) {
            showAlert("There was a problem with a client joining the server");
			SystemLogger.logError(e.getMessage());
		}
	}

	private void initThreads() {
		player1IOThread.start();
		player2IOThread.start();
		if (numberOfPlayers == 4) {
			player3IOThread.start();
			player4IOThread.start();
		}
	}

    private void showAlert(String alertText) {
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
