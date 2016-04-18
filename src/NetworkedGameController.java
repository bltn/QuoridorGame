import java.util.ArrayList;

public class NetworkedGameController implements Controller {

	private ClientSocketIOThread player1IO;
	private ClientSocketIOThread player2IO;
	private ClientSocketIOThread player3IO;
	private ClientSocketIOThread player4IO;

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

	public void setPlayer3IO(ClientSocketIOThread ioThread) {
		if (player3IO == null) {
			this.player3IO = ioThread;
		}
	}

	public void setPlayer4IO(ClientSocketIOThread ioThread) {
		if (player4IO == null) {
			this.player4IO = ioThread;
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

	public void placeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
		if (board.getCurrentPlayer().getID() == playerID) {
			try {
				board.placeWalls(topLeftX, topLeftY, orientation);
				sendWallUpdate(topLeftX, topLeftY, orientation);
			} catch (IllegalStateException e) {
				if (playerID == 1) {
					player1IO.sendErrorMessage(e.getMessage());
				} else if (playerID == 2) {
					player2IO.sendErrorMessage(e.getMessage());
				}
			}
		} else {
			if (playerID == 1) {
				player1IO.sendErrorMessage("It isn't your turn.");
			}
			else if (playerID == 2) {
				player2IO.sendErrorMessage("It isn't your turn.");
			}
		}
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

	public void movePawn(int posX, int posY, int playerID) {
		if (playerID == board.getCurrentPlayer().getID()) {
			try {
				boolean gameOver = board.movePawn(posX, posY);
                Player prevPlayer = board.getPreviousPlayer();
                sendPawnUpdate(prevPlayer);
                if (gameOver) {
                    resetGame();
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

	public void removeWall (int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
		if (board.getCurrentPlayer().getID() == playerID) {
			if (this.board instanceof ChallengeBoard) {
				boolean wallsRemoved = ((ChallengeBoard) board).removeWalls(topLeftX, topLeftY, orientation);
				if (wallsRemoved) {
					sendWallRemovalUpdate(topLeftX, topLeftY, orientation);
				} else {
					if (playerID == 1) {
						player1IO.sendErrorMessage("You can't remove that wall");
					} else if (playerID == 2) {
						player2IO.sendErrorMessage("You can't remove that wall");
					}
				}
			}
		} else {
			if (playerID == 1) {
				player1IO.sendErrorMessage("It isn't your turn.");
			}
			else if (playerID == 2) {
				player2IO.sendErrorMessage("It isn't your turn.");
			}
		}
	}

	public void resetGame() {
        Player player1 = board.getPlayer1();
        Player player2 = board.getPlayer2();
        // send updates to player 1's GUI
        player1IO.sendStatsUpdate(player1.getMoveCount(), player1.getWallCount(), player1.getID());
        player1IO.sendStatsUpdate(player2.getMoveCount(), player2.getMoveCount(), player2.getID());
        player1IO.sendPawnUpdate(player1.getPosition().getX(), player1.getPosition().getY(), player1.getID());
        player1IO.sendPawnUpdate(player2.getPosition().getX(), player2.getPosition().getY(), player2.getID());
        // send updates for player 2's GUI
        player2IO.sendStatsUpdate(player2.getMoveCount(), player2.getWallCount(), player2.getID());
        player2IO.sendStatsUpdate(player1.getMoveCount(), player1.getMoveCount(), player1.getID());
        player2IO.sendPawnUpdate(player2.getPosition().getX(), player2.getPosition().getY(), player2.getID());
        player2IO.sendPawnUpdate(player1.getPosition().getX(), player1.getPosition().getY(), player1.getID());
        // send commands to reset both GUI's walls
        player1IO.sendResetWalls();
        player2IO.sendResetWalls();
    }

	private void sendWallRemovalUpdate(int topLeftX, int topLeftY, WallPlacement orientation) {

		player1IO.sendRemoveWallDisplay(topLeftX, topLeftY, orientation);
		player2IO.sendRemoveWallDisplay(topLeftX, topLeftY, orientation);

		Player player1 = board.getPlayer1();
		Player player2 = board.getPlayer2();

		player1IO.sendStatsUpdate(player1.getMoveCount(), player1.getWallCount(), 1);
		player1IO.sendStatsUpdate(player2.getMoveCount(), player2.getWallCount(), 2);
		player2IO.sendStatsUpdate(player1.getMoveCount(), player1.getWallCount(), 1);
		player2IO.sendStatsUpdate(player2.getMoveCount(), player2.getWallCount(), 2);

		player1IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
        player2IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
	}

	private void sendWallUpdate(int topLeftX, int topLeftY, WallPlacement orientation) {
        player1IO.sendWallUpdate(topLeftX, topLeftY, orientation, board.getPreviousPlayer().getID());
        player2IO.sendWallUpdate(topLeftX, topLeftY, orientation, board.getPreviousPlayer().getID());

        player1IO.sendStatsUpdate(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getWallCount(), board.getPreviousPlayer().getID());
        player2IO.sendStatsUpdate(board.getPreviousPlayer().getMoveCount(), board.getPreviousPlayer().getWallCount(), board.getPreviousPlayer().getID());

        player1IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
        player2IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
	}

    private void sendPawnUpdate(Player prevPlayer) {
        player1IO.sendPawnUpdate(prevPlayer.getPosition().getX(), prevPlayer.getPosition().getY(), prevPlayer.getID());
        player1IO.sendStatsUpdate(prevPlayer.getMoveCount(), prevPlayer.getWallCount(), prevPlayer.getID());
        player2IO.sendPawnUpdate(prevPlayer.getPosition().getX(), prevPlayer.getPosition().getY(), prevPlayer.getID());
        player2IO.sendStatsUpdate(prevPlayer.getMoveCount(), prevPlayer.getWallCount(), prevPlayer.getID());
        player1IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
        player2IO.sendCurrentPlayerGUIUpdate(board.getCurrentPlayer().getID());
    }
}
