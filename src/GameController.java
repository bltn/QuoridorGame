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
    private Player currentPlayer;
    
    private int player1WallCount;
    private int player2WallCount;
    private int player1MoveCount;
    private int player2MoveCount;

    public GameController(BoardGUI gui, Board board) {
        this.board = board;
        this.gui = gui;
        
        player1 = new Player(5, 0);
        player2 = new Player(5, 9); 
        currentPlayer = player1;
        
        player1WallCount = 10;
        player2WallCount = 10;
        player1MoveCount = 0;
        player2MoveCount = 0;
    }

    public static void main(String[] args) {}

    public void showCurrentPlayerMoves() {
    	Position position = currentPlayer.getPosition(); 
    	Position[] availablePositions = board.getOccupiablePositions(position);
    	if (availablePositions.length > 0) {
    		for (Position pos : availablePositions) {
    			//highlightPositionAvailability(pos.getX(), pos.getY()); 
    		}
    	}
    	changePlayer(); 
    }

    public void placeWall(int pos1X, int pos1Y, int pos1Border, int pos2X, int pos2Y, int pos2Border) {
    	Position position1 = board.getPosition(pos1X, pos1Y);
    	Position position2 = board.getPosition(pos2X, pos2Y);
    	
    	assignWall(position1, pos1Border);
    	assignWall(position2, pos2Border); 
    	
    	decrementPlayerWallCount();
    	changePlayer();
    }
    
    public Player getCurrentPlayer() {
    	return currentPlayer; 
    }

    public void movePawn(int posX, int posY) {
    	if (currentPlayer == player1) {
    		player1.setX(posX);
    		player1.setY(posY);
    		player1MoveCount++;
    		//updatePlayer1MoveCount(player1MoveCount);
    		//updatePlayer1PawnPosition(player1.getX(), player1.getY());
    		changePlayer();
    	}
    	else {
    		player2.setX(posX);
    		player2.setY(posY); 
    		player2MoveCount++;
    		//updatePlayer2MoveCount(player2MoveCount);
    		//updatePlayer2PawnPosition(player2.getX(), player2.getY()); 
    		changePlayer();
    	}
    }

    public void gameOver(Player player) {
    }

    public void endGame() {
    }
    
    private void assignWall(Position position, int borderValue) {
    	if (borderValue == -1) {
    		position.setLeftWall();
    	}
    	else if (borderValue == 0) {
    		position.setTopWall();
    	}
    	else if (borderValue == 1) {
    		position.setRightWall();
    	}
    	else if (borderValue == 2) {
    		position.setBottomWall();
    	}
    }
    
    private void decrementPlayerWallCount() {
		if (currentPlayer == player1) {
			player1WallCount--;
		}
		else {
			player2WallCount--;
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

}