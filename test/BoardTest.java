import static org.junit.Assert.*;

/**
 * @author Thai Con
 *
 */
public class BoardTest {

	@org.junit.Test
	public void Boardtest() {
		Board board = new Board();
		for (int x = 1; x < 9; x++) {
			for (int y = 1; y < 9; y++) {

				assertEquals(x, board.getPosition(x, y).getX());
				assertEquals(y, board.getPosition(x, y).getY());

			}
		}
	}
}
