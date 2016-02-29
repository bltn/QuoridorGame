import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameControllerTest extends Application {
	private static Board board;
	private static BoardGUI gui;
	private static GameController controller;

	@BeforeClass
	public static void setUpClass() {
		Thread t = new Thread("JavaFX initialisation") {
			public void run() {
				Application.launch(GameControllerTest.class, new String[0]);
			}
		};
		t.setDaemon(true);
		t.start();
		board = new Board();
		gui = new BoardGUI();
		controller = new GameController<>(gui, board);
	}

	/**
	 * Test placeWall method
	 */
	@Test
	public void placeWallTest() {

		GameController.placeWall(4, 5, PositionWallLocation.BOTTOM, 5, 5, PositionWallLocation.BOTTOM, 4, 6, PositionWallLocation.TOP, 5, 6, PositionWallLocation.TOP);

		assertEquals(true, board.getPosition(4, 5).hasBottomWall());
		assertEquals(true, board.getPosition(5, 5).hasBottomWall());
		assertEquals(true, board.getPosition(4, 6).hasTopWall());
		assertEquals(true, board.getPosition(5, 6).hasTopWall());
		assertEquals(9, GameController.getPreviousPlayer().getWallCount());
		assertEquals(1, GameController.getPreviousPlayer().getMoveCount());

		GameController.placeWall(3, 0, PositionWallLocation.RIGHT, 3, 1, PositionWallLocation.RIGHT, 4, 0, PositionWallLocation.LEFT, 4, 1, PositionWallLocation.LEFT);

		assertEquals(true, board.getPosition(3, 0).hasRightWall());
		assertEquals(true, board.getPosition(3, 1).hasRightWall());
		assertEquals(true, board.getPosition(4, 0).hasLeftWall());
		assertEquals(true, board.getPosition(4, 1).hasLeftWall());
		assertEquals(9, GameController.getPreviousPlayer().getWallCount());
		assertEquals(1, GameController.getPreviousPlayer().getMoveCount());
	}

	/**
	 * Test movePawn method.
	 */
	@Test
	public void movePawnTest() {
		// move pawn across to the right
		int oneAcross = GameController.getCurrentPlayer().getX() + 1;
		int expectedY = GameController.getCurrentPlayer().getY();
		int initialMoveCount = GameController.getCurrentPlayer().getMoveCount();
		GameController.movePawn(oneAcross, GameController.getCurrentPlayer().getY());
		assertEquals(oneAcross, GameController.getPreviousPlayer().getX());
		assertEquals(expectedY, GameController.getPreviousPlayer().getY());
		assertEquals((initialMoveCount + 1), GameController.getPreviousPlayer().getMoveCount());

		// attempt to move pawn illegally
		int initialX = GameController.getCurrentPlayer().getX();
		int initialY = GameController.getCurrentPlayer().getY();
		Player startingPlayer = GameController.getCurrentPlayer();
		GameController.movePawn(5, 5);
		assertEquals(initialX, GameController.getCurrentPlayer().getX());
		assertEquals(initialY, GameController.getCurrentPlayer().getY());
		assertEquals(startingPlayer, GameController.getCurrentPlayer());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// STUB: no operation required.
	}
}
