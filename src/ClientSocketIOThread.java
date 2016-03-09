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

	public ClientSocketIOThread(Socket socket) {
		super("ClientSocketInputThread");
		this.socket = socket;
		init();
	}

	public void run() {
		try {
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				// process input
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void sendMessage(String message) {
		out.println(message);
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