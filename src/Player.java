/**
 * @author Jordan Bird
 * @author Ben Lawton
 * 
 *  @version 12/02/2016
 */

public class Player
{
    private Position position;
    private int xCoord;
    private int yCoord;
    private boolean startedAtTop;
    private boolean startedAtBottom;
    private int wallCount;
    private int moveCount;

    /**
     * Constructor for objects of class Player
     *
     * @param posX the starting X position of the player
     * @param posY the starting Y position of the player
     * @param walls the amount of walls the player has
     */
    public Player(int posX, int posY)
    {
    	xCoord = posX;
    	yCoord = posY;
        position = new Position(posX, posY);
        wallCount = 10;
        moveCount = 0;
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

    /**
     * Sets the player's X coordinate
     *
     * @param  set   the change to be made to the X co-ordinate
     */
    public void setX(int posX)
    {
        xCoord = posX;
    }

     /**
     * Sets the player's Y coordinate
     *
     * @param  set   the change to be made to the Y co-ordinate
     */
    public void setY(int posY)
    {
        yCoord = posY;
    }

    public int getX()
    {
        return xCoord;
    }

    public int getY()
    {
        return yCoord;
    }

     /**
     * Gets the position of the Player
     *
     * @return  position   an Array containing the position
     */
    public Position getPosition()
    {
        return position;
    }

    public void setStartedAtTop() {
    	if (!startedAtBottom) {
    		startedAtTop = true;
    	}
    }

    public void setStartedAtBottom() {
    	if (!startedAtTop) {
    		startedAtBottom = true;
    	}
    }

    public boolean startedAtTop() {
    	return startedAtTop;
    }

    public boolean startedAtBottom() {
    	return startedAtBottom;
    }
}
