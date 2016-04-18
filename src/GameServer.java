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
				System.out.println("Server created");
				listenForConnectionRequests();
			} catch (Exception e) {
				e.printStackTrace();
                showAlert("Error creating server");
				System.out.println("There was an error creating the server");
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
                    showAlert("Player 1 has joined");
					System.out.println("Client socket # 1 I/O thread booted up");
				}
				else if (socketCount == 1) {
					player2IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
                    showAlert("Player 2 has joined");
					System.out.println("Client socket # 2 I/O thread booted up");
				}
				else if (socketCount == 2) {
					player3IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
					showAlert("Player 3 has joined");
					System.out.println("Client socket # 3 I/O thread booted up");
				}
				else if (socketCount == 3) {
					player4IOThread = new ClientSocketIOThread(serverSocket.accept(), controller);
					showAlert("Player 4 has joined");
					System.out.println("Client socket # 4 I/O thread booted up");
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
            showAlert("Game has begun");
		} catch (IOException e) {
            showAlert("There was a problem with a client joining the server");
			System.out.println("IOException caught on the client side.");
			System.out.println(e.getMessage());
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
