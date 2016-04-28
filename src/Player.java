/**
 * The Player class is used to create an object for each player. This object
 * stores the ID of the plyaer, the current position of the player,
 * where the player started from, and the move and wall counts for the
 * player.
 *
 * @author Ben Lawton
 * @author Junaid Rasheed
 * @author Jordan Bird
 * @author Thai Hoang
 */
public class Player
{
	private int ID;
    private Position position;
    private boolean startedAtTop;
    private boolean startedAtBottom;
    private boolean startedAtLeft;
    private boolean startedAtRight;
    private int wallCount;
    private int moveCount;

    /**
     * Setup a player object
     * @param position The starting position of a player
     * @param ID The ID assigned to a player
     */
	public Player(Position position, int ID)
    {
    	this.ID = ID;
        this.position = position;
        wallCount = 10;
        moveCount = 0;

    }

    public int getID() {
    	return ID;
    }

    public int getWallCount() {
    	return wallCount;
    }

    public void setWallCount(int count) {
    	wallCount = count;
    }

    public void setMoveCount(int count) {
    	moveCount = count;
    }

    public void decrementWallCount() {
    	wallCount--;
    }


	public int getMoveCount() {
    	return moveCount;
    }

    public void incrementMoveCount() {
    	moveCount++;
    }

    public boolean hasWalls() {
    	return wallCount > 0;
    }

    public Position getPosition()
    {
        return position;
    }

    public void setPosition(Position position) {
    	this.position = position;
    }

    public void setStartedAtTop() {
    	if (!startedAtBottom && !startedAtLeft && !startedAtRight) {
    		startedAtTop = true;
    	}
    }

    public void setStartedAtBottom() {
    	if (!startedAtTop && !startedAtLeft && !startedAtRight) {
    		startedAtBottom = true;
    	}
    }

    public void setStartedAtLeft() {
        if (!startedAtBottom && !startedAtTop && !startedAtRight) {
            startedAtLeft = true;
        }
    }

    public void setStartedAtRight() {
        if (!startedAtBottom && !startedAtTop && !startedAtLeft) {
            startedAtRight = true;
        }
    }

    public boolean startedAtTop() {
        return startedAtTop;
    }

    public boolean startedAtBottom() {
        return startedAtBottom;
    }

    public boolean startedAtLeft() {
        return startedAtLeft;
    }

    public boolean startedAtRight() {
        return startedAtRight;
    }

	public void incrementWallCount() {
		wallCount++;
	}
}
