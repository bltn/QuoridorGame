import java.util.ArrayList;

public class ChallengeBoard extends Board {

    private WallOwnershipCollection wallOwnershipRecords;

    public ChallengeBoard(boolean fourPlayerMode) {
        super(GameMode.CHALLENGE, fourPlayerMode);
        initialisePlayer1(getPosition(0, 0));
        initialisePlayer2(getPosition(8, 8));
		if (fourPlayerMode) {
			initialisePlayer3(getPosition(0, 8));
			initialisePlayer4(getPosition(8, 0));
		}
        setCurrentPlayer(getPlayer1());
        wallOwnershipRecords = new WallOwnershipCollection();
    }

    public boolean removeWalls(int topLeftX, int topLeftY, WallPlacement orientation) {
    	if ((topLeftX >= 0 && topLeftX <= 8) && (topLeftY >= 0 && topLeftY <= 8)) {
    		int wallOwnerID = wallOwnershipRecords.getRecordByCoordinates(topLeftX, topLeftY).getPlayerID();
        	Player wallOwner = null;
        	if (wallOwnerID != getCurrentPlayer().getID()) {
        		if (wallOwnerID == 1) {
        			wallOwner = getPlayer1();
        		} else if (wallOwnerID == 2) {
        			wallOwner = getPlayer2();
        		} else if (wallOwnerID == 3) {
                    wallOwner = getPlayer3();
                } else if (wallOwnerID == 4) {
                    wallOwner = getPlayer4();
                }
        		Position topLeft = getPosition(topLeftX, topLeftY);
        		removeWallsFromPosition(topLeft, orientation);
        		wallOwner.incrementWallCount();
        		getCurrentPlayer().incrementMoveCount();
        		switchPlayer();
        		return true;
        	} else {
        		return false;
        	}
    	} else {
    		return false;
    	}
    }

    private void removeWallsFromPosition(Position topLeft, WallPlacement orientation) {
    	Position topRight = getPosition((topLeft.getX() + 1), topLeft.getY());
  	   	Position bottomRight = getPosition((topLeft.getX() + 1), (topLeft.getY() + 1));
  	   	Position bottomLeft = getPosition(topLeft.getX(), (topLeft.getY() + 1));
    	if (orientation == WallPlacement.VERTICAL) {
    		topLeft.setHasRightWall(false);
    		wallOwnershipRecords.removeRecord(topLeft, orientation);
    		topRight.setHasLeftWall(false);
    		bottomRight.setHasLeftWall(false);
    		bottomLeft.setHasRightWall(false);
    	} else if (orientation == WallPlacement.HORIZONTAL) {
    		topLeft.setHasBottomWall(false);
    		wallOwnershipRecords.removeRecord(topLeft, orientation);
    		topRight.setHasBottomWall(false);
    		bottomRight.setHasTopWall(false);
    		bottomLeft.setHasTopWall(false);
    	}
    }

    public void placeWalls(int topLeftX, int topLeftY, WallPlacement orientation) {
		if (getCurrentPlayer().hasWalls()) {
			if ((topLeftX >= 0 && topLeftX <= 8) && (topLeftY >= 0 && topLeftY <= 8)) {
				Position topLeft = getPosition(topLeftX, topLeftY);
				if (wallPlacementIsValid(topLeft, orientation)) {
					assignWallsFromTopLeftClockwise(topLeft, orientation);

					if (!Utility.AstarSearch(getPositions(), getPlayer1().getPosition(), 8)
							|| !Utility.AstarSearch(getPositions(), getPlayer2().getPosition(), 0)) {

						removeWallsFromPosition(topLeft, orientation);
						throw new IllegalStateException(Translate.blockingMove());
					}

					getCurrentPlayer().decrementWallCount();
					getCurrentPlayer().incrementMoveCount();
					switchPlayer();
				} else {
					throw new IllegalStateException(Translate.invalidMove());
				}
			} else {
				throw new IllegalStateException(Translate.invalidMove());
			}
		} else {
			throw new IllegalStateException(Translate.noWalls());
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
			} else if (getPosition(topLeft.getX(), (topLeft.getY() + 1)).hasRightWall()) {
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
			} else if (getPosition((topLeft.getX() + 1), topLeft.getY()).hasBottomWall()) {
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
        if (getPlayer3() == null) {
            if (getCurrentPlayer() == getPlayer1()) {
                if (positionOccupiedByPlayer(posX, posY)) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                }
                else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer1().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isBottomRightCorner()) {
                            reset();
                            return true;
                        }
                        switchPlayer();
                        return false;
                    } else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
            else if (getCurrentPlayer() == getPlayer2()) {
                if (positionOccupiedByPlayer(posX, posY)) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                }
                else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer2().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isTopLeftCorner()) {
                            reset();
                            return true;
                        }
                        switchPlayer();
                        return false;
                    } else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
        }
        else {
            if (getCurrentPlayer() == getPlayer1()) {
                if (positionOccupiedByPlayer(posX, posY)) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                } else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer1().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isBottomRightCorner()) {
                            reset();
                            return true;
                        }
                        switchPlayer();
                        return false;
                    } else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            } else if (getCurrentPlayer() == getPlayer2()) {
                if (positionOccupiedByPlayer(posX, posY)) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                } else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer2().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isTopLeftCorner()) {
                            reset();
                            return true;
                        }
                        switchPlayer();
                        return false;
                    } else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
            else if (getCurrentPlayer() == getPlayer3()) {
                if (positionOccupiedByPlayer(posX, posY)) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                }
                else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer3().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isTopRightCorner()) {
                            reset();
                            return true;
                        }
                        switchPlayer();
                        return false;
                    }
                    else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
            else if (getCurrentPlayer() == getPlayer4()) {
                if (positionOccupiedByPlayer(posX, posY)) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                }
                else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer4().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isBottomLeftCorner()) {
                            reset();
                            return true;
                        }
                        switchPlayer();
                        return false;
                    }
                    else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
        }
        return false;
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
        if (getPlayer3() != null) {
            getPlayer3().setMoveCount(0);
            getPlayer4().setMoveCount(0);
            getPlayer3().setWallCount(10);
            getPlayer4().setWallCount(10);
            getPlayer3().setPosition(getPosition(0, 8));
            getPlayer4().setPosition(getPosition(8, 0));
        }
        setCurrentPlayer(getPlayer1());
        resetWalledOffPositions();
    }
}
