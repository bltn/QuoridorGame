import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class PlayerTest {

	private static Player player;

	@BeforeClass
	public static void setUp() {
		player = new Player(2, 5);
	}

	@Test
	public void getXTest(){
		assertEquals(2, player.getX());
	}

	@Test
	public void getYTest(){
		assertEquals(5, player.getY());
	}

	@Test
	public void testGetWallCount() {
		assertEquals(2, player.getX());
		assertEquals(5, player.getY());

		player.decrementWallCount();
		assertEquals(9, player.getWallCount());
	}

	@Test
	public void testGetMoveCount(){
		int move = player.getMoveCount();
	    player.incrementMoveCount();
		assertEquals(move+1, player.getMoveCount());

		player.incrementMoveCount();
		player.incrementMoveCount();
		player.incrementMoveCount();
		assertEquals(move+4, player.getMoveCount());
	}

}
