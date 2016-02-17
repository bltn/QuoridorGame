/**
 * @author Ben Lawton
 * @author Thai Hoang
 */
import java.util.ArrayList;


public class Board {

	// 2D array for Positions
	private Position positions[][];

	// Positions with walls assigned to them, tracked for a more efficient reset
	private ArrayList<Position> walledOffPositions;

	/**
	 * Constructor for an object of class Board
	 */
	public Board() {
		initialiseBoard();
		walledOffPositions = new ArrayList<Position>();
	}

	/**
	 * Return all occupiable positions to the top, bottom, right or left of the given position
	 * @param the position (more than likey) occupied by the current player
	 * @return occupiable positions
	 */
	public ArrayList<Position> getOccupiablePositions(Position position) {
		ArrayList<Position> localPositions = new ArrayList<Position>();
		if (!position.hasTopWall()) {
			localPositions.add(positions[position.getY()-1][position.getX()]);
		}
		if (!position.hasRightWall()) {
			localPositions.add(positions[position.getY()][position.getX()+1]);
		}
		if (!position.hasBottomWall()) {
			localPositions.add(positions[position.getY()+1][position.getX()]);
		}
		if (!position.hasLeftWall()) {
			localPositions.add(positions[position.getY()][position.getX()-1]);
		}
		return localPositions;
	}

	/**
	 * Adds a position to the collection of positions with walls assigned to them
	 * @param pos position with wall assigned to it
	 */
	public void addWalledOffPosition(Position pos) {
		if (!walledOffPositions.contains(pos)) {
			walledOffPositions.add(pos);
		}
	}

	/**
	 * Removes all non-border walls from positions with walls assigned to them
	 */
	public void resetWalledOffPositions() {
		for (Position pos : walledOffPositions) {
			if (pos.getY() != 0) {
				pos.setHasTopWall(false);
			}
			if (pos.getX() != 8) {
				pos.setHasRightWall(false);
			}
			if (pos.getX() != 0) {
				pos.setHasLeftWall(false);
			}
			if (pos.getY() != 8) {
				pos.setHasBottomWall(false);
			}
		}
	}

	/**
	 * @param posX x coordinates of the position
	 * @param posY y coordinates of the position
	 * @return position at the given coordinates
	 */
	public Position getPosition(int posX, int posY) {
		return positions[posY][posX];
	}

	/**
	 * Assign borders to the board and set top and bottom grids as winning positions
	 */
	private void initialiseBoard() {
		positions = new Position[9][9];

		//initialise Position objects
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				positions[y][x] = new Position(x, y);
			}
		}
		//mark top positions as winners
		for (int x = 0; x < 9; x++) {
			positions[0][x].setTop();
		}
		//mark bottom positions as winners
		for (int x = 0; x < 9; x++) {
			positions[8][x].setBottom();
		}

		//set the board's top borders (walls)
		for (int x = 0; x < 9; x++) {
			positions[0][x].setHasTopWall(true);
		}
		//set the board's right borders (walls)
		for (int y = 0; y < 9; y++) {
			positions[y][8].setHasRightWall(true);
		}
		//set the board's bottom borders (walls)
		for (int x = 0; x < 9; x++) {
			positions[8][x].setHasBottomWall(true);
		}
		//set the board's left borders (walls)
		for (int y = 0; y < 9; y++) {
			positions[y][0].setHasLeftWall(true);
		}
	}
}
