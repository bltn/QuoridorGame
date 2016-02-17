import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTest {
	private static Position pos1;

	@BeforeClass
	public static void setUp() {
		pos1 = new Position(2, 5);
	}

	@Test
	public void getXTest() {
		assertEquals(2, pos1.getX());
	}

	@Test
	public void getYTest() {
		assertEquals(5, pos1.getY());
	}

	@Test
	public void hasBottomWallTesst() {
		assertEquals(false, pos1.hasBottomWall());
		pos1.setHasBottomWall(true);
		assertEquals(true, pos1.hasBottomWall());
	}

	@Test
	public void setTopTest(){

		pos1.setTop();
		assertEquals(true, pos1.isTop());

	}
}
