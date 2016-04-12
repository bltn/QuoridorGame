/**
 * @author Ben Lawton
 * @author Thai Hoang
 * @author Khadija Patel
 */
import java.util.ArrayList;


public abstract class Board {

    // 2D array for Positions
    public Position positions[][];

    // Positions with walls assigned to them, tracked for a more efficient reset
    public ArrayList<Position> walledOffPositions;


    public Player player1;
    public Player player2;

    public Player currentPlayer;

    /**
     * Constructor for an object of class Board
     */
    public Board() {
        walledOffPositions = new ArrayList<Position>();
    }

    public Player getPlayer1() {
            return player1;
    }

    public Player getPlayer2() {
            return player2;
    }

    public Player getCurrentPlayer() {
            return currentPlayer;
    }

    public Player getPreviousPlayer() {
            if (currentPlayer == player1) {
                    return player2;
            }
            else {
                    return player1;
            }
    }

    public void switchPlayer() {
            if (currentPlayer == player1) {
                    currentPlayer = player2;
            }
            else if (currentPlayer == player2) {
                    currentPlayer = player1;
            }
    }

    /**
     * @param posX x coordinates of the position
     * @param posY y coordinates of the position
     * @return position at the given coordinates
     */
    public Position getPosition(int posX, int posY) {
    	Position position = null;
    	if (posX <= 8 && posY <= 8) {
    		position = positions[posY][posX];
    	}
    	return position;
    }

    abstract public boolean movePawn(int posX, int posY);

    abstract public void placeWalls(Position topLeftPosition, PositionWallLocation topLeftBorder, Position coveredPos2, PositionWallLocation pos2Border,
            Position coveredPos3, PositionWallLocation pos3Border, Position coveredPos4, PositionWallLocation pos4Border);

    /**
     * Adds a position to the collection of positions with walls assigned to them
     * @param pos position with wall assigned to it
     */
    public void addWalledOffPosition(Position pos) {
        if (!walledOffPositions.contains(pos)) {
                walledOffPositions.add(pos);
        }
    }

    /**
     * Removes all non-border walls from positions with walls assigned to them
     */
    public void resetWalledOffPositions() {
        for (Position pos : walledOffPositions) {
                if (pos.getY() != 0) {
                        pos.setHasTopWall(false);
                }
                if (pos.getX() != 8) {
                        pos.setHasRightWall(false);
                }
                if (pos.getX() != 0) {
                        pos.setHasLeftWall(false);
                }
                if (pos.getY() != 8) {
                        pos.setHasBottomWall(false);
                }
        }
    }

    public ArrayList<Position> getCurrentPlayerOccupiablePositions() {
        ArrayList<Position> localPositions = new ArrayList<Position>();
        Position currentPosition = currentPlayer.getPosition();
        if (!currentPosition.hasTopWall()) {
                localPositions.add(positions[currentPosition.getY()-1][currentPosition.getX()]);
        }
        if (!currentPosition.hasRightWall()) {
                localPositions.add(positions[currentPosition.getY()][currentPosition.getX()+1]);
        }
        if (!currentPosition.hasBottomWall()) {
                localPositions.add(positions[currentPosition.getY()+1][currentPosition.getX()]);
        }
        if (!currentPosition.hasLeftWall()) {
                localPositions.add(positions[currentPosition.getY()][currentPosition.getX()-1]);
        }
        return localPositions;
    }

    public boolean isValidMove(Player player, int newX, int newY) {
	    boolean isValid = false;
	    Position playerPos = player.getPosition();
	    // if the move is directly along the x axis
	    if (((newX == (playerPos.getX() + 1)) || (newX == (playerPos.getX() - 1))) && newY == playerPos.getY()) {
	            // if the move is to the left and the player won't be blocked by a wall to the left
	            if ((newX == (playerPos.getX() - 1) && (!getPosition(playerPos.getX(), playerPos.getY()).hasLeftWall()))) {
	                    isValid = true;
	            }
	            // if the move is to the right and the player won't be blocked by a wall to the right
	            else if ((newX == (playerPos.getX() + 1) && (!getPosition(playerPos.getX(), playerPos.getY()).hasRightWall()))) {
	                    isValid = true;
	            }
	    }
	    // if the move is directly along the y axis
	    else if (((newY == (playerPos.getY() + 1)) || (newY == (playerPos.getY() - 1))) && newX == playerPos.getX()) {
	            // if the move is up and the player won't be blocked by a wall to the top
	            if ((newY == (playerPos.getY() - 1) && (!getPosition(playerPos.getX(), playerPos.getY()).hasTopWall()))) {
	                    isValid = true;
	            }
	            // if the move is down and the player won't be blocked by a wall to the bottom
	            else if ((newY == (playerPos.getY() + 1) && (!getPosition(playerPos.getX(), playerPos.getY()).hasBottomWall()))) {
	                    isValid = true;
	            }
	    }
	    return isValid;
    }
}
