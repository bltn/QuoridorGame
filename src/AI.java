
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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

	public Move Minimax(StandardBoard board, int depth) {
		int highestScore = -10000;
		Move bestMove = null;

		ArrayList<Move> moves = PossibleMoves(board);
		Iterator<Move> iterator = moves.iterator();

		while (iterator.hasNext()) {
			Move move = iterator.next();
			if (isValid(board, move) == false)
				continue;
			StandardBoard result = move(board, move);

			if (isBlock(board, move) == true) {
				if(highestScore<Min(board, -10000, 10000, depth - 1)){
					highestScore=Min(board, -10000, 10000, depth - 1);
					bestMove = move;
				}
			}

			unmove(board, move);
		}
		return bestMove;
	}

	private int Min(StandardBoard board, int a, int b, int depth) {
		if (depth == 0) {
			return evaluate(board);
		}

		int lowestScore = 10000;
		ArrayList<Move> moves = PossibleMoves(board);

		Iterator<Move> iterator = moves.iterator();

		while (iterator.hasNext()) {

			Move move = iterator.next();
			if (isValid(board, move) == false)
				continue;
			StandardBoard result = move(board, move);

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

		int highestScore = -10000;
		ArrayList<Move> moves = PossibleMoves(board);
		Iterator<Move> iterator = moves.iterator();

		while (iterator.hasNext()) {
			Move move = iterator.next();

			if (isValid(board, move) == false)
				continue;
			StandardBoard result = move(board, move);

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

		int PlayerLenght = Utility.shortestPathLenght(board.getPositions(), board.getPlayer1().getPosition(), 8);//8

		int AILength = Utility.shortestPathLenght(board.getPositions(), board.getPlayer2().getPosition(),0);//0
		Random random = new Random();
		int randomNumber = random.nextInt(2);
		return (PlayerLenght- AILength)+ randomNumber;
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
			}
		} else {
			if (hasWall == false || wallPlacement == false) {
				valid = false;
			}
		}
		return valid;
	}

	// Return a new board after the move apply to current board
	public StandardBoard move(StandardBoard Board, Move move) {

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
		return Board;
	}

	public static StandardBoard unmove(StandardBoard Board, Move move) {

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
		return Board;
	}

	public StandardBoard clone(StandardBoard Board) {

		StandardBoard clone = new StandardBoard(2);
		Position[][] clonePositions = Utility.clone(Board.getPositions());
		clone.setPositions(clonePositions);

		clone.setPlayer1(new Player(Board.getPlayer1().getPosition(), Board.getPlayer1().getID()));
		clone.getPlayer1().setWallCount(Board.getPlayer1().getWallCount());
		clone.setPlayer2(new Player(Board.getPlayer2().getPosition(), Board.getPlayer2().getID()));
		clone.getPlayer2().setWallCount(Board.getPlayer2().getWallCount());
		if (Board.getCurrentPlayer() == Board.getPlayer1()) {
			clone.setCurrentPlayer(clone.getPlayer1());
		} else {
			clone.setCurrentPlayer(clone.getPlayer2());
		}
		return clone;
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

	public StandardBoard getBoard() {
		return AIBoard;
	}

	public void setBoard(StandardBoard AIBoard) {
		this.AIBoard = AIBoard;
	}

	public static void main(String[] args) {
		StandardBoard board = new StandardBoard(2);
		Position pos = board.getPositions()[0][4];
		Position pos2 = board.getPositions()[4][0];
		AI AI = new AI(board);

		Move move3 = new Move(pos2.getX(), pos2.getY(), WallPlacement.VERTICAL);
		Move move4 = new Move(pos.getX(), pos.getY(), WallPlacement.VERTICAL);
		Move move2 = new Move(pos2.getX(), pos2.getY(), WallPlacement.HORIZONTAL);
		Move move = new Move(pos.getX(), pos.getY(), WallPlacement.HORIZONTAL);

		StandardBoard result1 = AI.move(board, move2);
		StandardBoard result2 = AI.move(result1, move3);
		StandardBoard result3 = AI.move(result2, move4);
		StandardBoard result = AI.move(result3, move);
	}

}
