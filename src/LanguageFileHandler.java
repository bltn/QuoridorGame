import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class LanguageFileHandler {

	private static String filePath;
	private static BufferedReader br;

	private static String start;
	private static String multiplayer;
	private static String quit;
	private static String moves;
	private static String player2;
	private static String player1;
	private static String walls;
	private static String joinGame;
	private static String hint;
	private static String createServer;
	private static String multiplayerInstructions;
	private static String connect;

	public static void setLanguage(String language) {
		switch (language) {
			case "English": {
				filePath = "src/lang/English.txt";
				break;
			}
			case "French": {
				filePath = "src/lang/French.txt";
				break;
			}
			case "Spanish": {
				filePath = "src/lang/Spanish.txt";
				break;
			}
			case "Chinese": {
				filePath = "src/lang/Chinese.txt";
				break;
			}
			default: {
				filePath = "src/lang/English.txt";
				break;
			}
		}

		FileInputStream fs;
		try {
			fs = new FileInputStream(filePath);
			br = new BufferedReader(new InputStreamReader(fs));
			start = br.readLine();
			multiplayer = br.readLine();
			quit = br.readLine();
			moves = br.readLine();
			player1 = br.readLine();
			player2 = br.readLine();
			walls = br.readLine();
			hint = br.readLine();
			joinGame = br.readLine();
			multiplayerInstructions = br.readLine();
			createServer = br.readLine();
			connect = br.readLine();
		} catch (FileNotFoundException e) {
			SystemLogger.logError(e.getMessage());
		} catch (IOException e) {
			SystemLogger.logError(e.getMessage());
		}
	}

	public static String getLocalMode()
	{
		if (start != null) {
			return start;
		} else {
			return "Start";
		}
	}

	public static String getMultiplayer()
	{
		if (multiplayer != null) {
			return multiplayer;
		} else {
			return "Multiplayer";
		}
	}

	public static String getQuit()
	{
		if (quit != null) {
			return quit;
		} else {
			return "Quit";
		}
	}

	public static String getMoves()
	{
		if (moves != null) {
			return moves;
		} else {
			return "Moves";
		}
	}

	public static String getPlayer1()
	{
		if (player1 != null) {
			return player1;
		} else {
			return "Player 1";
		}
	}

	public static String getPlayer2()
	{
		if (player2 != null) {
			return player2;
		} else {
			return "Player 2";
		}
	}

	public static String getWalls()
	{
		if (walls != null) {
			return walls;
		} else {
			return "Walls";
		}
	}

	public static String getHint()
	{
		if (hint != null) {
			return hint;
		} else {
			return "Hint";
		}
	}

	public static String getJoinGame()
	{
		if (joinGame != null) {
			return joinGame;
		} else {
			return "Join game";
		}
	}

	public static String getMP()
	{
		if (multiplayerInstructions != null) {
			return multiplayerInstructions;
		} else {
			return "Enter the IP and port address for your machine";
		}
	}

	public static String getCreateServer()
	{
		if (createServer != null) {
			return createServer;
		} else {
			return "Create server";
		}
	}

	public static String getConnect()
	{
		if (connect != null) {
			return connect;
		} else {
			return "Connect to game";
		}
	}

}
