/**
 * StandardBoard is used as model class to model a board being played with
 * the standard rule set. It models a 9x9 board for the game. It also extends
 * the Board class.
 *
 * @author Ben Lawton
 * @author Junaid Rasheed
 * @author Khadija Patel
 * @author Thai Hoang
 */
public class StandardBoard extends Board {

    public StandardBoard(boolean fourPlayerMode) {
		super(GameMode.STANDARD, fourPlayerMode);
		initialisePlayer1(getPosition(4, 0));
		initialisePlayer2(getPosition(4, 8));
		if (fourPlayerMode) {
			initialisePlayer3(getPosition(0, 4));
			initialisePlayer4(getPosition(8, 4));
		}
		setCurrentPlayer(getPlayer1());
    }

    /**
     * Move the currently active pawn to it's new position
     * @param posX The X coordinate to move the pawn to
     * @param posY The Y coordinate to move the pawn to
     * @return True if the pawn has moved to it's winning position
     */
    public boolean movePawn(int posX, int posY) {
        if (getPlayer3() == null) {
            if (getCurrentPlayer() == getPlayer1()) {
                if (posX == getPlayer2().getPosition().getX() && posY == getPlayer2().getPosition().getY()) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
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
                    } else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
            else if (getCurrentPlayer() == getPlayer2()) {
                if (posX == getPlayer1().getPosition().getX() && posY == getPlayer1().getPosition().getY()) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
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
                    } else {
                        throw new IllegalArgumentException(Translate.invalidMove());
                    }
                }
            }
        }
        else {
            if (getCurrentPlayer() == getPlayer1()) {
                if (posX == getPlayer2().getPosition().getX() && posY == getPlayer2().getPosition().getY() || posX == getPlayer3().getPosition().getX() && posY == getPlayer3().getPosition().getY() || posX == getPlayer4().getPosition().getX() && posY == getPlayer4().getPosition().getY()) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                } else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer1().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isBottom()) {
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
                if (posX == getPlayer1().getPosition().getX() && posY == getPlayer1().getPosition().getY() || posX == getPlayer3().getPosition().getX() && posY == getPlayer3().getPosition().getY() || posX == getPlayer4().getPosition().getX() && posY == getPlayer4().getPosition().getY()) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                } else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer2().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isTop()) {
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
                if (posX == getPlayer1().getPosition().getX() && posY == getPlayer1().getPosition().getY() || posX == getPlayer2().getPosition().getX() && posY == getPlayer2().getPosition().getY() || posX == getPlayer4().getPosition().getX() && posY == getPlayer4().getPosition().getY()) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                }
                else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer3().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isRight()) {
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
                if (posX == getPlayer1().getPosition().getX() && posY == getPlayer1().getPosition().getY() || posX == getPlayer2().getPosition().getX() && posY == getPlayer2().getPosition().getY() || posX == getPlayer3().getPosition().getX() && posY == getPlayer3().getPosition().getY()) {
                    throw new IllegalArgumentException(Translate.positionOccupied());
                }
                else {
                    if (isValidMove(getCurrentPlayer(), posX, posY)) {
                        getPlayer4().setPosition(getPosition(posX, posY));
                        getCurrentPlayer().incrementMoveCount();
                        if (getPosition(posX, posY).isLeft()) {
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

    /**
     * Move each player to their starting positions and reset their stats
     */
    private void reset() {
        getPlayer1().setMoveCount(0);
        getPlayer2().setMoveCount(0);
        getPlayer1().setWallCount(10);
        getPlayer2().setWallCount(10);
        getPlayer1().setPosition(getPosition(4, 0));
        getPlayer2().setPosition(getPosition(4, 8));
        if (getPlayer3() != null) {
            getPlayer3().setMoveCount(0);
            getPlayer4().setMoveCount(0);
            getPlayer3().setWallCount(10);
            getPlayer4().setWallCount(10);
            getPlayer3().setPosition(getPosition(0, 4));
            getPlayer4().setPosition(getPosition(8, 4));
        }
        setCurrentPlayer(getPlayer1());
        resetWalledOffPositions();
    }

    /**
     * Place a wall onto the board
     * @param topLeftX The X coordinate to the top left of the wall
     * @param topLeftY The Y coordinate to the top left of the wall
     * @param orientation Whether the wall is horizontal or vertical
     */
    public void placeWalls(int topLeftX, int topLeftY, WallPlacement orientation) {
		if (getCurrentPlayer().hasWalls()) {
			if ((topLeftX >= 0 && topLeftX <= 8) && (topLeftY >= 0 && topLeftY <= 8)) {
				Position topLeft = getPosition(topLeftX, topLeftY);
				if (wallPlacementIsValid(topLeft, orientation)) {
					assignWallsFromTopLeftClockwise(topLeft, orientation);

					if (!Utility.AstarSearch(getPositions(), getPlayer1().getPosition(), 8)
							|| !Utility.AstarSearch(getPositions(), getPlayer2().getPosition(), 0)) {

						removeWalls(topLeft, orientation);
						throw new IllegalStateException(Translate.blockingMove());
					}

					getCurrentPlayer().incrementMoveCount();
					getCurrentPlayer().decrementWallCount();
					switchPlayer();
				} else {
					throw new IllegalStateException(Translate.invalidMove());
				}
			} else {
				throw new IllegalStateException(Translate.invalidMove());
			}
		} else {
			throw new IllegalStateException(Translate.invalidMove());
		}
	}

    /**
     * Assign a wall to a position
     * @param topLeft The position to the top left of the wall
     * @param orientation Whether the wall is horizontal or vertical
     */
	public void assignWallsFromTopLeftClockwise(Position topLeft, WallPlacement orientation) {
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

    /**
     * Remove a wall from a position
     * @param topLeft The position to the top left of the wall
     * @param orientation Whether the wall is horizontal or vertical
     */
	public void removeWalls(Position topLeft, WallPlacement orientation) {
		Position topRight = getPosition((topLeft.getX() + 1), topLeft.getY());
		Position bottomRight = getPosition((topLeft.getX() + 1), (topLeft.getY() + 1));
		Position bottomLeft = getPosition(topLeft.getX(), (topLeft.getY() + 1));
		if (orientation == WallPlacement.VERTICAL) {
			topLeft.setHasRightWall(false);
			topRight.setHasLeftWall(false);
			bottomRight.setHasLeftWall(false);
			bottomLeft.setHasRightWall(false);
		} else if (orientation == WallPlacement.HORIZONTAL) {
			topLeft.setHasBottomWall(false);
			topRight.setHasBottomWall(false);
			bottomRight.setHasTopWall(false);
			bottomLeft.setHasTopWall(false);
		}
	}

    /**
     * Whether the position is valid for a wall to be placed on it
     * @param topLeft The position to the top elft of the wall
     * @param orientation Whether the wall is horizontal or vertical
     * @return
     */
	public boolean wallPlacementIsValid(Position topLeft, WallPlacement orientation) {
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
}
