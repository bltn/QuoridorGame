import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * @author Ben Lawton
 * @author Khadija Patel
 * @author Jordan Bird
 *
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

    public void placeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
    	try {
    		board.placeWalls(topLeftX, topLeftY, orientation);
    		gui.displayWall(topLeftX, topLeftY, orientation, board.getPreviousPlayer().getID());
    		gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
    		gui.updatePlayerWallCount(board.getPreviousPlayer().getWallCount(), board.getPreviousPlayer().getID());
    		gui.updateActivePlayer(board.getCurrentPlayer().getID());
    	} catch (IllegalStateException e) {
    		gui.displayErrorMessage(e.getMessage());
    	}
    }

    public void removeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
    	if (this.board instanceof ChallengeBoard) {
    		boolean wallsRemoved = ((ChallengeBoard) board).removeWalls(topLeftX, topLeftY, orientation);
    		if (wallsRemoved) {
    			gui.removeWallDisplay(topLeftX, topLeftY, orientation);
    			gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
    			gui.updatePlayerWallCount(board.getCurrentPlayer().getWallCount(), board.getCurrentPlayer().getID());
    			gui.updateActivePlayer(board.getCurrentPlayer().getID());
    		} else {
    			gui.displayErrorMessage("You can't remove that wall");
    		}
    	}
    }

    public void movePawn(int posX, int posY, int playerID) {
    	try {
    		boolean gameOver = board.movePawn(posX, posY);
			gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
			gui.updatePlayerPawnPosition(board.getPreviousPlayer().getPosition().getX(), board.getPreviousPlayer().getPosition().getY(), board.getPreviousPlayer().getID());
			gui.updateActivePlayer(board.getCurrentPlayer().getID());
			if (gameOver) {
                GameOverGUI gui = new GameOverGUI((Controller) this);
                gui.start(new Stage());
			}
    	} catch (IllegalArgumentException e) {
    		gui.displayErrorMessage(e.getMessage());
    	}
    }

    public void resetGame() {
    	Player player1 = board.getPlayer1();
    	Player player2 = board.getPlayer2();
    	gui.updatePlayerMoveCount(player1.getMoveCount(), 1);
    	gui.updatePlayerMoveCount(player2.getMoveCount(), 2);
    	gui.updatePlayerWallCount(player1.getWallCount(), 1);
    	gui.updatePlayerWallCount(player2.getWallCount(), 2);
    	gui.updatePlayerPawnPosition(player1.getPosition().getX(), player1.getPosition().getY(), 1);
    	gui.updatePlayerPawnPosition(player2.getPosition().getX(), player2.getPosition().getY(), 2);
		gui.resetWalls();
    }

	@Override
	public int getPlayer1X() {
		return board.getPlayer1().getPosition().getX();
	}

	@Override
	public int getPlayer1Y() {
		return board.getPlayer1().getPosition().getY();
	}

	@Override
	public int getPlayer2X() {
		return board.getPlayer2().getPosition().getX();
	}

	@Override
	public int getPlayer2Y() {
		return board.getPlayer2().getPosition().getY();
	}
}
