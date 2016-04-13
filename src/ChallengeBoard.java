
import java.util.ArrayList;

/**
 *
 * @author Khadija Patel
 */

public class ChallengeBoard extends Board{

    private WallOwnershipCollection wallOwnershipRecords;

    public ChallengeBoard() {
        super("Challenge");
        initialisePlayer1(getPosition(0, 0));
        initialisePlayer2(getPosition(8, 8));
        setCurrentPlayer(getPlayer1());
        wallOwnershipRecords = new WallOwnershipCollection();
    }

    public boolean removeWalls(Position topLeftPosition, PositionWallLocation topLeftBorder, Position coveredPos2, PositionWallLocation pos2Border,
            Position coveredPos3, PositionWallLocation pos3Border, Position coveredPos4, PositionWallLocation pos4Border) {

    	int wallOwnerID = wallOwnershipRecords.getRecordByCoordinates(topLeftPosition.getX(), topLeftPosition.getY()).getPlayerID();
    	Player wallOwner = null;
    	if (wallOwnerID != getCurrentPlayer().getID()) {
    		if (wallOwnerID == 1) {
    			wallOwner = getPlayer1();
    		} else if (wallOwnerID == 2) {
    			wallOwner = getPlayer2();
    		}
    		removeWallFromPosition(topLeftPosition, topLeftBorder);
            removeWallFromPosition(coveredPos2, pos2Border);
            removeWallFromPosition(coveredPos3, pos3Border);
            removeWallFromPosition(coveredPos4, pos4Border);

            wallOwner.incrementWallCount();
            getCurrentPlayer().incrementMoveCount();

            switchPlayer();
            return true;
    	} else {
    		return false;
    	}
    }

    public void placeWalls(int topLeftX, int topLeftY, WallPlacement orientation) {
		if (getCurrentPlayer().hasWalls()) {
	    	if ((topLeftX >= 0 && topLeftX <= 8) && (topLeftY >= 0 && topLeftY <= 8)) {
	    		Position topLeft = getPosition(topLeftX, topLeftY);
	    		if (wallPlacementIsValid(topLeft, orientation)) {
	    			assignWallsFromTopLeftClockwise(topLeft, orientation);
	    			getCurrentPlayer().decrementWallCount();
	    			getCurrentPlayer().incrementMoveCount();
	    			switchPlayer();
	    		} else {
	    			throw new IllegalStateException("Move is invalid");
	    		}
	    	} else {
	    		throw new IllegalStateException("Move is invalid");
	    	}
		} else {
			throw new IllegalStateException("You don't have any walls");
		}
	}

	private boolean wallPlacementIsValid(Position topLeft, WallPlacement orientation) {

		boolean isValid = true;

		if (orientation == WallPlacement.VERTICAL) {
			if (topLeft.hasRightWall()) {
				isValid = false;
			}
			if (topLeft.getY() == 8) {
				isValid = false;
			}
			else if (getPosition(topLeft.getX(), (topLeft.getY() + 1)).hasRightWall()) {
				isValid = false;
			}
			if (topLeft.hasBottomWall() && getPosition((topLeft.getX() + 1), topLeft.getY()).hasBottomWall()) {
				isValid = false;
			}
		} else if (orientation == WallPlacement.HORIZONTAL) {
			if (topLeft.hasBottomWall()) {
				isValid = false;
			}
			if (topLeft.getX() == 8) {
				isValid = false;
			}
			else if (getPosition((topLeft.getX() + 1), topLeft.getY()).hasBottomWall()) {
				isValid = false;
			}
			if (topLeft.hasRightWall() && getPosition(topLeft.getX(), (topLeft.getY() + 1)).hasRightWall()) {
				isValid = false;
			}
		} else {
			isValid = false;
		}
		return isValid;
	}

    public boolean movePawn(int posX, int posY) {
            if (getCurrentPlayer() == getPlayer1()) {
            if (posX == getPlayer2().getPosition().getX() && posY == getPlayer2().getPosition().getY()) {
                    throw new IllegalArgumentException("Position is occupied");
            }
            else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                            getPlayer1().setPosition(getPosition(posX, posY));
                            getCurrentPlayer().incrementMoveCount();
                            if (getPosition(posX, posY).isBottomCorner()) {
                                    reset();
                                    return true;
                            }
                            switchPlayer();
                            return false;
                    }
                    else {
                            throw new IllegalArgumentException("That isn't a valid move");
                    }
            }
    }
    else if (getCurrentPlayer() == getPlayer2()) {
            if (posX == getPlayer1().getPosition().getX() && posY == getPlayer1().getPosition().getY()) {
                    throw new IllegalArgumentException("Position is occupied");
            }
            else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                            getPlayer2().setPosition(getPosition(posX, posY));
                            getCurrentPlayer().incrementMoveCount();
                            if (getPosition(posX, posY).isTopCorner()) {
                                    reset();
                                    return true;
                            }
                            switchPlayer();
                            return false;
                    }
                    else {
                            throw new IllegalArgumentException("That isn't a valid move.");
                    }
            }
    }
            return false;
    }

    private void removeWallFromPosition(Position position, PositionWallLocation location) {
    	/**switch (location) {
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
    	}**/
    }

    private void assignWallsFromTopLeftClockwise(Position topLeft, WallPlacement orientation) {
 	   Position topRight = getPosition((topLeft.getX() + 1), topLeft.getY());
 	   Position bottomRight = getPosition((topLeft.getX() + 1), (topLeft.getY() + 1));
 	   Position bottomLeft = getPosition(topLeft.getX(), (topLeft.getY() + 1));

 	   if (orientation == WallPlacement.VERTICAL) {
 		   topLeft.setHasRightWall(true);
 		   addWalledOffPosition(topLeft);
 		   WallOwnershipRecord wallOwnerRecord = new WallOwnershipRecord(getCurrentPlayer().getID(), topLeft, orientation);
 		   wallOwnershipRecords.addRecord(wallOwnerRecord);
 		   topRight.setHasLeftWall(true);
 		   addWalledOffPosition(topRight);
 		   bottomRight.setHasLeftWall(true);
 		   addWalledOffPosition(bottomRight);
 		   bottomLeft.setHasRightWall(true);
 		   addWalledOffPosition(bottomLeft);
 	   } else if (orientation == WallPlacement.HORIZONTAL) {
 		   topLeft.setHasBottomWall(true);
 		   addWalledOffPosition(topLeft);
 		   WallOwnershipRecord wallOwnerRecord = new WallOwnershipRecord(getCurrentPlayer().getID(), topLeft, orientation);
		   wallOwnershipRecords.addRecord(wallOwnerRecord);
 		   topRight.setHasBottomWall(true);
 		   addWalledOffPosition(topRight);
 		   bottomRight.setHasTopWall(true);
 		   addWalledOffPosition(bottomRight);
 		   bottomLeft.setHasTopWall(true);
 		   addWalledOffPosition(bottomLeft);
 	   }
    }

    private void reset() {
        getPlayer1().setMoveCount(0);
        getPlayer2().setMoveCount(0);
        getPlayer1().setWallCount(10);
        getPlayer2().setWallCount(10);
        getPlayer1().setPosition(getPosition(0, 0));
        getPlayer2().setPosition(getPosition(8, 8));
        setCurrentPlayer(getPlayer1());
        resetWalledOffPositions();
    }
}
