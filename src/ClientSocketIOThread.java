import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketIOThread extends Thread {

	private Socket socket = null;

	PrintWriter out;
	BufferedReader in;

	public ClientSocketIOThread(Socket socket) {
		super("ClientSocketInputThread");
		this.socket = socket;
		init();
	}

	public void run() {
		try {
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				String[] commands = inputLine.split("\\s+");
				if (commands[0].equals("hey!")) {
					out.println("Hey, thanks****");
				}
			}
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