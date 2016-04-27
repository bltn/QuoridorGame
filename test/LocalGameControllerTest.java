import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LocalGameControllerTest {
	private Board board;
	private GUIMock gui;
	@SuppressWarnings("rawtypes")
	private LocalGameController controller;

	@Before
	public void setup() {
		board = new StandardBoard(2);
		gui = new GUIMock();
		controller = new LocalGameController<>(gui, board);
		gui.setController(controller);
	}

	@Test
	public void removeWallTest() {
		board = new ChallengeBoard(2);

		int player1InitialWallCount = board.getPlayer1().getWallCount();
		int player2InitialMoveCount = board.getPlayer2().getMoveCount();

		controller.placeWall(4, 5, WallPlacement.HORIZONTAL, 1);
		controller.removeWall(4, 5, WallPlacement.HORIZONTAL, 2);

		assertEquals(false, board.getPosition(4, 5).hasBottomWall());
		assertEquals(false, board.getPosition(5, 5).hasBottomWall());
		assertEquals(false, board.getPosition(4, 6).hasTopWall());
		assertEquals(false, board.getPosition(5, 6).hasTopWall());
		assertEquals(player1InitialWallCount, board.getPlayer1().getWallCount());
		assertEquals(player2InitialMoveCount, board.getPlayer2().getMoveCount());
	}

	/**
	 * Test placeWall method
	 */
	@Test
	public void placeWallTest() {
		int initialWallCount = board.getPlayer1().getWallCount();
		int initialMoveCount = board.getPlayer1().getMoveCount();
		controller.placeWall(4, 5, WallPlacement.HORIZONTAL, 1);

		assertEquals(true, board.getPosition(4, 5).hasBottomWall());
		assertEquals(true, board.getPosition(5, 5).hasBottomWall());
		assertEquals(true, board.getPosition(4, 6).hasTopWall());
		assertEquals(true, board.getPosition(5, 6).hasTopWall());
		assertEquals((initialWallCount - 1), board.getPlayer1().getWallCount());
		assertEquals((initialMoveCount + 1), board.getPlayer1().getMoveCount());
	}

	/**
	 * Test movePawn method.
	 */
	@Test
	public void movePawnTest() {
		// coordinates for position one to the right, where the player will be moved to
		int expectedX = controller.getCurrentPlayer().getPosition().getX() + 1;
		int expectedY = controller.getCurrentPlayer().getPosition().getY();

		int initialMoveCount = controller.getCurrentPlayer().getMoveCount();

		controller.movePawn(expectedX, expectedY, controller.getCurrentPlayer().getID());

		assertEquals(expectedX, board.getPreviousPlayer().getPosition().getX());
		assertEquals(expectedY, board.getPreviousPlayer().getPosition().getY());
		assertEquals((initialMoveCount + 1), board.getPreviousPlayer().getMoveCount());
	}
}
