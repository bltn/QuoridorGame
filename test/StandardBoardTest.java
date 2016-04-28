import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class StandardBoardTest {

	private Board board;
    private Player player1;
    private Player player2;

	@Before
	public void setUp() {
		board = new StandardBoard(false);
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

		assertEquals(3, occupiablePositions.size());
		if (board.getCurrentPlayer().getID() == 1) {
			assertEquals(true, containsCoordinates(occupiablePositions, 5, 0));
			assertEquals(true, containsCoordinates(occupiablePositions, 3, 0));
			assertEquals(true, containsCoordinates(occupiablePositions, 4, 1));
		} else if (board.getCurrentPlayer().getID() == 2) {
			assertEquals(true, containsCoordinates(occupiablePositions, 4, 7));
			assertEquals(true, containsCoordinates(occupiablePositions, 5, 8));
			assertEquals(true, containsCoordinates(occupiablePositions, 3, 8));
		}
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

    @Test (expected = IllegalArgumentException.class)
    /**
     * Test that move pawn is working correctly.
     */
    public void movePawnTest() {
        // test normal moves
        board.movePawn(4, 1);
        assertEquals(player1.getPosition(), board.getPosition(4, 1));
        // test an illegal move before the next valid move
        board.movePawn(4, 9);
        assertEquals(player2.getPosition(), board.getPosition(4, 8));
        board.movePawn(4, 7);
        assertEquals(player2.getPosition(), board.getPosition(4, 7));
        // test if two pawns are prevented from occupying the same position
        board.movePawn(4, 2);
        board.movePawn(4, 6);
        board.movePawn(4, 3);
        board.movePawn(4, 5);
        board.movePawn(4, 4);
        board.movePawn(4, 4);
        assertEquals(player1.getPosition(), board.getPosition(4, 4));
        assertEquals(player2.getPosition(), board.getPosition(4, 5));
    }

    @Test
    public void placeVerticalWallTest() {
    	int topLeftX = 2;
    	int topLeftY = 5;

    	Position topLeft = board.getPosition(topLeftX, topLeftY);
    	Position topRight = board.getPosition((topLeftX + 1), topLeftY);
    	Position bottomRight = board.getPosition((topLeftX + 1), (topLeftY + 1));
    	Position bottomLeft = board.getPosition(topLeftX, (topLeftY + 1));

    	board.placeWalls(topLeftX, topLeftY, WallPlacement.VERTICAL);
    	assertEquals(true, topLeft.hasRightWall());
    	assertEquals(true, topRight.hasLeftWall());
    	assertEquals(true, bottomRight.hasLeftWall());
    	assertEquals(true, bottomLeft.hasRightWall());
    }

    @Test
    public void placeHorizontalWallTest() {
    	int topLeftX = 6;
    	int topLeftY = 2;

    	Position topLeft = board.getPosition(topLeftX, topLeftY);
    	Position topRight = board.getPosition((topLeftX + 1), topLeftY);
    	Position bottomRight = board.getPosition((topLeftX + 1), (topLeftY + 1));
    	Position bottomLeft = board.getPosition(topLeftX, (topLeftY + 1));

    	board.placeWalls(topLeftX, topLeftY, WallPlacement.HORIZONTAL);
    	assertEquals(true, topLeft.hasBottomWall());
    	assertEquals(true, topRight.hasBottomWall());
    	assertEquals(true, bottomLeft.hasTopWall());
    	assertEquals(true, bottomRight.hasTopWall());
    }

    @Test
    public void winningMoveTest() {
    	// move player1 to the top of his/her winning move
    	board.getPlayer1().setPosition(board.getPosition(7, 8));

    	boolean gameOver = board.movePawn(8, 8);
    	assertEquals(true, gameOver);
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
