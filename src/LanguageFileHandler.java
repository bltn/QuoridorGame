import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Jordan Bird
 * @version 4/4/2016
 */


public class LanguageFileHandler {
	
	String filePath;
	private BufferedReader br;
	
	private String start;
	private String multiplayer;
	private String quit;
	private String moves;
	private String player2;
	private String player1;
	private String walls;
	private String joinGame;
	private String hint;
	private String createServer;
	private String multiplayerInstructions;
	private String connect;

	public LanguageFileHandler(String language) throws IOException{
		
		if(language == "English")
		{
			filePath = "src/lang/English.txt";
		}
		
		if(language == "French")
		{
			filePath = "src/lang/French.txt";
		}
		
		if(language == "Spanish")
		{
			filePath = "src/lang/Spanish.txt";
		}
		
		if(language == "Chinese")
		{
			filePath = "src/lang/Chinese.txt";
		}
		
		
		
		FileInputStream fs= new FileInputStream(filePath);
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
		
		System.out.println(start);
	}
	
	public String getStart()
	{
		return start;
	}
	
	public String getMultiplayer()
	{
		return multiplayer;
	}
	
	public String getQuit()
	{
		return quit;
	}
	
	public String getMoves()
	{
		return moves;
	}
	
	public String getPlayer1()
	{
		return player1;
	}
	
	public String getPlayer2()
	{
		return player2;
	}
	
	public String getWalls()
	{
		return walls;
	}
	
	public String getHint()
	{
		return hint;
	}
	
	public String getJoinGame()
	{
		return joinGame;
	}
	
	public String getMP()
	{
		return multiplayerInstructions;
	}
	
	public String getCreateServer()
	{
		return createServer;
	}
	
	public String getConnect()
	{
		return connect;
	}
	
}
