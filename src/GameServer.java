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

	public GameServer() {}

	public void initialiseServer(String IPAddress, int portAddress) {
		if (portAddress <= 65535) {
			try {
				serverSocket = new ServerSocket(portAddress, 8, InetAddress.getByName(IPAddress));
				System.out.println("Server created");
				listenForConnectionRequests();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("There was an error creating the server");
			}
		}
	}

	public void listenForConnectionRequests() {
		try {
			int socketCount = 0;
			while (socketCount < 2) {
				if (socketCount == 0) {
					player1IOThread = new ClientSocketIOThread(serverSocket.accept());
					System.out.println("Client socket # 1 I/O thread booted up");
				}
				else if (socketCount == 1) {
					player2IOThread = new ClientSocketIOThread(serverSocket.accept());
					System.out.println("Client socket # 2 I/O thread booted up");
				}
				socketCount++;
			}
			initThreads();
		} catch (IOException e) {
			System.out.println("IOException caught on the client side.");
			System.out.println(e.getMessage());
		}
	}

	private void initThreads() {
		player1IOThread.start();
		player2IOThread.start();
	}
}
