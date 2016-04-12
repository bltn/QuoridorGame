
import java.util.ArrayList;

/**
 *
 * @author Khadija Patel
 */

public class StandardBoard extends Board{

    public StandardBoard() {
            super();
            initialiseBoard();
            player1 = new Player(positions[0][4], 1);
            player2 = new Player(positions[8][4], 2);
            currentPlayer = player1;
    }

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

    public void placeWallsModified(int topLeftX, int topLeftY, WallPlacement orientation) {
    	if ((topLeftX >= 0 && topLeftX <= 8) && (topLeftY >= 0 && topLeftY <= 8)) {
    		Position topLeft = positions[topLeftY][topLeftX];
    		if (wallPlacementIsValidModified(topLeft, orientation)) {
        		Position topRight = positions[topLeftY][topLeftX + 1];
        		Position bottomRight = positions[topLeftY + 1][topLeftX + 1];
        		Position bottomLeft = positions[topLeftY + 1][topLeftX];

        		System.out.println("TOP LEFT: " + topLeft.getX() + ", " + topLeft.getY() + " " + topLeft.hasRightWall());
        		System.out.println("TOP RIGHT: " + topRight.getX() + ", " + topRight.getY() + " " + topRight.hasLeftWall());
        		System.out.println("BOTTOM RIGHT: " + bottomRight.getX() + ", " + bottomRight.getY() + " " + bottomRight.hasLeftWall());
        		System.out.println("BOTTOM LEFT: " + bottomLeft.getX() + ", " + bottomLeft.getY() + " " + bottomLeft.hasLeftWall());

        		if (orientation == WallPlacement.VERTICAL) {
        			topLeft.setHasRightWall(true);
        			addWalledOffPosition(topLeft);
        			topRight.setHasLeftWall(true);
        			addWalledOffPosition(topRight);
        			bottomRight.setHasLeftWall(true);
        			addWalledOffPosition(bottomRight);
        			bottomLeft.setHasRightWall(true);
        			addWalledOffPosition(bottomLeft);

        			currentPlayer.incrementMoveCount();
        			currentPlayer.decrementWallCount();
        			switchPlayer();
        		}
        		System.out.println("TOP LEFT: " + topLeft.getX() + ", " + topLeft.getY() + " " + topLeft.hasRightWall());
        		System.out.println("TOP RIGHT: " + topRight.getX() + ", " + topRight.getY() + " " + topRight.hasLeftWall());
        		System.out.println("BOTTOM RIGHT: " + bottomRight.getX() + ", " + bottomRight.getY() + " " + bottomRight.hasLeftWall());
        		System.out.println("BOTTOM LEFT: " + bottomLeft.getX() + ", " + bottomLeft.getY() + " " + bottomLeft.hasLeftWall());
        	} else {
        		throw new IllegalStateException("Move is invalid");
        	}
    	}
    }

    public boolean movePawn(int posX, int posY) {
            if (currentPlayer == player1) {
            if (posX == player2.getPosition().getX() && posY == player2.getPosition().getY()) {
                    throw new IllegalArgumentException("Position is occupied");
            }
            else {
                    if (isValidMove(currentPlayer, posX, posY)) {
                            player1.setPosition(getPosition(posX, posY));
                            currentPlayer.incrementMoveCount();
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
    else if (currentPlayer == player2) {
            if (posX == player1.getPosition().getX() && posY == player1.getPosition().getY()) {
                    throw new IllegalArgumentException("Position is occupied");
            }
            else {
                    if (isValidMove(currentPlayer, posX, posY)) {
                            player2.setPosition(getPosition(posX, posY));
                            currentPlayer.incrementMoveCount();
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
            player1.setMoveCount(0);
            player2.setMoveCount(0);
            player1.setWallCount(10);
            player2.setWallCount(10);
            player1.setPosition(getPosition(4, 0));
            player2.setPosition(getPosition(4, 8));
            currentPlayer = player1;
            resetWalledOffPositions();
    }
	/**
	 * Assign borders to the board and set top and bottom grids as winning positions
	 */
   private void initialiseBoard() {
            positions = new Position[9][9];

            //initialise Position objects
            for (int x = 0; x < 9; x++) {
                    for (int y = 0; y < 9; y++) {
                            positions[y][x] = new Position(x, y);
                    }
            }
            //mark top positions as winners
            for (int x = 0; x < 9; x++) {
                    positions[0][x].setTop();
            }
            //mark bottom positions as winners
            for (int x = 0; x < 9; x++) {
                    positions[8][x].setBottom();
            }

            //set the board's top borders (walls)
            for (int x = 0; x < 9; x++) {
                    positions[0][x].setHasTopWall(true);
            }
            //set the board's right borders (walls)
            for (int y = 0; y < 9; y++) {
                    positions[y][8].setHasRightWall(true);
            }
            //set the board's bottom borders (walls)
            for (int x = 0; x < 9; x++) {
                    positions[8][x].setHasBottomWall(true);
            }
            //set the board's left borders (walls)
            for (int y = 0; y < 9; y++) {
                    positions[y][0].setHasLeftWall(true);
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

   private boolean wallPlacementIsValidModified(Position topLeft, WallPlacement orientation) {

		boolean isValid = true;

		if (orientation == WallPlacement.VERTICAL) {
			System.out.println("RIGHT: " + topLeft.hasRightWall());
			System.out.println("LEFT: " + topLeft.hasLeftWall());
			System.out.println("TOP: " + topLeft.hasTopWall());
			System.out.println("BOTTOM: " + topLeft.hasBottomWall());
			if (topLeft.hasRightWall()) {
				isValid = false;
			}
			if (topLeft.getY() == 8) {
				isValid = false;
			}
			else if (positions[topLeft.getY() + 1][topLeft.getX()].hasRightWall()) {
				isValid = false;
			}
			if (topLeft.hasBottomWall() && positions[topLeft.getY()][topLeft.getX() + 1].hasBottomWall()) {
				isValid = false;
			}
		} else if (orientation == WallPlacement.HORIZONTAL) {
			// TODO
		} else {
			isValid = false;
		}
		return isValid;
	}

   private void assignWall(Position position, PositionWallLocation location) {
   	switch (location) {
           case LEFT: {
                   position.setHasLeftWall(true);
                   addWalledOffPosition(position);
                   break;
           }
           case RIGHT: {
                   position.setHasRightWall(true);
                   addWalledOffPosition(position);
                   break;
           }
           case TOP: {
                   position.setHasTopWall(true);
                   addWalledOffPosition(position);
                   break;
           }
           case BOTTOM: {
                   position.setHasBottomWall(true);
                   addWalledOffPosition(position);
                   break;
           }
       }
   }
}
