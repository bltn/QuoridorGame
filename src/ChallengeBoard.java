
import java.util.ArrayList;

/**
 *
 * @author Khadija Patel
 */

public class ChallengeBoard extends Board{
    public ChallengeBoard() {
        super();
        initialiseBoard();
        player1 = new Player(positions[0][0], 1);
        player2 = new Player(positions[8][8], 2);  
        currentPlayer = player1;
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
    else if (currentPlayer == player2) {
            if (posX == player1.getPosition().getX() && posY == player1.getPosition().getY()) {
                    throw new IllegalArgumentException("Position is occupied");
            }
            else {
                    if (isValidMove(currentPlayer, posX, posY)) {
                            player2.setPosition(getPosition(posX, posY));
                            currentPlayer.incrementMoveCount();
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
                positions[0][0].setTopCorner();
        }
        //mark bottom positions as winners
        for (int x = 0; x < 9; x++) {
                positions[8][8].setBottomCorner();
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
    
    private void reset() {
        player1.setMoveCount(0);
        player2.setMoveCount(0);
        player1.setWallCount(10);
        player2.setWallCount(10);
        player1.setPosition(getPosition(0, 0));
        player2.setPosition(getPosition(8, 8));
        currentPlayer = player1;
        resetWalledOffPositions();
    }
}
