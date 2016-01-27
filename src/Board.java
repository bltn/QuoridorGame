/**
 * @author Ben Lawton
 * @author Thai Hoang
 */
import java.util.ArrayList;


public class Board {

	// 2D array for Positions
	private Position positions[][];

	/**
	 * Constructor for an object of class Board
	 */
	public Board() {
		initialiseBoard();
	}

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

	public Position getPosition(int posX, int posY) {
		return positions[posY][posX];
	}

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
			positions[0][x].placeTopWall();
		}
		//set the board's right borders (walls)
		for (int y = 0; y < 9; y++) {
			positions[y][8].placeRightWall();
		}
		//set the board's bottom borders (walls)
		for (int x = 0; x < 9; x++) {
			positions[8][x].placeBottomWall();
		}
		//set the board's left borders (walls)
		for (int y = 0; y < 9; y++) {
			positions[y][0].placeLeftWall();
		}
	}
}
