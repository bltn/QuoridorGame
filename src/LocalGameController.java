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

    /**
     * First argument must always be the top left position of the 4x4 grid of positions the walls are
     * being assigned to, as this is the one validation must be performed on
     */
    public void placeWall(int topLeftX, int topLeftY, PositionWallLocation topLeftBorder, int pos2X, int pos2Y, PositionWallLocation pos2Border, int pos3X, int pos3Y,
    	PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {

    	Position coveredPos1 = board.getPosition(topLeftX, topLeftY);
    	Position coveredPos2 = board.getPosition(pos2X, pos2Y);
    	Position coveredPos3 = board.getPosition(pos3X, pos3Y);
    	Position coveredPos4 = board.getPosition(pos4X, pos4Y);

    	try {
    		board.placeWalls(coveredPos1, topLeftBorder, coveredPos2, pos2Border, coveredPos3, pos3Border, coveredPos4, pos4Border);
    		gui.displayWall(topLeftX, topLeftY, topLeftBorder, board.getPreviousPlayer().getID());
    		gui.displayWall(pos2X, pos2Y, pos2Border, board.getPreviousPlayer().getID());
    		gui.displayWall(pos3X, pos3Y, pos3Border, board.getPreviousPlayer().getID());
    		gui.displayWall(pos4X, pos4Y, pos4Border, board.getPreviousPlayer().getID());
    		gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
    		gui.updatePlayerWallCount(board.getPreviousPlayer().getWallCount(), board.getPreviousPlayer().getID());
    		gui.updateActivePlayer(board.getCurrentPlayer().getID());
    	} catch (IllegalStateException e) {
    		throw e;
    	}
    }

    public void removeWall(int topLeftX, int topLeftY, PositionWallLocation topLeftBorder, int pos2X, int pos2Y, PositionWallLocation pos2Border, int pos3X, int pos3Y,
        	PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border) {

    	if (this.board instanceof ChallengeBoard) {
    		Position coveredPos1 = board.getPosition(topLeftX, topLeftY);
    		Position coveredPos2 = board.getPosition(pos2X, pos2Y);
        	Position coveredPos3 = board.getPosition(pos3X, pos3Y);
        	Position coveredPos4 = board.getPosition(pos4X, pos4Y);

    		boolean wallsRemoved = ((ChallengeBoard) board).removeWalls(coveredPos1, topLeftBorder, coveredPos2, pos2Border, coveredPos3, pos3Border, coveredPos4, pos4Border);
    		if (wallsRemoved) {
    			gui.removeWallDisplay(topLeftX, topLeftY, topLeftBorder, board.getPreviousPlayer().getID());
        		gui.removeWallDisplay(pos2X, pos2Y, pos2Border, board.getPreviousPlayer().getID());
        		gui.removeWallDisplay(pos3X, pos3Y, pos3Border, board.getPreviousPlayer().getID());
        		gui.removeWallDisplay(pos4X, pos4Y, pos4Border, board.getPreviousPlayer().getID());
    			gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
    			gui.updatePlayerWallCount(board.getCurrentPlayer().getWallCount(), board.getCurrentPlayer().getID());
    			gui.updateActivePlayer(board.getCurrentPlayer().getID());
    		}
    	}
    }

    public void movePawn(int posX, int posY) {
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
