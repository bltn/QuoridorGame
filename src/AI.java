
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
		
		for (Move move : moves) {
			if (isValid(AIBoard, move) == false)
				continue;
			move(AIBoard, move);

			if (isBlock(AIBoard, move) == true) {
				if (highestScore < Min(AIBoard, -99999999, 99999999, depth - 1)) {
					highestScore = Min(AIBoard, -99999999, 99999999, depth - 1);
					bestMove = move;
				}
			}

			unmove(AIBoard, move);
		}
		return bestMove;
	}

	private int Min(StandardBoard board, int a, int b, int depth) {
		if (depth == 0) {
			return evaluate(board);
		}

		int lowestScore = 99999999;
		ArrayList<Move> moves = PossibleMoves(board);

		for (Move move : moves) {
			if (isValid(board, move) == false)
				continue;
			move(board, move);

			if (isBlock(board, move) == true) {

				lowestScore = Math.min(Max(board, a, b, depth - 1), lowestScore);
				b = Math.min(b, lowestScore);
			}
			unmove(board, move);
			if (b <= a)
				break;
		}
		return lowestScore;

	}

	private int Max(StandardBoard board, int a, int b, int depth) {
		if (depth == 0) {
			return evaluate(board);
		}

		int highestScore = -99999999;
		ArrayList<Move> moves = PossibleMoves(board);
		
		for (Move move : moves) {

			if (isValid(board, move) == false)
				continue;
			move(board, move);

			if (isBlock(board, move) == true) {

				highestScore = Math.max(Min(board, a, b, depth - 1), highestScore);
				a = Math.max(a, highestScore);
			}
			unmove(board, move);

			if (b <= a)
				break;
		}

		return highestScore;
	}

	private int evaluate(Board board) {

		int PlayerLenght = Utility.shortestPathLenght(board.getPositions(), board.getPlayer1().getPosition(), 8);// 8
		int AILength = Utility.shortestPathLenght(board.getPositions(), board.getPlayer2().getPosition(), 0);// 0
		int AIManhata = board.getPlayer2().getPosition().getY() - 0;
		//int PlayerManhata = 8 - board.getPlayer1().getPosition().getY();
		Random random = new Random();
		int randomNumber = random.nextInt(10) + 1;
		return (30 * PlayerLenght - 50 * AILength) - AIManhata + randomNumber;
	}

	public boolean isBlock(StandardBoard board, Move move) {
		boolean valid = true;

		boolean p1block = Utility.AstarSearch(board.getPositions(), board.getPlayer1().getPosition(), 8);
		boolean p2block = Utility.AstarSearch(board.getPositions(), board.getPlayer2().getPosition(), 0);

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
			
		} else {
			if (hasWall == false || wallPlacement == false) {
				valid = false;
			}
		}
		return valid;
	}

	private void move(StandardBoard Board, Move move) {

		if (move.getOrientation() == WallPlacement.NULL) {
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

	private void unmove(StandardBoard AIboard, Move move) {

		if (move.getOrientation() == WallPlacement.NULL) {
			AIboard.switchPlayer();
			Position last = AIboard.getCurrentPlayer().getPreviousPos();
			AIboard.getCurrentPlayer().setPosition(last);
		} else {
			int topLeftX = move.getX();
			int topLeftY = move.getY();

			Position topLeft = AIboard.getPosition(topLeftX, topLeftY);
			AIboard.unassignWalls(topLeft, move.getOrientation());
			AIboard.switchPlayer();
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
