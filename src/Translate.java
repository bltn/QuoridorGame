import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class Translate {

	private static String filePath;
	private static BufferedReader br;

	private static String start;
	private static String back;
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
	private static String settings;
	private static String blockingMove;
	private static String invalidMove;
	private static String noWalls;
	private static String positionOccupied;
	private static String practiseMode;
	private static String standardRules;
	private static String challengeRules;
	private static String twoPlayerStandard;
	private static String fourPlayerStandard;
	private static String twoPlayerChallenge;
	private static String fourPlayerChallenge;
	private static String availableMoves;
	private static String gameOver;
	private static String newGame;
	private static String connectToTwoPlayerGame;
	private static String connectToFourPlayerGame;
	private static String gameModes;
	private static String chooseGameMode;
	private static String mode;

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
			settings = br.readLine();
			blockingMove = br.readLine();
			invalidMove = br.readLine();
			noWalls = br.readLine();
			positionOccupied = br.readLine();
			practiseMode = br.readLine();
			standardRules = br.readLine();
			challengeRules = br.readLine();
			twoPlayerStandard = br.readLine();
			fourPlayerStandard = br.readLine();
			twoPlayerChallenge = br.readLine();
			fourPlayerChallenge = br.readLine();
			back = br.readLine();
			availableMoves = br.readLine();
			gameOver = br.readLine();
			newGame = br.readLine();
			connectToFourPlayerGame = br.readLine();
			connectToTwoPlayerGame = br.readLine();
			gameModes = br.readLine();
			chooseGameMode = br.readLine();
			mode = br.readLine();
		} catch (FileNotFoundException e) {
			SystemLogger.logError(e.getMessage());
		} catch (IOException e) {
			SystemLogger.logError(e.getMessage());
		}
	}

	public static String mode()
	{
		if (mode != null) {
			return mode;
		} else {
			return "mode";
		}
	}

	public static String chooseGameMode()
	{
		if (chooseGameMode != null) {
			return chooseGameMode;
		} else {
			return "Choose a game mode";
		}
	}

	public static String gameModes()
	{
		if (gameModes != null) {
			return gameModes;
		} else {
			return "Game modes";
		}
	}

	public static String connectToTwoPlayerGame()
	{
		if (connectToTwoPlayerGame != null) {
			return connectToTwoPlayerGame;
		} else {
			return "Connect to 2P game";
		}
	}

	public static String connectToFourPlayerGame()
	{
		if (connectToFourPlayerGame != null) {
			return connectToFourPlayerGame;
		} else {
			return "Connect to 4P game";
		}
	}

	public static String newGame()
	{
		if (newGame != null) {
			return newGame;
		} else {
			return "New game";
		}
	}

	public static String back()
	{
		if (back != null) {
			return back;
		} else {
			return "Back";
		}
	}

	public static String gameOver()
	{
		if (gameOver != null) {
			return gameOver;
		} else {
			return "Game over";
		}
	}

	public static String localMode()
	{
		if (start != null) {
			return start;
		} else {
			return "Start";
		}
	}

	public static String practiseMode()
	{
		if (practiseMode != null) {
			return practiseMode;
		} else {
			return "Practise mode";
		}
	}

	public static String standardRules()
	{
		if (standardRules != null) {
			return standardRules;
		} else {
			return "Standard rules";
		}
	}

	public static String challengeRules()
	{
		if (challengeRules != null) {
			return challengeRules;
		} else {
			return "Challenge rules";
		}
	}

	public static String twoPlayerStandard()
	{
		if (twoPlayerStandard != null) {
			return twoPlayerStandard;
		} else {
			return "2P Standard";
		}
	}

	public static String fourPlayerStandard()
	{
		if (fourPlayerStandard != null) {
			return fourPlayerStandard;
		} else {
			return "4P Standard";
		}
	}

	public static String twoPlayerChallenge()
	{
		if (twoPlayerChallenge != null) {
			return twoPlayerChallenge;
		} else {
			return "2P Challenge";
		}
	}

	public static String fourPlayerChallenge()
	{
		if (fourPlayerChallenge != null) {
			return fourPlayerChallenge;
		} else {
			return "4P Challenge";
		}
	}

	public static String multiplayer()
	{
		if (multiplayer != null) {
			return multiplayer;
		} else {
			return "Multiplayer";
		}
	}

	public static String quit()
	{
		if (quit != null) {
			return quit;
		} else {
			return "Quit";
		}
	}

	public static String settings() {
		if (settings != null) {
			return settings;
		} else {
			return "Settings";
		}
	}

	public static String moves()
	{
		if (moves != null) {
			return moves;
		} else {
			return "Moves";
		}
	}

	public static String player1()
	{
		if (player1 != null) {
			return player1;
		} else {
			return "Player 1";
		}
	}

	public static String player2()
	{
		if (player2 != null) {
			return player2;
		} else {
			return "Player 2";
		}
	}

	public static String walls()
	{
		if (walls != null) {
			return walls;
		} else {
			return "Walls";
		}
	}

	public static String hint()
	{
		if (hint != null) {
			return hint;
		} else {
			return "Hint";
		}
	}

	public static String joinGame()
	{
		if (joinGame != null) {
			return joinGame;
		} else {
			return "Join game";
		}
	}

	public static String enterIPAndPort()
	{
		if (multiplayerInstructions != null) {
			return multiplayerInstructions;
		} else {
			return "Enter the IP and port address for your machine";
		}
	}

	public static String createServer()
	{
		if (createServer != null) {
			return createServer;
		} else {
			return "Create server";
		}
	}

	public static String connectToGame()
	{
		if (connect != null) {
			return connect;
		} else {
			return "Connect to game";
		}
	}

	public static String blockingMove()
	{
		if (blockingMove != null) {
			return blockingMove;
		} else {
			return "You can't completely block another player";
		}
	}

	public static String invalidMove()
	{
		if (invalidMove != null) {
			return invalidMove;
		} else {
			return "Move is invalid";
		}
	}

	public static String noWalls()
	{
		if (noWalls != null) {
			return noWalls;
		} else {
			return "You don't have any walls";
		}
	}

	public static String positionOccupied()
	{
		if (positionOccupied != null) {
			return positionOccupied;
		} else {
			return "Position is occupied";
		}
	}

	public static String availableMoves()
	{
		if (availableMoves != null) {
			return availableMoves;
		} else {
			return "Available moves";
		}
	}
}
