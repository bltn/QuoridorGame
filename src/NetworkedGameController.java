import java.util.ArrayList;

public class NetworkedGameController implements Controller {

	private ClientSocketIOThread player1IO;
	private ClientSocketIOThread player2IO;

	private Board board;

	public NetworkedGameController(Board board) {
		this.board = board;
	}

	public void setPlayer1IO(ClientSocketIOThread ioThread) {
		if (player1IO == null) {
			this.player1IO = ioThread;
		}
	}

	public void setPlayer2IO(ClientSocketIOThread ioThread) {
		if (player2IO == null) {
			this.player2IO = ioThread;
		}
	}

	@Override
	public Player getCurrentPlayer() {
		return board.getCurrentPlayer();
	}

	@Override
	public void showCurrentPlayerMoves() {
		ArrayList<Position> availablePositions = board.getCurrentPlayerOccupiablePositions();
    	if (availablePositions.size() > 0) {
    		for (Position pos : availablePositions) {
				int x = pos.getX() * 2;
				int y = pos.getY() * 2;
    			player1IO.sendAvailableMove(x, y);
    			player2IO.sendAvailableMove(x, y);
    		}
    	}
	}

	@Override
	public void placeWall(int pos1x, int pos1y, PositionWallLocation pos1Border, int pos2x, int pos2y,
			PositionWallLocation pos2Border, int pos3x, int pos3y, PositionWallLocation pos3Border, int pos4x,
			int pos4y, PositionWallLocation pos4Border) {/*Method not implemented; player ID required. See below.*/}

	public void placeWall(int pos1x, int pos1y, PositionWallLocation pos1Border, int pos2x, int pos2y,
			PositionWallLocation pos2Border, int pos3x, int pos3y, PositionWallLocation pos3Border, int pos4x,
			int pos4y, PositionWallLocation pos4Border, int playerID) {
		if (playerID == board.getCurrentPlayer().getID()) {
			Position coveredPos1 = board.getPosition(pos1x, pos1y);
			Position coveredPos2 = board.getPosition(pos2x, pos2y);
			Position coveredPos3 = board.getPosition(pos3x, pos3y);
			Position coveredPos4 = board.getPosition(pos4x, pos4y);

			try {
				board.placeWalls(coveredPos1, pos1Border, coveredPos2, pos2Border, coveredPos3, pos3Border, coveredPos4, pos4Border);
				player1IO.sendWallUpdate(coveredPos1.getX(), coveredPos1.getY(), pos1Border);
				player1IO.sendWallUpdate(coveredPos2.getX(), coveredPos2.getY(), pos2Border);
				player1IO.sendWallUpdate(coveredPos3.getX(), coveredPos3.getY(), pos3Border);
				player1IO.sendWallUpdate(coveredPos4.getX(), coveredPos4.getY(), pos4Border);

				player2IO.sendWallUpdate(coveredPos1.getX(), coveredPos1.getY(), pos1Border);
				player2IO.sendWallUpdate(coveredPos2.getX(), coveredPos2.getY(), pos2Border);
				player2IO.sendWallUpdate(coveredPos3.getX(), coveredPos3.getY(), pos3Border);
				player2IO.sendWallUpdate(coveredPos4.getX(), coveredPos4.getY(), pos4Border);

				Player prevPlayer = board.getPreviousPlayer();
				player1IO.sendStatsUpdate(prevPlayer.getMoveCount(), prevPlayer.getWallCount(), prevPlayer.getID());
				player2IO.sendStatsUpdate(prevPlayer.getMoveCount(), prevPlayer.getWallCount(), prevPlayer.getID());

				player1IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
				player2IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
			} catch (IllegalStateException e) {
				if (playerID == 1) {
					player1IO.sendErrorMessage(e.getMessage());
				}
				else if (playerID == 2) {
					player2IO.sendErrorMessage(e.getMessage());
				}
			}
		}
		else {
			if (playerID == 1) {
				player1IO.sendErrorMessage("It isn't your turn.");
			}
			else if (playerID == 2) {
				player2IO.sendErrorMessage("It isn't your turn.");
			}
		}
	}

	@Override
	public void movePawn(int posX, int posY) {/*Method not implemented; player ID required. See below.*/}

	public void movePawn(int posX, int posY, int playerID) {
		if (playerID == board.getCurrentPlayer().getID()) {
			try {
				boolean gameOver = board.movePawn(posX, posY);
                Player prevPlayer = board.getPreviousPlayer();
                player1IO.sendPawnUpdate(prevPlayer.getPosition().getX(), prevPlayer.getPosition().getY(), prevPlayer.getID());
                player1IO.sendStatsUpdate(prevPlayer.getMoveCount(), prevPlayer.getWallCount(), prevPlayer.getID());
                player2IO.sendPawnUpdate(prevPlayer.getPosition().getX(), prevPlayer.getPosition().getY(), prevPlayer.getID());
                player2IO.sendStatsUpdate(prevPlayer.getMoveCount(), prevPlayer.getWallCount(), prevPlayer.getID());
                player1IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
                player2IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
                if (gameOver) {
                    player1IO.sendStatsUpdate(0, 10, 1);
                    player1IO.sendStatsUpdate(0, 10, 2);
                    player2IO.sendStatsUpdate(0, 10, 1);
                    player2IO.sendStatsUpdate(0, 10, 2);
                    player1IO.sendPawnUpdate(4, 0, 1);
                    player1IO.sendPawnUpdate(4, 8, 2);
                    player2IO.sendPawnUpdate(4, 0, 1);
                    player2IO.sendPawnUpdate(4, 8, 2);
                    player1IO.sendResetWalls();
                    player2IO.sendResetWalls();
                    player1IO.sendCurrentPlayerGUIUpdate(1);
                    player2IO.sendCurrentPlayerGUIUpdate(1);
                }
			} catch (IllegalArgumentException e) {
				if (playerID == 1) {
					player1IO.sendErrorMessage(e.getMessage());
				}
				else if (playerID == 2) {
					player2IO.sendErrorMessage(e.getMessage());
				}
			}
		}
		else {
			if (playerID == 1) {
				player1IO.sendErrorMessage("It isn't your turn.");
			}
			else if (playerID == 2) {
				player2IO.sendErrorMessage("It isn't your turn.");
			}
		}
	}
}
