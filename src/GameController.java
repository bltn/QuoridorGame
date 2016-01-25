
import java.util.ArrayList;
import java.util.Iterator;




/**
 * @author Ben Lawton
 * @author Khadija Patel
 */
public class GameController<T> {

    private Board board;
    private BoardGUI gui; 
    
    private Player player1;
    private Player player2;
    //The player whose turn it is 
    private Player currentPlayer;

    private int player1MoveCount;
    private int player2MoveCount;

    public GameController(BoardGUI gui, Board board) {
        this.board = board;
        this.gui = gui;
        
        player1 = new Player(4, 0);
        player2 = new Player(4, 8); 
        currentPlayer = player1;
        
        player1MoveCount = 0;
        player2MoveCount = 0;
    }
    
    public static void main(String args[]) {
    	Board board = new Board();
    	BoardGUI gui = new BoardGUI();
    	GameController controller = new GameController(gui, board);
    }

    public void showCurrentPlayerMoves() {
    	Position position = board.getPosition(currentPlayer.getX(), currentPlayer.getY()); 
    	ArrayList<Position> availablePositions = board.getOccupiablePositions(position);
    	if (availablePositions.size() > 0) {
    		for (Position pos : availablePositions) {
    			//highlightPositionAvailability(pos.getX(), pos.getY()); 
    		}
    	}
    }
    
    /**
     * @param pos1X
     * @param pos1Y
     * @param pos1BorderSetting
     * @param pos2X
     * @param pos2Y
     * @param pos2BorderSetting
     */
    public void placeWall(int pos1X, int pos1Y, int pos1BorderSetting, int pos2X, int pos2Y, int pos2BorderSetting) {
    	if (currentPlayer.hasWalls()) {
	    	Position coveredPosition1 = board.getPosition(pos1X, pos1Y);
	    	Position coveredPosition2 = board.getPosition(pos2X, pos2Y);
	    	
	    	assignWall(coveredPosition1, pos1BorderSetting);
	    	assignWall(coveredPosition2, pos2BorderSetting); 
	    	
	    	currentPlayer.decrementWallCount();
	    	currentPlayer.incrementMoveCount();
	    	if (currentPlayer == player1) {
	    		//updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		//updatePlayerWallCount(currentPlayer.getWallCount());
	    	}
	    	else {
	    		//updatePlayer2MoveCount(currentPlayer.getMoveCount());
	    		//updatePlayer2WallCount(currentPlayer.getWallCount());
	    	}
	    	changePlayer();
    	}
    	else {
    		//errorMessage("you have no remaining walls");
    	}
    }

    public void movePawn(int posX, int posY) {
    	if (currentPlayer == player1) {
    		if (posX == player2.getX() && posY == player2.getY()) {
    			//errorMessage("that position is occupied");
    		}
    		else {
	    		player1.setX(posX);
	    		player1.setY(posY);
	    		currentPlayer.incrementMoveCount();
	    		//updatePlayer1MoveCount(currentPlayer.getMoveCount());
	    		//updatePlayer1PawnPosition(currentPlayer.getX(), currentPlayer.getY());
	    		if (currentPlayer.getPosition().isBottom()) {
	    			gameOver(currentPlayer);
	    			return;
	    		}
	    		changePlayer();
    		}
    	}
    	else {
    		if (posX == player1.getX() && posY == player1.getY()) {
	    		//errorMessage("that position is occupied");
    		}
    		else {
    			player2.setX(posX);
	    		player2.setY(posY); 
	    		currentPlayer.incrementMoveCount();
	    		//updatePlayer2MoveCount(player2MoveCount);
	    		//updatePlayer2PawnPosition(player2.getX(), player2.getY()); 
	    		if (currentPlayer.getPosition().isTop()) {
	    			gameOver(currentPlayer);
	    			return;
	    		}
	    		changePlayer();
    		}
    	}
    }
    
    public void gameOver() {
    	System.exit(0);
    }
    
    private void gameOver(Player player) {
    	//gui.winner(player);
    	System.exit(0);
    }

    private void assignWall(Position position, int borderValue) {
    	if (borderValue == -1) {
    		position.placeLeftWall();
    	}
    	else if (borderValue == 0) {
    		position.placeTopWall();
    	}
    	else if (borderValue == 1) {
    		position.placeRightWall();
    	}
    	else if (borderValue == 2) {
    		position.placeBottomWall();
    	}
    }
    
    private void changePlayer() {
    	if (this.currentPlayer == player1) {
    		currentPlayer = player2;
    	}
    	else {
    		currentPlayer = player1;
    	}
    	//updateCurrentPlayer(currentPlayer); 
    }

    public Player getCurrentPlayer(){
    	
    	return currentPlayer;
    	
    }
    
    public Board getBoard(){
    	
    	return board;
    	
    }
}