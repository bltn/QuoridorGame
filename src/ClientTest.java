
public class ClientTest {

	public static void main(String[] args) {
		GameClient client = new GameClient();
		client.connectToServer("localhost", 33333);
		client.sendMessageToServer("hey! you");
	}
}
