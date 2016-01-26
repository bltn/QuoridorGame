import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void test() {
		Player aCon = new Player(2,5);
		
		assertEquals(2, aCon.getX());
		assertEquals(5, aCon.getY());
		
		aCon.decrementWallCount();		
		assertEquals(9, aCon.getWallCount());

		
		int move = aCon.getMoveCount();
	    aCon.incrementMoveCount();
		assertEquals(move+1, aCon.getMoveCount());

		aCon.incrementMoveCount();
		aCon.incrementMoveCount();
		aCon.incrementMoveCount();
		assertEquals(move+4, aCon.getMoveCount());
		
	}

}
