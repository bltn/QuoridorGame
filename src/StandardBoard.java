
import java.util.ArrayList;

/**
 *
 * @author Khadija Patel
 */

public class StandardBoard extends Board {

    public StandardBoard() {
            super("Standard");
            initialisePlayer1(getPosition(4, 0));
            initialisePlayer2(getPosition(4, 8));
            setCurrentPlayer(getPlayer1());
    }

    public void placeWalls(int topLeftX, int topLeftY, WallPlacement orientation) {
    	if (getCurrentPlayer().hasWalls()) {
	    	if ((topLeftX >= 0 && topLeftX <= 8) && (topLeftY >= 0 && topLeftY <= 8)) {
	    		Position topLeft = getPosition(topLeftX, topLeftY);
	    		if (wallPlacementIsValid(topLeft, orientation)) {
	        		assignWallsFromTopLeftClockwise(topLeft, orientation);
	    			getCurrentPlayer().incrementMoveCount();
	    			getCurrentPlayer().decrementWallCount();
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

    public boolean movePawn(int posX, int posY) {
            if (getCurrentPlayer() == getPlayer1()) {
            if (posX == getPlayer2().getPosition().getX() && posY == getPlayer2().getPosition().getY()) {
                    throw new IllegalArgumentException("Position is occupied");
            }
            else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                            getPlayer1().setPosition(getPosition(posX, posY));
                            getCurrentPlayer().incrementMoveCount();
                            if (getPosition(posX, posY).isBottom()) {
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
                            if (getPosition(posX, posY).isTop()) {
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

    private void reset() {
            getPlayer1().setMoveCount(0);
            getPlayer2().setMoveCount(0);
            getPlayer1().setWallCount(10);
            getPlayer2().setWallCount(10);
            getPlayer1().setPosition(getPosition(4, 0));
            getPlayer2().setPosition(getPosition(4, 8));
            setCurrentPlayer(getPlayer1());
            resetWalledOffPositions();
    }

   private void assignWallsFromTopLeftClockwise(Position topLeft, WallPlacement orientation) {
	   Position topRight = getPosition((topLeft.getX() + 1), topLeft.getY());
 	   Position bottomRight = getPosition((topLeft.getX() + 1), (topLeft.getY() + 1));
 	   Position bottomLeft = getPosition(topLeft.getX(), (topLeft.getY() + 1));

	   if (orientation == WallPlacement.VERTICAL) {
		   topLeft.setHasRightWall(true);
		   addWalledOffPosition(topLeft);
		   topRight.setHasLeftWall(true);
		   addWalledOffPosition(topRight);
		   bottomRight.setHasLeftWall(true);
		   addWalledOffPosition(bottomRight);
		   bottomLeft.setHasRightWall(true);
		   addWalledOffPosition(bottomLeft);
	   } else if (orientation == WallPlacement.HORIZONTAL) {
		   topLeft.setHasBottomWall(true);
		   addWalledOffPosition(topLeft);
		   topRight.setHasBottomWall(true);
		   addWalledOffPosition(topRight);
		   bottomRight.setHasTopWall(true);
		   addWalledOffPosition(bottomRight);
		   bottomLeft.setHasTopWall(true);
		   addWalledOffPosition(bottomLeft);
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
}
