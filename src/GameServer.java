import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

	private ServerSocket serverSocket;

	private ClientSocketIOThread player1IOThread;
	private ClientSocketIOThread player2IOThread;

	private NetworkedGameController controller;

    private Alert alert;

	public GameServer(NetworkedGameController controller) {
		this.controller = controller;
        alert = new Alert(Alert.AlertType.CONFIRMATION, "");
    }

	public void initialiseServer(String IPAddress, int portAddress) {
		if (portAddress <= 65535) {
			try {
				serverSocket = new ServerSocket(portAddress, 8, InetAddress.getByName(IPAddress));
				System.out.println("Server created");
				listenForConnectionRequests();
			} catch (Exception e) {
				e.printStackTrace();
                showAlert("Error creating server. Please restart and try again.");
				System.out.println("There was an error creating the server");
			}
		}
	}

	public void listenForConnectionRequests() {
		try {
            showAlert("Waiting for players to join");
			int socketCount = 0;
			while (socketCount < 2) {
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
	}

    private void showAlert(String alertText) {
        alert.setContentText(alertText);
        alert.showAndWait();
    }
}
