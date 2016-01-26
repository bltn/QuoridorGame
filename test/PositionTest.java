import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {
	Position pos1 = new Position(2, 5);

	@Test
	public void getXTest() {
		assertEquals(2, pos1.getX());
	}

	public void getYTest() {
		assertEquals(5, pos1.getY());
	}

	public void hasBottomWallTesst() {
		assertEquals(false, pos1.hasBottomWall());
		pos1.placeBottomWall();
		assertEquals(true, pos1.hasBottomWall());
	}
	
	public void setTopTest(){
		
		pos1.setTop();
		assertEquals(true, pos1.isTop());
		
	}
}
