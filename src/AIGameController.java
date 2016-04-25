
import java.util.ArrayList;

import javafx.stage.Stage;

public class AIGameController<T> implements Controller {

	private AI AI;
	// The game board and its positions' logic
	private StandardBoard board;
	// GUI (View) representing the game board
	private LocalBoardGUI gui;

	public AIGameController(LocalBoardGUI gui, StandardBoard board) {
		this.board = board;
		this.gui = gui;
		AI = new AI(board);
		// System.out.println(Utility.toString(AI.getBoard().getPositions()));
		// System.out.println(AI.getPossibleWallMoves().get(0).getTopleft());
	}

	public Player getCurrentPlayer() {
		return board.getCurrentPlayer();
	}

	/**
	 * Get the available positions a player can move into and then highlight
	 * them in the GUI
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

	public void AImove() {

		if (board.getCurrentPlayer() == board.getPlayer1())
			return;
		StandardBoard new1 = AI.clone(board);

		Move move = AI.Minimax(new1, 2);
		// System.out.println(move.getX()+" "+move.getY());
		if (move == null) {
			System.out.println("ok null");
		}
		if (move.getOrientation() != WallPlacement.NULL) {

			int topLeftX = move.getX();
			int topLeftY = move.getY();
			WallPlacement orientation = move.getOrientation();
			//System.out.println(move.getX() + " " + move.getY() + move.getOrientation() + " AI");

			placeWall(topLeftX, topLeftY, orientation, board.getCurrentPlayer().getID());

		}

		if (move.getOrientation() == WallPlacement.NULL) {

			// Position topLeft = move.getTopleft();
			int posX = move.getX();
			int posY = move.getY();

			//System.out.println(move.getX() + " " + move.getY() + " AI");

			movePawn(posX, posY, board.getCurrentPlayer().getID());

		}


	}

	public void placeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
		try {
			board.placeWalls(topLeftX, topLeftY, orientation);
			gui.displayWall(topLeftX, topLeftY, orientation, board.getPreviousPlayer().getID());
			gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
			gui.updatePlayerWallCount(board.getPreviousPlayer().getWallCount(), board.getPreviousPlayer().getID());
			gui.updateActivePlayer(board.getCurrentPlayer().getID());
			//System.out.println(topLeftX + " " + topLeftY + " " + orientation + " human");
			AImove();
		} catch (IllegalStateException e) {
			//
			e.printStackTrace();
			gui.displayErrorMessage(e.getMessage());
		}
	}

	/*
	 * public void removeWall(int topLeftX, int topLeftY, WallPlacement
	 * orientation, int playerID) {
	 *
	 * Position topLeft=board.getPosition(topLeftX, topLeftY); boolean
	 * wallsRemoved = board.unassignWalls(topLeft,orientation); if
	 * (wallsRemoved) { gui.removeWallDisplay(topLeftX, topLeftY, orientation);
	 * gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(),
	 * board.getPreviousPlayer().getID());
	 * gui.updatePlayerWallCount(board.getCurrentPlayer().getWallCount(),
	 * board.getCurrentPlayer().getID());
	 * gui.updateActivePlayer(board.getCurrentPlayer().getID()); } else {
	 * gui.displayErrorMessage("You can't remove that wall"); }
	 *
	 * }
	 */
	public void movePawn(int posX, int posY, int playerID) {
		try {
			boolean gameOver = board.movePawn(posX, posY);
			gui.updatePlayerMoveCount(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getID());
			gui.updatePlayerPawnPosition(board.getPreviousPlayer().getPosition().getX(),
					board.getPreviousPlayer().getPosition().getY(), board.getPreviousPlayer().getID());
			gui.updateActivePlayer(board.getCurrentPlayer().getID());
			if (gameOver) {
				GameOverGUI gui = new GameOverGUI((Controller) this);
				gui.start(new Stage());
			}
			AImove();
		} catch (IllegalArgumentException e) {
			System.out.println(posX + " " + posY + " " + playerID + "");
			e.printStackTrace();
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

	@Override
	public void removeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {/*Can't remove walls in practise mode*/}

	@Override
	public int getPlayer3X() {
		return board.getPlayer3().getPosition().getX();
	}

	@Override
	public int getPlayer3Y() {
		return board.getPlayer3().getPosition().getY();
	}

	@Override
	public int getPlayer4X() {
		return board.getPlayer4().getPosition().getX();
	}

	@Override
	public int getPlayer4Y() {
		return board.getPlayer4().getPosition().getY();
	}

}
