import static org.junit.Assert.*;

import org.junit.Test;

public class ControllerTest {

	@Test
	public void ControllerTest() {
		Board board = new Board();
		BoardGUI gui = new BoardGUI();

		GameController Control = new GameController<>(gui, board);

		Control.placeWall(5, 5, -1, 4, 5, 1);
		Control.placeWall(5, 5, 0, 5, 6, 2);

		assertEquals(true, Control.getBoard().getPosition(5, 5).hasLeftWall());
		assertEquals(true, Control.getBoard().getPosition(4, 5).hasRightWall());
		assertEquals(true, Control.getBoard().getPosition(5, 5).hasTopWall());
		assertEquals(true, Control.getBoard().getPosition(5, 6).hasBottomWall());
		assertEquals(9, Control.getCurrentPlayer().getWallCount());
		assertEquals(1, Control.getCurrentPlayer().getMoveCount());

		Control.movePawn(5, 6);
		Control.movePawn(7, 6);

		assertEquals(5, Control.getCurrentPlayer().getX());
		assertEquals(6, Control.getCurrentPlayer().getY());

		Control.movePawn(1, 2);

		assertEquals(7, Control.getCurrentPlayer().getX());
		assertEquals(6, Control.getCurrentPlayer().getY());
	}

}
