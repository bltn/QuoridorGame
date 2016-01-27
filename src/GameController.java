<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Iterator;


=======
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;


>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
/**
 * @author Ben Lawton
 * @author Khadija Patel
 */
public class GameController<T> {

<<<<<<< HEAD
    private Board board;
    private BoardGUI gui; 
    
    private Player player1;
    private Player player2;
    //The player whose turn it is 
    private Player currentPlayer;

    private int player1MoveCount;
    private int player2MoveCount;
=======
    private static Board board;
    private static BoardGUI gui;
    
    private static Player player1;
    private static Player player2;
    //The player whose turn it is 
    private static Player currentPlayer;

    private static int player1MoveCount;
    private static int player2MoveCount;
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7

    public GameController(BoardGUI gui, Board board) {
        this.board = board;
        this.gui = gui;
<<<<<<< HEAD
=======
		gui.start(new Stage());
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
        
        player1 = new Player(4, 0);
        player2 = new Player(4, 8); 
        currentPlayer = player1;
        
        player1MoveCount = 0;
        player2MoveCount = 0;
    }
<<<<<<< HEAD
    
=======

>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    public static void main(String args[]) {
    	Board board = new Board();
    	BoardGUI gui = new BoardGUI();
    	GameController controller = new GameController(gui, board);
    }

<<<<<<< HEAD
    public void showCurrentPlayerMoves() {
=======
    public static void showCurrentPlayerMoves() {
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    	Position position = board.getPosition(currentPlayer.getX(), currentPlayer.getY()); 
    	ArrayList<Position> availablePositions = board.getOccupiablePositions(position);
    	if (availablePositions.size() > 0) {
    		for (Position pos : availablePositions) {
<<<<<<< HEAD
    			//highlightPositionAvailability(pos.getX(), pos.getY()); 
=======
				int x = pos.getX() * 2 + 1;
				int y = pos.getY() * 2 + 1;
    			gui.highlightPositionAvailability(x, y);
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    		}
    	}
    }
    
    /**
     * @param pos1X
     * @param pos1Y
     * @param pos1BorderSetting
     * @param pos2X
     * @param pos2Y
     * @param pos2BorderSetting
     */
<<<<<<< HEAD
    public void placeWall(int pos1X, int pos1Y, int pos1BorderSetting, int pos2X, int pos2Y, int pos2BorderSetting) {
=======
    public static void placeWall(int pos1X, int pos1Y, int pos1BorderSetting, int pos2X, int pos2Y, int pos2BorderSetting) {
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    	if (currentPlayer.hasWalls()) {
	    	Position coveredPosition1 = board.getPosition(pos1X, pos1Y);
	    	Position coveredPosition2 = board.getPosition(pos2X, pos2Y);
	    	
	    	assignWall(coveredPosition1, pos1BorderSetting);
	    	assignWall(coveredPosition2, pos2BorderSetting); 
	    	
	    	currentPlayer.decrementWallCount();
	    	currentPlayer.incrementMoveCount();
	    	if (currentPlayer == player1) {
<<<<<<< HEAD
	    		//updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		//updatePlayerWallCount(currentPlayer.getWallCount());
	    	}
	    	else {
	    		//updatePlayer2MoveCount(currentPlayer.getMoveCount());
	    		//updatePlayer2WallCount(currentPlayer.getWallCount());
=======
	    		gui.updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		gui.updatePlayer1WallCount(currentPlayer.getWallCount());
	    	}
	    	else {
	    		gui.updatePlayer2MoveCount(currentPlayer.getMoveCount());
	    		gui.updatePlayer2WallCount(currentPlayer.getWallCount());
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
	    	}
	    	changePlayer();
    	}
    	else {
    		//errorMessage("you have no remaining walls");
    	}
    }

<<<<<<< HEAD
    public void movePawn(int posX, int posY) {
    	if (currentPlayer == player1) {
    		if (posX == player2.getX() && posY == player2.getY()) {
    			//errorMessage("that position is occupied");
    		}
    		else {
	    		player1.setX(posX);
	    		player1.setY(posY);
	    		currentPlayer.incrementMoveCount();
	    		//updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		//updatePlayer1PawnPosition(currentPlayer.getX(), currentPlayer.getY());
=======
    public static void movePawn(int posX, int posY) {
		int x = (posX - 1) / 2;
		int y = (posY - 1) / 2;
    	if (currentPlayer == player1) {
    		if (x == player2.getX() && y == player2.getY()) {
    			//errorMessage("that position is occupied");
    		}
    		else {
	    		player1.setX(x);
	    		player1.setY(y);
	    		currentPlayer.incrementMoveCount();
	    		gui.updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		gui.updatePlayer1PawnPosition(posX, posY);
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
	    		if (currentPlayer.getPosition().isBottom()) {
	    			gameOver(currentPlayer);
	    			return;
	    		}
	    		changePlayer();
    		}
    	}
    	else {
    		if (posX == player1.getX() && posY == player1.getY()) {
	    		//errorMessage("that position is occupied");
    		}
    		else {
<<<<<<< HEAD
    			player2.setX(posX);
	    		player2.setY(posY); 
	    		currentPlayer.incrementMoveCount();
	    		//updatePlayer2MoveCount(player2MoveCount);
	    		//updatePlayer2PawnPosition(player2.getX(), player2.getY()); 
=======
    			player2.setX(x);
	    		player2.setY(y);
	    		currentPlayer.incrementMoveCount();
	    		gui.updatePlayer2MoveCount(currentPlayer.getMoveCount());
	    		gui.updatePlayer2PawnPosition(posX, posY);
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
	    		if (currentPlayer.getPosition().isTop()) {
	    			gameOver(currentPlayer);
	    			return;
	    		}
	    		changePlayer();
    		}
    	}
    }
    
<<<<<<< HEAD
    public void gameOver() {
    	System.exit(0);
    }
    
    private void gameOver(Player player) {
=======
    public static void gameOver() {
    	System.exit(0);
    }
    
    private static void gameOver(Player player) {
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    	//gui.winner(player);
    	System.exit(0);
    }

<<<<<<< HEAD
    private void assignWall(Position position, int borderValue) {
    	if (borderValue == -1) {
    		position.placeLeftWall();
=======
    private static void assignWall(Position position, int borderValue) {
    	if (borderValue == -1) {
    		position.placeBottomWall();
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    	}
    	else if (borderValue == 0) {
    		position.placeTopWall();
    	}
    	else if (borderValue == 1) {
    		position.placeRightWall();
    	}
    	else if (borderValue == 2) {
    		position.placeLeftWall();
    	}
    }
    
<<<<<<< HEAD
    private void changePlayer() {
    	if (this.currentPlayer == player1) {
=======
    private static void changePlayer() {
    	if (currentPlayer == player1) {
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    		currentPlayer = player2;
    	}
    	else {
    		currentPlayer = player1;
    	}
<<<<<<< HEAD
    	//updateCurrentPlayer(currentPlayer); 
=======
>>>>>>> 4a4702573c5b82b9bca2bc45aa5559e3d2470ff7
    }

}