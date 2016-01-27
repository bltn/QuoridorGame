import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Ben Lawton
 * @author Khadija Patel
 */
public class GameController<T> {

    private static Board board;
    private static BoardGUI gui;

    private static Player player1;
    private static Player player2;
    //The player whose turn it is
    private static Player currentPlayer;

    public GameController(BoardGUI gui, Board board) {
        this.board = board;
        this.gui = gui;
		gui.start(new Stage());

        player1 = new Player(4, 0);
        player2 = new Player(4, 8);
        currentPlayer = player1;
    }

    public static void showCurrentPlayerMoves() {
    	Position position = board.getPosition(currentPlayer.getX(), currentPlayer.getY());
    	ArrayList<Position> availablePositions = board.getOccupiablePositions(position);
    	if (availablePositions.size() > 0) {
    		for (Position pos : availablePositions) {
				int x = pos.getX() * 2 + 1;
				int y = pos.getY() * 2 + 1;
    			gui.highlightPositionAvailability(x, y);
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
    public static void placeWall(int pos1X, int pos1Y, int pos1BorderSetting, int pos2X, int pos2Y, int pos2BorderSetting) {
    	if (currentPlayer.hasWalls()) {
	    	Position coveredPosition1 = board.getPosition(pos1X, pos1Y);
	    	Position coveredPosition2 = board.getPosition(pos2X, pos2Y);

	    	assignWall(coveredPosition1, pos1BorderSetting);
	    	assignWall(coveredPosition2, pos2BorderSetting);

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

    public static void movePawn(int posX, int posY) {
		int x = (posX - 1) / 2;
		int y = (posY - 1) / 2;
    	if (currentPlayer == player1) {
    		if (x == player2.getX() && y == player2.getY()) {
    			//errorMessage("that position is occupied");
    		}
    		else {
    			if (isValidMove(currentPlayer, x, y)) {
		    		player1.setX(x);
		    		player1.setY(y);
		    		currentPlayer.incrementMoveCount();
		    		gui.updatePlayer1MoveCount(currentPlayer.getMoveCount());
		    		gui.updatePlayer1PawnPosition(posX, posY);
		    		if (board.getPosition(x, y).isBottom()) {
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
    		if (x == player1.getX() && y == player1.getY()) {
	    		//errorMessage("that position is occupied");
    		}
    		else {
    			if (isValidMove(currentPlayer, x, y)) {
	    			player2.setX(x);
		    		player2.setY(y);
		    		currentPlayer.incrementMoveCount();
		    		gui.updatePlayer2MoveCount(currentPlayer.getMoveCount());
		    		gui.updatePlayer2PawnPosition(posX, posY);
		    		if (board.getPosition(x, y).isTop()) {
		    			resetGame();
		    		}
		    		changePlayer();
	    		}
    		}
    	}
    }

    public static void gameOver() {
    	System.exit(0);
    }

    public static boolean isValidMove(Player player, int newX, int newY) {
    	boolean isValid = false;
    	if (((newX == (player.getX() + 1)) || (newX == (player.getX() - 1))) && newY == player.getY()) {
    		isValid = true;
    	}
    	else if (((newY == (player.getY() + 1)) || (newY == (player.getY() - 1))) && newX == player.getX()) {
    		isValid = true;
    	}
    	return isValid;
    }


    private static void resetGame() {
    	gui.updatePlayer1MoveCount(0);
    	player1.setMoveCount(0);
    	gui.updatePlayer2MoveCount(0);
    	player2.setMoveCount(0);
    	gui.updatePlayer1WallCount(10);
    	player1.setWallCount(10);
    	gui.updatePlayer2WallCount(10);
    	player2.setWallCount(10);
    	gui.updatePlayer1PawnPosition(9,1);
    	player1.setX(4);
    	player1.setY(0);
    	gui.updatePlayer2PawnPosition(9, 17);
    	player2.setX(4);
    	player2.setY(8);
    	currentPlayer = player1;
    	gui.resetWalls();
    }

    private static void gameOver(Player player) {
    	//gui.winner(player);
    	System.exit(0);
    }

    private static void assignWall(Position position, int borderValue) {
    	if (borderValue == -1) {
    		position.placeBottomWall();
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

    private static void changePlayer() {
    	if (currentPlayer == player1) {
    		currentPlayer = player2;
    	}
    	else {
    		currentPlayer = player1;
    	}
    }

}