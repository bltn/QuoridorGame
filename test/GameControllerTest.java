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

	@Test
	public void placeWallTest() {

		Control.placeWall(5, 5, -1, 4, 5, 1);
		Control.placeWall(5, 5, 0, 5, 6, 2);

		assertEquals(true, Control.getBoard().getPosition(5, 5).hasLeftWall());
		assertEquals(true, Control.getBoard().getPosition(4, 5).hasRightWall());
		assertEquals(true, Control.getBoard().getPosition(5, 5).hasTopWall());
		assertEquals(true, Control.getBoard().getPosition(5, 6).hasBottomWall());
		assertEquals(9, Control.getCurrentPlayer().getWallCount());
		assertEquals(1, Control.getCurrentPlayer().getMoveCount());


	}
	public void movePawnTest(){
	Control.movePawn(5, 6);
	Control.movePawn(7, 6);

	assertEquals(5, Control.getCurrentPlayer().getX());
	assertEquals(6, Control.getCurrentPlayer().getY());

	Control.movePawn(1, 2);

	assertEquals(7, Control.getCurrentPlayer().getX());
	assertEquals(6, Control.getCurrentPlayer().getY());}
}
