import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	Player aCon = new Player(2,5);
	
	@Test
	public void getXTest(){		
		assertEquals(2, aCon.getX());		
	}
	
	@Test
	public void getYTest(){		
		assertEquals(2, aCon.getY());		
	}
	
	@Test
	public void getWallCountest() {
				
		assertEquals(2, aCon.getX());
		assertEquals(5, aCon.getY());
		
		aCon.decrementWallCount();		
		assertEquals(9, aCon.getWallCount());
		
		
		
	}
	
	public void getMoveCountTest(){
		
		int move = aCon.getMoveCount();
	    aCon.incrementMoveCount();
		assertEquals(move+1, aCon.getMoveCount());

		aCon.incrementMoveCount();
		aCon.incrementMoveCount();
		aCon.incrementMoveCount();
		assertEquals(move+4, aCon.getMoveCount());
		
	}

}
