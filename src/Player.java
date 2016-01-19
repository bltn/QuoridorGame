public class Player
{
    private Position position;
    private int xCoord;
    private int yCoord; 
    private boolean startedAtTop;
    private boolean startedAtBottom;
    
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
