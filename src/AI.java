import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class AI {

	private StandardBoard AIBoard;
	private ArrayList<Move> PossibleWallMoves;
	private Stack<Position> previousPosPlayer1;
	private Stack<Position> previousPosPlayer2;

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

		previousPosPlayer1 = new Stack<Position>();
		previousPosPlayer2 = new Stack<Position>();

	}

	public Move Minimax(int depth) {
		int highestScore = -99999999;
		Move bestMove = null;

		ArrayList<Move> moves = PossibleMoves(AIBoard);
		for (Move move : moves) {
			if (isValid(AIBoard, move) == false)
				continue;
			move(AIBoard, move);

			int PlayerLenght = Utility.shortestPathLenght(AIBoard.getPositions(), AIBoard.getPlayer1().getPosition(),
					8);
			int AILength = Utility.shortestPathLenght(AIBoard.getPositions(), AIBoard.getPlayer2().getPosition(), 0);

			if (AILength > -1 && PlayerLenght > -1) {
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

			if (AILength == 0) {
				return 999999;
			} else if (PlayerLenght == 0) {
				return -999999;
			}

			return evaluate(PlayerLenght, AILength, board);
		}

		int lowestScore = 99999999;
		ArrayList<Move> moves = PossibleMoves(board);

		for (Move move : moves) {
			if (isValid(board, move) == false)
				continue;
			move(board, move);

			PlayerLenght = Utility.shortestPathLenght(AIBoard.getPositions(), AIBoard.getPlayer1().getPosition(), 8);
			AILength = Utility.shortestPathLenght(AIBoard.getPositions(), AIBoard.getPlayer2().getPosition(), 0);

			if (AILength > -1 && PlayerLenght > -1) {

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
			if (AILength == 0) {
				return 999999;

			} else if (PlayerLenght == 0) {
				return -999999;
			}

			return evaluate(PlayerLenght, AILength, board);
		}

		int highestScore = -99999999;
		ArrayList<Move> moves = PossibleMoves(board);

		for (Move move : moves) {
			if (isValid(board, move) == false)
				continue;
			move(board, move);

			PlayerLenght = Utility.shortestPathLenght(AIBoard.getPositions(), AIBoard.getPlayer1().getPosition(), 8);
			AILength = Utility.shortestPathLenght(AIBoard.getPositions(), AIBoard.getPlayer2().getPosition(), 0);

			if (AILength > -1 && PlayerLenght > -1) {
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
		Random random = new Random();
		int AIWall = board.getPlayer2().getWallCount();
		int randomNumber = random.nextInt(10) + 1;
		return (35 * PlayerLenght - 45 * AILength) - AIManhata + AIWall * 40 + randomNumber;//
	}

	private int evaluateNoWall(Board board) {
		int AILength = Utility.shortestPathLenght(board.getPositions(), board.getPlayer2().getPosition(), 0);
		return -25 * AILength;
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

			if(Board.getCurrentPlayer()==Board.getPlayer1()){
				previousPosPlayer1.push(Board.getCurrentPlayer().getPosition());
			}
			else{
				previousPosPlayer2.push(Board.getCurrentPlayer().getPosition());
			};

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
			Position last=null;
			if(Board.getCurrentPlayer()==Board.getPlayer1()){
				last=previousPosPlayer1.pop();
			}
			else{
				last=previousPosPlayer2.pop();
			};


			//Position last = Board.getCurrentPlayer().getPreviousPos();
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

	public Move MoveNoWalls() {
		int highestScore = -99999999;
		Move bestMove = null;

		ArrayList<Move> moves = PossiblePawnMoves(AIBoard);

		for (Move move : moves) {
			if (isValid(AIBoard, move) == false)
				continue;
			move(AIBoard, move);

			int score = evaluateNoWall(AIBoard);

			if (highestScore < score) {
				highestScore = score;
				bestMove = move;
			}

			unmove(AIBoard, move);
		}
		return bestMove;
	}





}
