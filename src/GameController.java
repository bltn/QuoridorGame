import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Ben Lawton
 * @author Khadija Patel
 * @author Jordan Bird
 * 
 * @version 12/02/2016
 */
public class GameController<T> {

    private static Board board;
    private static BoardGUI gui;

    private static Player player1;
    private static Player player2;
    //The player whose turn it is
    private static Player currentPlayer;

    public GameController(BoardGUI gui, Board board) {
        GameController.board = board;
        GameController.gui = gui;

        player1 = new Player(4, 0);
        player2 = new Player(4, 8);
        currentPlayer = player1;
    }
    
/**
 * Show the available positions for the player and then highlights them onscreen
 */
    public static void showCurrentPlayerMoves() {
    	Position position = board.getPosition(currentPlayer.getX(), currentPlayer.getY());
    	ArrayList<Position> availablePositions = board.getOccupiablePositions(position);
    	if (availablePositions.size() > 0) {
    		for (Position pos : availablePositions) {
				int x = pos.getX() * 2;
				int y = pos.getY() * 2;
    			gui.highlightPositionAvailability(x, y);
    		}
    	}
    }

    /**
     * Allows the active player to place a wall providing they have walls
     * 
     * @param pos1X
     * @param pos1Y
     * @param pos1Border
     * @param pos2X
     * @param pos2Y
     * @param pos2Border
     */
    public static void placeWall(int pos1X, int pos1Y, PositionWallLocation pos1Border, int pos2X, int pos2Y, PositionWallLocation pos2Border, int pos3X, int pos3Y, PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {
    	if (currentPlayer.hasWalls()) {
	    	Position coveredPosition1 = board.getPosition(pos1X, pos1Y);
	    	Position coveredPosition2 = board.getPosition(pos2X, pos2Y);
	    	Position coveredPosition3 = board.getPosition(pos3X, pos3Y);
	    	Position coveredPosition4 = board.getPosition(pos4X, pos4Y);

	    	assignWall(coveredPosition1, pos1Border);
	    	assignWall(coveredPosition2, pos2Border);
	    	assignWall(coveredPosition3, pos3Border);
	    	assignWall(coveredPosition4, pos4Border);

	    	currentPlayer.decrementWallCount();
	    	currentPlayer.incrementMoveCount();
	    	if (currentPlayer == player1) {
	    		gui.updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		gui.updatePlayer1WallCount(currentPlayer.getWallCount());
	    	}
	    	else {
	    		gui.updatePlayer2MoveCount(currentPlayer.getMoveCount());
	    		gui.updatePlayer2WallCount(currentPlayer.getWallCount());
	    	}
	    	changePlayer();
    	}
    	else {
    		//errorMessage("you have no remaining walls");
    	}
    }

    /**
     * Move a player to a specified position
     * @param posX the x co-ordinate of the move
     * @param posY the y co-ordinate of the move
     */
    public static void movePawn(int posX, int posY) {
    	if (currentPlayer == player1) {
    		if (posX == player2.getX() && posY == player2.getY()) {
    			//errorMessage("that position is occupied");
    		}
    		else {
    			if (isValidMove(currentPlayer, posX, posY)) {
		    		player1.setX(posX);
		    		player1.setY(posY);
		    		currentPlayer.incrementMoveCount();
		    		gui.updatePlayer1MoveCount(currentPlayer.getMoveCount());
		    		gui.updatePlayer1PawnPosition(posX, posY);
		    		if (board.getPosition(posX, posY).isBottom()) {
		    			resetGame();
		    		}
		    		changePlayer();
    			}
    			else {
    				//errorMessage("that isn't a valid move");
    			}
    		}
    	}
    	else {
    		if (posX == player1.getX() && posY == player1.getY()) {
	    		//errorMessage("that position is occupied");
    		}
    		else {
    			if (isValidMove(currentPlayer, posX, posY)) {
	    			player2.setX(posX);
		    		player2.setY(posY);
		    		currentPlayer.incrementMoveCount();
		    		gui.updatePlayer2MoveCount(currentPlayer.getMoveCount());
		    		gui.updatePlayer2PawnPosition(posX, posY);
		    		if (board.getPosition(posX, posY).isTop()) {
		    			resetGame();
		    		}
		    		changePlayer();
	    		}
    		}
    	}
    }
    
/**
 * Process the gameover
 */
    public static void gameOver() {
    	System.exit(0);
    }

    /**
     * Check if a player is allowed to move to a position 
     * @param player the active player to check against 
     * @param newX the new co-ordinate to move to 
     * @param newY the new co-ordinate to move to 
     * @return player can or cannot move to speicified position
     */
    public static boolean isValidMove(Player player, int newX, int newY) {
    	boolean isValid = false;
    	// if the move is directly along the x axis
    	if (((newX == (player.getX() + 1)) || (newX == (player.getX() - 1))) && newY == player.getY()) {
    		// if the move is to the left and the player won't be blocked by a wall to the left
    		if ((newX == (player.getX() - 1) && (!board.getPosition(player.getX(), player.getY()).hasLeftWall()))) {
    			isValid = true;
    		}
    		// if the move is to the right and the player won't be blocked by a wall to the right
    		else if ((newX == (player.getX() + 1) && (!board.getPosition(player.getX(), player.getY()).hasRightWall()))) {
    			isValid = true;
    		}
    	}
    	// if the move is directly along the y axis
    	else if (((newY == (player.getY() + 1)) || (newY == (player.getY() - 1))) && newX == player.getX()) {
    		// if the move is up and the player won't be blocked by a wall to the top
    		if ((newY == (player.getY() - 1) && (!board.getPosition(player.getX(), player.getY()).hasTopWall()))) {
    			isValid = true;
    		}
    		// if the move is down and the player won't be blocked by a wall to the bottom
    		else if ((newY == (player.getY() + 1) && (!board.getPosition(player.getX(), player.getY()).hasBottomWall()))) {
    			isValid = true;
    		}
    	}
    	return isValid;
    }

/**
 * Reset the game as a new game
 */
    private static void resetGame() {
    	gui.updatePlayer1MoveCount(0);
    	player1.setMoveCount(0);
    	gui.updatePlayer2MoveCount(0);
    	player2.setMoveCount(0);
    	gui.updatePlayer1WallCount(10);
    	player1.setWallCount(10);
    	gui.updatePlayer2WallCount(10);
    	player2.setWallCount(10);
    	gui.updatePlayer1PawnPosition(4, 0);
    	player1.setX(4);
    	player1.setY(0);
    	gui.updatePlayer2PawnPosition(4, 8);
    	player2.setX(4);
    	player2.setY(8);
    	currentPlayer = player1;
    	gui.resetWalls();
    }

    /**
     * Execute the winning sequence for a player 
     * @param player	winner
     */
    private static void gameOver(Player player) {
    	//gui.winner(player);
    	System.exit(0);
    }

    /**
     * Assign a wall a given position
     * 
     * @param position the position of the wall
     * @param location Enum category of wall direction
     */
    private static void assignWall(Position position, PositionWallLocation location) {
    	switch (location) {
	    	case LEFT: {
	    		position.placeLeftWall();
	    		break;
	    	}
	    	case RIGHT: {
	    		position.placeRightWall();
	    		break;
	    	}
	    	case TOP: {
	    		position.placeTopWall();
	    		break;
	    	}
	    	case BOTTOM: {
	    		position.placeBottomWall();
	    		break;
	    	}
    	}
    }

    /**
     * Make the next player the active player 
     */
    private static void changePlayer() {
    	if (currentPlayer == player1) {
    		currentPlayer = player2;
    	}
    	else {
    		currentPlayer = player1;
    	}
    }

}
