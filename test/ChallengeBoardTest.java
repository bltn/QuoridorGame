import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

public class ChallengeBoardTest {

	private static Board board;
	private static Player player1;
	private static Player player2;

	@BeforeClass
	public static void setUp() {
		board = new ChallengeBoard();
		player1 = board.getPlayer1();
		player2 = board.getPlayer2();
	}

	@Test
	public void testResetWalls() {
		Position position = board.getPosition(4, 0);
		position.setHasBottomWall(true);
		position.setHasRightWall(true);
		position.setHasLeftWall(true);
		board.addWalledOffPosition(position);
		board.resetWalledOffPositions();
		assertEquals(true, position.hasTopWall());
		assertEquals(false, position.hasBottomWall());
		assertEquals(false, position.hasRightWall());
		assertEquals(false, position.hasLeftWall());
	}

	@Test
	public void testGetOccupiablePositions() {
		ArrayList<Position> occupiablePositions = board.getCurrentPlayerOccupiablePositions();

		assertEquals(2, occupiablePositions.size());
		assertEquals(true, containsCoordinates(occupiablePositions, 1, 0));
		assertEquals(true, containsCoordinates(occupiablePositions, 0, 1));
	}

	@Test
	public void testBoardInitialisation() {
		assertEquals(true, (board.getPosition(0, 0).hasTopWall() && board.getPosition(0, 0).isTopCorner()));
		assertEquals(true, board.getPosition(4, 8).hasBottomWall());
		assertEquals(true, board.getPosition(0, 4).hasLeftWall());
		assertEquals(true, board.getPosition(8, 4).hasRightWall());
		assertEquals(true, board.getPosition(8, 8).isBottomCorner());
		// make sure inner positions don't get any premature wall assignments
		assertEquals(false, board.getPosition(4, 4).hasTopWall());
	}

	@Test
    /**
     * Test that the players switch turns correctly
     */
    public void testSwitchPlayer() {
        board.switchPlayer();
        assertEquals(player2, board.getCurrentPlayer());
        assertEquals(player1, board.getPreviousPlayer());
        board.switchPlayer();
        assertEquals(player1, board.getCurrentPlayer());
        assertEquals(player2, board.getPreviousPlayer());
    }

    /**
     * Test that move pawn is working correctly.
     */
	@Test (expected = IllegalArgumentException.class)
    public void movePawnTest() {
        // test normal moves
        board.movePawn(1, 0);
        assertEquals(player1.getPosition(), board.getPosition(1, 0));
        // test an illegal move before the next valid move
        board.movePawn(8, 7);
        assertEquals(player2.getPosition(), board.getPosition(8, 7));

        // make sure illegal moves don't change the players' positions
        board.movePawn(4, 2);
        board.movePawn(4, 6);
        board.movePawn(4, 3);
        board.movePawn(4, 5);
        board.movePawn(4, 4);
        board.movePawn(4, 4);
        assertEquals(player1.getPosition(), board.getPosition(1, 0));
        assertEquals(player2.getPosition(), board.getPosition(8, 7));
    }

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
