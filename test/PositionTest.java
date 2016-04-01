import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTest {
	private static Position pos1;
    private static Position pos2;

	@BeforeClass
	public static void setUp() {
		pos1 = new Position(2, 5);
        pos2 = new Position(5, 8);
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
	public void hasBottomWallTest() {
		assertEquals(false, pos1.hasBottomWall());
		pos1.setHasBottomWall(true);
		assertEquals(true, pos1.hasBottomWall());
	}

    @Test
    public void hasLeftWallTest() {
        assertEquals(false, pos1.hasLeftWall());
        pos1.setHasLeftWall(true);
        assertEquals(true, pos1.hasLeftWall());
    }

    @Test
    public void hasRightWallTest() {
        assertEquals(false, pos2.hasRightWall());
        pos2.setHasRightWall(true);
        assertEquals(true, pos2.hasRightWall());
    }

    @Test
    public void hasTopWallTest() {
        assertEquals(false, pos1.hasTopWall());
        pos1.setHasTopWall(true);
        assertEquals(true, pos1.hasTopWall());
    }

	@Test
	public void setTopTest() {
        assertEquals(false, pos1.isTop());
		pos1.setTop();
		assertEquals(true, pos1.isTop());

	}

    @Test
    public void setBottomTest() {
        assertEquals(false, pos2.isBottom());
        pos2.setBottom();
        assertEquals(true, pos2.isBottom());
    }
}
