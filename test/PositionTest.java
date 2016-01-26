import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {

	@Test
	public void test() {
		Position pos1 = new Position(2, 5);

		assertEquals(2, pos1.getX());
		assertEquals(5, pos1.getY());

		assertEquals(false, pos1.hasBottomWall());
		pos1.placeBottomWall();
		assertEquals(true, pos1.hasBottomWall());
	}

}
