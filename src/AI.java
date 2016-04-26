
import java.util.ArrayList;
import java.util.Random;

public class AI {

	private StandardBoard AIBoard;

	private ArrayList<Move> PossibleWallMoves;

	public AI(StandardBoard AIBoard) {
		this.AIBoard = AIBoard;
		PossibleWallMoves = new ArrayList<Move>(128);
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Move WallMove1 = new Move(y, x, WallPlacement.VERTICAL);
				Move WallMove2 = new Move(y, x, WallPlacement.HORIZONTAL);
				PossibleWallMoves.add(WallMove1);
				PossibleWallMoves.add(WallMove2);
			}
		}
	}

	public Move Minimax(int depth) {
		int highestScore = -99999999;
		Move bestMove = null;

		ArrayList<Move> moves = PossibleMoves(AIBoard);

		int PlayerLenght = 0;
		int AILength = 0;
		for (Move move : moves) {
			if (isValid(AIBoard, move) == false)
				continue;
			move(AIBoard, move);

			if (isBlock(PlayerLenght, AILength, AIBoard, move) == true) {
				int score = Min(PlayerLenght, AILength, AIBoard, -99999999, 99999999, depth - 1);
				if (highestScore < score) {
					highestScore = score;
					bestMove = move;
				}
			}

			unmove(AIBoard, move);
		}
		return bestMove;
	}

	private int Min(int PlayerLenght, int AILength, StandardBoard board, int a, int b, int depth) {
		if (depth == 0) {
			return evaluate(PlayerLenght, AILength, board);
		}

		int lowestScore = 99999999;
		ArrayList<Move> moves = PossibleMoves(board);

		for (Move move : moves) {
			if (isValid(board, move) == false)
				continue;
			move(board, move);

			if (isBlock(PlayerLenght, AILength, board, move) == true) {

				lowestScore = Math.min(Max(PlayerLenght, AILength, board, a, b, depth - 1), lowestScore);
				b = Math.min(b, lowestScore);
			}
			unmove(board, move);
			if (b <= a)
				break;
		}
		return lowestScore;

	}

	private int Max(int PlayerLenght, int AILength, StandardBoard board, int a, int b, int depth) {
		if (depth == 0) {
			return evaluate(PlayerLenght, AILength, board);
		}

		int highestScore = -99999999;
		ArrayList<Move> moves = PossibleMoves(board);

		for (Move move : moves) {
			if (isValid(board, move) == false)
				continue;
			move(board, move);

			if (isBlock(PlayerLenght, AILength, board, move) == true) {

				highestScore = Math.max(Min(PlayerLenght, AILength, board, a, b, depth - 1), highestScore);
				a = Math.max(a, highestScore);
			}
			unmove(board, move);

			if (b <= a)
				break;
		}

		return highestScore;
	}

	private int evaluate(int PlayerLenght, int AILength, Board board) {

		int AIManhata = board.getPlayer2().getPosition().getY() - 0;
		//int PlayerManhata = 8 - board.getPlayer1().getPosition().getY();
		Random random = new Random();
		int randomNumber = random.nextInt(10) + 1;
		return (15 * PlayerLenght - 50 * AILength) - AIManhata + randomNumber;//
	}

	private int evaluateNoWall(Board board) {


		int AILength = Utility.shortestPathLenght(board.getPositions(), board.getPlayer2().getPosition(), 0);// 0
		int AIManhata = board.getPlayer2().getPosition().getY() - 0;

		Random random = new Random();
		int randomNumber = random.nextInt(10) + 1;
		return -50 * AILength - AIManhata + randomNumber;
	}
	
	public boolean isBlock(int PlayerLenght, int AILength, StandardBoard board, Move move) {
		boolean valid = true;

		boolean p1block = Utility.AstarSearch(PlayerLenght, board.getPositions(), board.getPlayer1().getPosition(), 8);
		boolean p2block = Utility.AstarSearch(AILength, board.getPositions(), board.getPlayer2().getPosition(), 0);

		if (p1block == false || p2block == false)
			valid = false;

		return valid;
	}

	public boolean isValid(StandardBoard board, Move move) {
		boolean valid = true;

		boolean hasWall = board.getCurrentPlayer().hasWalls();
		Position pos = board.getPosition(move.getX(), move.getY());
		boolean wallPlacement = board.wallPlacementIsValid(pos, move.getOrientation());
		boolean validPawnMove = board.isValidMove(board.getCurrentPlayer(), move.getX(), move.getY());

		if (move.getOrientation() == WallPlacement.NULL) {
			if (!validPawnMove) {
				valid = false;
			} else if (move.getX() == board.getPreviousPlayer().getPosition().getX()
					&& move.getY() == board.getPreviousPlayer().getPosition().getY()) {
				valid = false;
			}
			;
		} else {
			if (hasWall == false || wallPlacement == false) {
				valid = false;
			}
		}
		return valid;
	}

	private void move(StandardBoard Board, Move move) {

		if (move.getOrientation() == WallPlacement.NULL) {
			Board.getCurrentPlayer().pushPreviousPos();
			Board.getCurrentPlayer().setPosition(Board.getPosition(move.getX(), move.getY()));
			Board.switchPlayer();
		} else {
			int topLeftX = move.getX();
			int topLeftY = move.getY();
			Position topLeft = Board.getPosition(topLeftX, topLeftY);
			Board.assignWallsFromTopLeftClockwise(topLeft, move.getOrientation());
			Board.switchPlayer();
		}

	}

	private void unmove(StandardBoard Board, Move move) {

		if (move.getOrientation() == WallPlacement.NULL) {
			Board.switchPlayer();
			Position last = Board.getCurrentPlayer().getPreviousPos();
			Board.getCurrentPlayer().setPosition(last);
		} else {
			int topLeftX = move.getX();
			int topLeftY = move.getY();

			Position topLeft = Board.getPosition(topLeftX, topLeftY);
			Board.unassignWalls(topLeft, move.getOrientation());
			Board.switchPlayer();
		}

	}

	public ArrayList<Move> PossiblePawnMoves(StandardBoard currentBoard) {
		ArrayList<Move> PossiblePawnMoves = new ArrayList<Move>(5);
		ArrayList<Position> availablePositions = currentBoard.getCurrentPlayerOccupiablePositions();
		if (availablePositions.size() > 0) {
			for (Position pos : availablePositions) {
				PossiblePawnMoves.add(new Move(pos.getX(), pos.getY(), WallPlacement.NULL));
			}
		}
		return PossiblePawnMoves;
	}

	public ArrayList<Move> PossibleMoves(StandardBoard currentBoard) {
		ArrayList<Move> PossibleMoves = new ArrayList<Move>(132);
		PossibleMoves.addAll(PossiblePawnMoves(currentBoard));
		PossibleMoves.addAll(PossibleWallMoves);
		return PossibleMoves;
	}

	public ArrayList<Move> PossibleWallMoves() {
		return PossibleWallMoves;
	}

}
