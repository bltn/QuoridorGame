public class ConnectionGUI {

	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.initialiseServer("localhost", 33333);
	}

}
