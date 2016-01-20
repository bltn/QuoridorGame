import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		board = new Board();
	}
	
	@Test
	public void testInitialisation() {
		assertEquals(true, board.getPosition(4, 0).isTop());
	}
	
	@Test
	public void testGetOccupiablePositions() {
		Position pos = board.getPosition(4, 0);
		assertEquals(3, board.getOccupiablePositions(pos).size());
	}
	
	@Test public void testGetPosition() {
		Position position = board.getPosition(4, 1);
		assertEquals(4, position.getX());
		assertEquals(1, position.getY());
	}

}
