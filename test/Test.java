import static org.junit.Assert.*;


/**
 * @author Thai Con
 *
 */
public class Test {

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
	

	@org.junit.Test
	public void ControllerTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException  {
		Board board = new Board();
		BoardGUI gui = new BoardGUI();
		
		GameController Control = new GameController<>(gui, board);
		
		Control.placeWall(5, 5, -1, 4, 5, 1);
		Control.placeWall(5, 5, 0, 5, 6, 2);
			

		assertEquals(true,Control.getBoard().getPosition(5, 5).hasLeftWall());
		assertEquals(true,Control.getBoard().getPosition(4, 5).hasRightWall());
		assertEquals(true,Control.getBoard().getPosition(5, 5).hasTopWall());
		assertEquals(true,Control.getBoard().getPosition(5, 6).hasBottomWall());
        assertEquals(9, Control.getCurrentPlayer().getWallCount());
        assertEquals(1, Control.getCurrentPlayer().getMoveCount());
		
   
        Control.movePawn(5,6);
        Control.movePawn(7,6);
        
        assertEquals(5, Control.getCurrentPlayer().getX());
        assertEquals(6, Control.getCurrentPlayer().getY());
        
        Control.movePawn(1, 2);
        
        assertEquals(7, Control.getCurrentPlayer().getX());
        assertEquals(6, Control.getCurrentPlayer().getY());
	}
	
}
