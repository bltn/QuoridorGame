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

    public WallOwnershipCollection wallOwnershipRecords;

    public Player player1;
    public Player player2;

    public Player currentPlayer;

    /**
     * Constructor for an object of class Board
     */
    public Board() {
        walledOffPositions = new ArrayList<Position>();
        wallOwnershipRecords = new WallOwnershipCollection();
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

    /**
     * @param topLeftPosition First argument must always be the top left position of the 4x4 grid of positions the walls are
     * being assigned to, as this is the one validation must be performed on
     */
    public void placeWalls(Position topLeftPosition, PositionWallLocation topLeftBorder, Position coveredPos2, PositionWallLocation pos2Border,
            Position coveredPos3, PositionWallLocation pos3Border, Position coveredPos4, PositionWallLocation pos4Border) {

    	if (currentPlayer.hasWalls()) {
    		if (wallPlacementIsValid(topLeftPosition, topLeftBorder)) {
    			assignWall(topLeftPosition, topLeftBorder);
                assignWall(coveredPos2, pos2Border);
                assignWall(coveredPos3, pos3Border);
                assignWall(coveredPos4, pos4Border);

                currentPlayer.decrementWallCount();
                currentPlayer.incrementMoveCount();

                switchPlayer();
    		} else {
    			throw new IllegalStateException("You can't place a wall there.");
    		}
	    } else {
	            throw new IllegalStateException("You have no remaining walls");
	    }
    }

    public boolean removeWalls(Position topLeftPosition, PositionWallLocation topLeftBorder, Position coveredPos2, PositionWallLocation pos2Border,
            Position coveredPos3, PositionWallLocation pos3Border, Position coveredPos4, PositionWallLocation pos4Border) {

    	int wallOwnerID = wallOwnershipRecords.getRecordByCoordinates(topLeftPosition.getX(), topLeftPosition.getY()).getPlayerID();
    	Player wallOwner = null;
    	if (wallOwnerID != currentPlayer.getID()) {
    		if (wallOwnerID == 1) {
    			wallOwner = player1;
    		} else if (wallOwnerID == 2) {
    			wallOwner = player2;
    		}
    		removeWallFromPosition(topLeftPosition, topLeftBorder);
            removeWallFromPosition(coveredPos2, pos2Border);
            removeWallFromPosition(coveredPos3, pos3Border);
            removeWallFromPosition(coveredPos4, pos4Border);

            wallOwner.incrementWallCount();
            currentPlayer.incrementMoveCount();

            switchPlayer();
            return true;
    	} else {
    		return false;
    	}
    }

    /**
     * Returns true if a wall placement move is valid. Returns false if the move isn't valid, or if the
     * given position isn't the top left position in the 4x4 grid of board grids affected by the wall placement
     */
    private boolean wallPlacementIsValid(Position topLeftPosition, PositionWallLocation borderValue) {
    	if (borderValue == PositionWallLocation.RIGHT || borderValue == PositionWallLocation.BOTTOM) {
	    	boolean isValid = true;

	    	// vertically placed wall
	    	if (borderValue == PositionWallLocation.RIGHT) {
	    		// make there isn't already a wall here
	    		if (topLeftPosition.hasRightWall()) {
	    			isValid = false;
	    		}
	    		// make sure the player isn't clicking the bottom wall grid of the board (isn't allowed)
	    		if (topLeftPosition.getY() == 8) {
	    			isValid = false;
	    		} // make sure the wall following this one won't be placed in a wall slot that's already occupied
	    		else if (positions[topLeftPosition.getY() + 1][topLeftPosition.getX()].hasRightWall()) {
	    			isValid = false;
	    		}
	    		// make sure a cross wouldn't be formed with another two walls
	    		if (topLeftPosition.hasBottomWall() && positions[topLeftPosition.getY()][topLeftPosition.getX() + 1].hasBottomWall()) {
	    			isValid = false;
	    		}
	    		// horizontally placed wall
	    	} else if (borderValue == PositionWallLocation.BOTTOM) {
	    		// make sure there isn't already a wall here
	    		if (topLeftPosition.hasBottomWall()) {
	    			isValid = false;
	    		}
	    		// make sure the player isn't clicking the far-right wall grid of the board (isn't allowed)
	    		if (topLeftPosition.getX() == 8) {
	    			isValid = false;
	    			// make sure the wall following this one won't be placed in a wall slot that's already occupied
	    		} else if (positions[topLeftPosition.getY()][topLeftPosition.getX() + 1].hasBottomWall()) {
	    			isValid = false;
	    		}
	    		// make sure a cross wouldn't be formed with another two walls
	    		if (topLeftPosition.hasRightWall() && positions[topLeftPosition.getY() + 1][topLeftPosition.getX()].hasRightWall()) {
	    			isValid = false;
	    		}
	    	}
	    	return isValid;
    	} else {
    		return false;
    	}
    }

    /**
     * Adds a position to the collection of positions with walls assigned to them
     * @param pos position with wall assigned to it
     */
    private void addWalledOffPosition(Position pos) {
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

    private void assignWall(Position position, PositionWallLocation location) {
    	switch (location) {
            case LEFT: {
                    position.setHasLeftWall(true);
                    addWalledOffPosition(position);
                    WallOwnershipRecord wallOwnerRecord = new WallOwnershipRecord(currentPlayer.getID(), position, location);
                    wallOwnershipRecords.addRecord(wallOwnerRecord);
                    break;
            }
            case RIGHT: {
                    position.setHasRightWall(true);
                    addWalledOffPosition(position);
                    WallOwnershipRecord wallOwnerRecord = new WallOwnershipRecord(currentPlayer.getID(), position, location);
                    wallOwnershipRecords.addRecord(wallOwnerRecord);
                    break;
            }
            case TOP: {
                    position.setHasTopWall(true);
                    addWalledOffPosition(position);
                    WallOwnershipRecord wallOwnerRecord = new WallOwnershipRecord(currentPlayer.getID(), position, location);
                    wallOwnershipRecords.addRecord(wallOwnerRecord);
                    break;
            }
            case BOTTOM: {
                    position.setHasBottomWall(true);
                    addWalledOffPosition(position);
                    WallOwnershipRecord wallOwnerRecord = new WallOwnershipRecord(currentPlayer.getID(), position, location);
                    wallOwnershipRecords.addRecord(wallOwnerRecord);
                    break;
            }
        }
    }

    private void removeWallFromPosition(Position position, PositionWallLocation location) {
    	switch (location) {
	    	case LEFT: {
	    		position.setHasLeftWall(false);
	    		wallOwnershipRecords.removeRecord(position, location);
	    		break;
	    	}
	    	case RIGHT: {
	    		position.setHasRightWall(false);
	    		wallOwnershipRecords.removeRecord(position, location);
	    		break;
	    	}
	    	case TOP: {
	    		position.setHasTopWall(false);
	    		wallOwnershipRecords.removeRecord(position, location);
	    		break;
	    	}
	    	case BOTTOM: {
	    		position.setHasBottomWall(false);
	    		wallOwnershipRecords.removeRecord(position, location);
	    		break;
	    	}
    	}
    }
}
