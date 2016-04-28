import java.util.ArrayList;

/**
 * @author Ben Lawton
 * @author Junaid Rasheed
 * @author Khadija Patel
 *
 * Board models Quoridor's 9x9 game board by providing a 2d array of Position objects, each
 * representing a grid on the board
 *
 * It acts as the model and has two extending subclasses, ChallengeBoard and StandardBoard.
 *
 */

public abstract class Board {

    // Models a Quoridor board of grids
    private Position positions[][];
	// Positions with non-border walls assigned to them, tracked so they can be reset upon game over
    private ArrayList<Position> walledOffPositions;

    private Player currentPlayer;
	private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    // True: Board models a four player game; false: board models a two player game
    private boolean fourPlayerMode;

    /**
     * Constructor
     *
     * @param gameMode the rules for the game; either standard or challenge rules
     * @param fourPlayerMode whether or not the game is in 4 player mode; if not, it's in 2 player mode
     */
    public Board(GameMode gameMode, boolean fourPlayerMode) {
        walledOffPositions = new ArrayList<Position>();
        this.fourPlayerMode = fourPlayerMode;
        if (gameMode == GameMode.CHALLENGE) {
        	initialiseBoardWithChallengeRules();
        } else {
        	initialiseBoardWithStandardRules();
        }
    }

    /**
     * If the move is valid, moves the current player's pawn to the specified board coordinates
     * @param posX position-to-be-occupied's x coordinates
     * @param posY position-to-beoccupied's y coordinates
     * @return whether or not the move was actually made (might not be if it's an illegal move)
     */
    abstract public boolean movePawn(int posX, int posY);

    /**
     * Assign a wall to four positions in a 4x4 grid of positions, their coordinates being calculated
     * relative to the provided top left position in the 4x4 grid's coordinates
     *
     * IMPORTANT: the given coordinates *MUST* be for the top left position in the 4x4 grid (illustrated below)
     * of positions being blocked off/assigned a wall
     *
     * 			_this_|____
     * 				  |
     *
     * ^ Coordinates for the other positions being given a wall are calculated by working out their position
     * relative to the top left one
     *
     * @param topLeftX top left position's x coordinates
     * @param topLeftY top left position's y coordinates
     * @param orientation
     */
    abstract public void placeWalls(int topLeftX, int topLeftY, WallPlacement orientation);

    public void initialisePlayer1(Position startingPosition) {
    	if (player1 == null) {
    		player1 = new Player(startingPosition, 1);
    	}
    }

    public void initialisePlayer2(Position startingPosition) {
        if (player2 == null) {
            player2 = new Player(startingPosition, 2);
        }
    }

    public void initialisePlayer3(Position startingPosition) {
        if (player3 == null) {
            player3 = new Player(startingPosition, 3);
        }
    }

    public void initialisePlayer4(Position startingPosition) {
        if (player4 == null) {
            player4 = new Player(startingPosition, 4);
        }
    }

    public void setCurrentPlayer(Player player) {
    	currentPlayer = player;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getPlayer1() {
            return player1;
    }

    public Player getPlayer2() {
            return player2;
    }

    public Player getPlayer3() {
    	return player3;
    }

    public Player getPlayer4() {
    	return player4;
    }

    public Position[][] getPositions() {
		return positions;
	}

    public Position getPosition(int posX, int posY) {
    	Position position = null;
    	if (posX <= 8 && posY <= 8) {
    		position = positions[posY][posX];
    	}
    	return position;
    }

    /**
     * @return the player who last made a turn
     */
    public Player getPreviousPlayer() {
        if (!fourPlayerMode) {
            if (currentPlayer == player1) {
                return player2;
            } else {
                return player1;
            }
        } else {
            if (currentPlayer == player1) {
                return player4;
            } else if (currentPlayer == player2) {
                return player1;
            } else if (currentPlayer == player3) {
                return player2;
            } else {
                return player3;
            }
        }
    }

    /**
     * Switch to the player whose turn it is next
     * Should be called after a player makes a (successful) move
     */
    public void switchPlayer() {
        if (!fourPlayerMode) { // two player mode
        	if (currentPlayer == player1) {
        		currentPlayer = player2;
        	} else {
        		currentPlayer = player1;
        	}
        } else { // four player mode
        	if (currentPlayer == player1) {
        		currentPlayer = player2;
        	}
        	else if (currentPlayer == player2) {
        		currentPlayer = player3;
        	}
        	else if (currentPlayer == player3) {
        		currentPlayer = player4;
        	}
        	else if (currentPlayer == player4) {
        		currentPlayer = player1;
        	}
        }
    }

    /**
     * Add a position to the collection of positions with walls assigned to them
     * @param pos position with wall assigned to it
     */
    public void addWalledOffPosition(Position pos) {
        if (!walledOffPositions.contains(pos)) {
                walledOffPositions.add(pos);
        }
    }

    /**
     * Remove all non-border walls from positions with walls assigned to them
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

    /**
     * Give the positions the current player could legally move to
     * @return The occupiable positions
     */
    public ArrayList<Position> getCurrentPlayerOccupiablePositions() {
        ArrayList<Position> localPositions = new ArrayList<Position>();
        Position currentPosition = currentPlayer.getPosition();
        if (!currentPosition.hasTopWall() && !positionOccupiedByPlayer(currentPosition.getX(), (currentPosition.getY() - 1))) {
                localPositions.add(positions[currentPosition.getY()-1][currentPosition.getX()]);
        }
        if (!currentPosition.hasRightWall() && !positionOccupiedByPlayer((currentPosition.getX() + 1), currentPosition.getY())) {
                localPositions.add(positions[currentPosition.getY()][currentPosition.getX()+1]);
        }
        if (!currentPosition.hasBottomWall() && !positionOccupiedByPlayer(currentPosition.getX(), (currentPosition.getY() + 1))) {
                localPositions.add(positions[currentPosition.getY()+1][currentPosition.getX()]);
        }
        if (!currentPosition.hasLeftWall() && !positionOccupiedByPlayer((currentPosition.getX() - 1), currentPosition.getY())) {
                localPositions.add(positions[currentPosition.getY()][currentPosition.getX()-1]);
        }
        return localPositions;
    }

    /**
     * Determine whether or not a position with the given coordinates is occupied by a player
     */
    public boolean positionOccupiedByPlayer(int x, int y) {
		boolean occupied = false;

		if (x == getPlayer1().getPosition().getX() && y == getPlayer1().getPosition().getY()) {
			occupied = true;
		}
		if (x == getPlayer2().getPosition().getX() && y == getPlayer2().getPosition().getY()) {
			occupied = true;
		}

		if (getPlayer3() != null) {
			if (x == getPlayer3().getPosition().getX() && y == getPlayer3().getPosition().getY()) {
				occupied = true;
			}
			else if (x == getPlayer4().getPosition().getX() && y == getPlayer4().getPosition().getY()) {
				occupied = true;
			}
		}
		return occupied;
	}

    /**
     * Validate a pawn relocation move if the pawn's desired location is to the top, right, bottom or left of it
     * and isn't blocked off by a wall or other player
     */
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

    /**
     * Initialise the board's positions
     * Set the board's borders
     * Set the corner positions as "winning" positions
     */
    private void initialiseBoardWithChallengeRules() {
        positions = new Position[9][9];

        //initialise Position objects
        for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                        positions[y][x] = new Position(x, y);
                }
        }
        //mark top left positions as winners
        positions[0][0].setTopLeftCorner();
        //mark bottom right positions as winners
        positions[8][8].setBottomRightCorner();
        //mark top right positions as winners
        positions[0][8].setTopRightCorner();
        //mark bottom left positions as winners
        positions[8][0].setBottomLeftCorner();

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
	 * Initialise the board's positions
	 * Set the board's borders
	 * Set the top row and bottom row of the board as winning positions
	 */
   private void initialiseBoardWithStandardRules() {
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
        //mark left position as winners
        for (int y = 0; y < 9; y++) {
            positions[y][0].setLeft();
        }
       for (int y = 0; y < 9; y++) {
           positions[y][8].setRight();
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
}
