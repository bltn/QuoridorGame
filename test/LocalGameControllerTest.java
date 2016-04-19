import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class LocalGameControllerTest extends Application {
	private static Board board;
	private static LocalBoardGUI gui;
	private static LocalGameController controller;

	/**
	 * Test placeWall method
	 */
	@Test
	public void placeWallTest() {

		Thread t = new Thread("JavaFX initialisation") {
			public void run() {
				Application.launch(LocalGameControllerTest.class, new String[0]);
				Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        gui = new LocalBoardGUI();
                        gui.start(new Stage());
                    }
                });
				board = new StandardBoard();
				controller = new LocalGameController<>(gui, board);
				gui.setController(controller);

				controller.placeWall(4, 5, WallPlacement.HORIZONTAL, 1);

				assertEquals(true, board.getPosition(4, 5).hasBottomWall());
				assertEquals(true, board.getPosition(5, 5).hasBottomWall());
				assertEquals(true, board.getPosition(4, 6).hasTopWall());
				assertEquals(true, board.getPosition(5, 6).hasTopWall());
				assertEquals(9, board.getPreviousPlayer().getWallCount());
				assertEquals(1, board.getPreviousPlayer().getMoveCount());

				controller.placeWall(3, 0, WallPlacement.VERTICAL, 2);

				assertEquals(true, board.getPosition(3, 0).hasRightWall());
				assertEquals(true, board.getPosition(3, 1).hasRightWall());
				assertEquals(true, board.getPosition(4, 0).hasLeftWall());
				assertEquals(true, board.getPosition(4, 1).hasLeftWall());
				assertEquals(9, board.getPreviousPlayer().getWallCount());
				assertEquals(1, board.getPreviousPlayer().getMoveCount());
			}
		};
		t.setDaemon(true);
		t.start();
	}

	/**
	 * Test movePawn method.
	 */
	@Test
	public void movePawnTest() {
		Thread t = new Thread("JavaFX initialisation") {
			public void run() {
				Application.launch(LocalGameControllerTest.class, new String[0]);
				Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        gui = new LocalBoardGUI();
                        gui.start(new Stage());
                    }
                });
				board = new StandardBoard();
				controller = new LocalGameController<>(gui, board);
				gui.setController(controller);

				// move pawn across to the right
				int oneAcross = controller.getCurrentPlayer().getPosition().getX() + 1;
				int expectedY = controller.getCurrentPlayer().getPosition().getY();
				int initialMoveCount = controller.getCurrentPlayer().getMoveCount();

				controller.movePawn(oneAcross, controller.getCurrentPlayer().getPosition().getY(), controller.getCurrentPlayer().getID());

				assertEquals(oneAcross, board.getPreviousPlayer().getPosition().getX());
				assertEquals(expectedY, board.getPreviousPlayer().getPosition().getY());
				assertEquals((initialMoveCount + 1), board.getPreviousPlayer().getMoveCount());

				// attempt to move pawn illegally
				int initialX = controller.getCurrentPlayer().getPosition().getX();
				int initialY = controller.getCurrentPlayer().getPosition().getY();
				Player startingPlayer = controller.getCurrentPlayer();

				try {
					controller.movePawn(5, 5, controller.getCurrentPlayer().getID());
				} catch (IllegalArgumentException e) {/*Do nothing*/}

				assertEquals(initialX, controller.getCurrentPlayer().getPosition().getX());
				assertEquals(initialY, controller.getCurrentPlayer().getPosition().getY());
				assertEquals(startingPlayer, controller.getCurrentPlayer());
			}
		};
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// STUB: no operation required.
	}
}
