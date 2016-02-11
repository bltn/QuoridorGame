import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Thai Con
 * @author Ben Lawton
 */
public class BoardTest {

	private static Board board;

	@BeforeClass
	public static void setUp() {
		board = new Board();
	}

	@Test
	public void testGetPosition() {
		int x = 0;
		int y = 8;

		assertEquals(x, board.getPosition(x, y).getX());
		assertEquals(y, board.getPosition(x, y).getY());
	}

	@Test
	public void testGetOccupiablePositions() {
		Position topMiddle = board.getPosition(4, 0);
		ArrayList<Position> occupiablePositions = board.getOccupiablePositions(topMiddle);

		assertEquals(3, occupiablePositions.size());
		assertEquals(true, containsCoordinates(occupiablePositions, 5, 0));
		assertEquals(true, containsCoordinates(occupiablePositions, 3, 0));
		assertEquals(true, containsCoordinates(occupiablePositions, 4, 1));
	}

	@Test
	/**
	 * Tests that board initialisation assigns borders to the correct positions
	 */
	public void testBoardInitialisation() {
		assertEquals(true, (board.getPosition(4, 0).hasTopWall() && board.getPosition(4,0).isTop()));
		assertEquals(true, (board.getPosition(4, 8).hasBottomWall() && board.getPosition(4, 8).isBottom()));
		assertEquals(true, board.getPosition(0, 4).hasLeftWall());
		assertEquals(true, board.getPosition(8, 4).hasRightWall());
		// make sure inner positions don't get any premature wall assignments
		assertEquals(false, board.getPosition(4, 4).hasTopWall());
	}

	/**
	 * @param positions the collection of positions
	 * @param x the x coordinate of the position being searched for
	 * @param y the y coordinate of the position being searched for
	 * @return whether or not the given ArrayList of positions contains a position with the given coordinates
	 */
	private boolean containsCoordinates(ArrayList<Position> positions, int xCoord, int yCoord) {
		boolean containsCoordinates = false;
		for (Position pos : positions) {
			if (pos.getY() == yCoord && pos.getX() == xCoord) {
				containsCoordinates = true;
			}
		}
		return containsCoordinates;
	}
}
