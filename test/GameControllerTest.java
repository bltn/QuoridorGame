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
		gui.setController(controller);
	}

	/**
	 * Test placeWall method
	 */
	@Test
	public void placeWallTest() {

		controller.placeWall(4, 5, PositionWallLocation.BOTTOM, 5, 5, PositionWallLocation.BOTTOM, 4, 6, PositionWallLocation.TOP, 5, 6, PositionWallLocation.TOP);

		assertEquals(true, board.getPosition(4, 5).hasBottomWall());
		assertEquals(true, board.getPosition(5, 5).hasBottomWall());
		assertEquals(true, board.getPosition(4, 6).hasTopWall());
		assertEquals(true, board.getPosition(5, 6).hasTopWall());
		assertEquals(9, controller.getPreviousPlayer().getWallCount());
		assertEquals(1, controller.getPreviousPlayer().getMoveCount());

		controller.placeWall(3, 0, PositionWallLocation.RIGHT, 3, 1, PositionWallLocation.RIGHT, 4, 0, PositionWallLocation.LEFT, 4, 1, PositionWallLocation.LEFT);

		assertEquals(true, board.getPosition(3, 0).hasRightWall());
		assertEquals(true, board.getPosition(3, 1).hasRightWall());
		assertEquals(true, board.getPosition(4, 0).hasLeftWall());
		assertEquals(true, board.getPosition(4, 1).hasLeftWall());
		assertEquals(9, controller.getPreviousPlayer().getWallCount());
		assertEquals(1, controller.getPreviousPlayer().getMoveCount());
	}

	/**
	 * Test movePawn method.
	 */
	@Test
	public void movePawnTest() {
		// move pawn across to the right
		int oneAcross = controller.getCurrentPlayer().getPosition().getX() + 1;
		int expectedY = controller.getCurrentPlayer().getPosition().getY();
		int initialMoveCount = controller.getCurrentPlayer().getMoveCount();
		controller.movePawn(oneAcross, controller.getCurrentPlayer().getPosition().getY());
		assertEquals(oneAcross, controller.getPreviousPlayer().getPosition().getX());
		assertEquals(expectedY, controller.getPreviousPlayer().getPosition().getY());
		assertEquals((initialMoveCount + 1), controller.getPreviousPlayer().getMoveCount());

		// attempt to move pawn illegally
		int initialX = controller.getCurrentPlayer().getPosition().getX();
		int initialY = controller.getCurrentPlayer().getPosition().getY();
		Player startingPlayer = controller.getCurrentPlayer();
		controller.movePawn(5, 5);
		assertEquals(initialX, controller.getCurrentPlayer().getPosition().getX());
		assertEquals(initialY, controller.getCurrentPlayer().getPosition().getY());
		assertEquals(startingPlayer, controller.getCurrentPlayer());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// STUB: no operation required.
	}
}
