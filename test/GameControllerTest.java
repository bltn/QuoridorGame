import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameControllerTest {
	private static Board board;
	private static BoardGUI gui;
	private static GameController controller;

	@BeforeClass
	public static void setUp() {
		board = new Board();
		gui = new BoardGUI();
		controller = new GameController<>(gui, board);
	}
	/**
	 * Test placeWall method
	 */
	@Test
	public void placeWallTest() {

		GameController.placeWall(5, 5, -1, 4, 5, 1, 5, 6, -1, 4, 6, -1);
		GameController.placeWall(5, 5, 0, 5, 6, 2, 5, 6, -1, 4, 6, -1);

		assertEquals(true, board.getPosition(5, 5).hasLeftWall());
		assertEquals(true, board.getPosition(4, 5).hasRightWall());
		assertEquals(true, board.getPosition(5, 5).hasTopWall());
		assertEquals(true, board.getPosition(5, 6).hasBottomWall());
		assertEquals(9, controller.getCurrentPlayer().getWallCount());
		assertEquals(1, controller.getCurrentPlayer().getMoveCount());
	}

	/** 
	 * Test movePawn method.
	 */
	@Test
	public void movePawnTest() {
		GameController.movePawn(5, 6);
		GameController.movePawn(7, 6);

		assertEquals(5, controller.getCurrentPlayer().getX());
		assertEquals(6, controller.getCurrentPlayer().getY());

		controller.movePawn(1, 2);

		assertEquals(7, controller.getCurrentPlayer().getX());
		assertEquals(6, controller.getCurrentPlayer().getY());
	}
	
	/**
	 * Test isValidMove method.
	 */
	@Test
	public void isValidMoveTest(){
		GameController.placeWall(5, 0, 0, 4, 0, 0, 6, 0, 0, 7, 0, 0);
		GameController.placeWall(5, 5, 0, 5, 6, 2, 5, 6, -1, 4, 6, -1);

		assertEquals(false, GameController.isValidMove(controller.getCurrentPlayer(), 4, 1));
	}
}
