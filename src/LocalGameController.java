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
public class LocalGameController<T> implements Controller {

	// The game board and its positions' logic
    private Board board;
    // GUI (View) representing the game board
    private LocalBoardGUI gui;

    public LocalGameController(LocalBoardGUI gui, Board board) {
        this.board = board;
        this.gui = gui;
    }

    public Player getCurrentPlayer() {
    	return board.getCurrentPlayer();
    }

    /**
     * Get the available positions a player can move into and then highlight them in the GUI
     */
    public void showCurrentPlayerMoves() {
    	ArrayList<Position> availablePositions = board.getCurrentPlayerOccupiablePositions();
    	if (availablePositions.size() > 0) {
    		for (Position pos : availablePositions) {
				int x = pos.getX() * 2;
				int y = pos.getY() * 2;
    			gui.highlightPositionAvailability(x, y);
    		}
    	}
    }

    public void placeWall(int pos1X, int pos1Y, PositionWallLocation pos1Border, int pos2X, int pos2Y, PositionWallLocation pos2Border, int pos3X, int pos3Y,
    		PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {
    	Position coveredPos1 = board.getPosition(pos1X, pos1Y);
    	Position coveredPos2 = board.getPosition(pos2X, pos2Y);
    	Position coveredPos3 = board.getPosition(pos3X, pos3Y);
    	Position coveredPos4 = board.getPosition(pos4X, pos4Y);

    	try {
    		board.placeWalls(coveredPos1, pos1Border, coveredPos2, pos2Border, coveredPos3, pos3Border, coveredPos4, pos4Border);
    		gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
    		gui.updatePlayerWallCount(board.getPreviousPlayer().getWallCount(), board.getPreviousPlayer().getID());
    		gui.updateActivePlayer();
    	} catch (IllegalStateException e) {
    		throw e;
    	}
    }

    public void movePawn(int posX, int posY) {
    	try {
    		boolean gameOver = board.movePawn(posX, posY);
			gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
			gui.updatePlayerPawnPosition(board.getPreviousPlayer().getPosition().getX(), board.getPreviousPlayer().getPosition().getY(), board.getPreviousPlayer().getID());
			gui.updateActivePlayer();
			if (gameOver) {
				gui.updatePlayerMoveCount(0, 1);
				gui.updatePlayerMoveCount(0, 2);
				gui.updatePlayerWallCount(10, 1);
				gui.updatePlayerWallCount(10, 2);
				gui.updatePlayerPawnPosition(4, 0, 1);
				gui.updatePlayerPawnPosition(4, 8, 2);
				gui.resetWalls();
			}
    	} catch (IllegalArgumentException e) {
    		throw e;
    	}
    }
}
