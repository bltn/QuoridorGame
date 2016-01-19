/**
<<<<<<< HEAD
 * Skeleton class 
 * @author ben
 *
 */
public class Player {
	
	private int xCoord;
	private int yCoord;
	private Position position; 
	
	public Player(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	public Position getPosition() {
		return position; 
	}

	public void setX(int posX) {
		// TODO Auto-generated method stub
		
	}

	public void setY(int posY) {
		// TODO Auto-generated method stub
		
	}

=======
 * Models a player object for placing on the board ingame. 
 * 
 * @author Jordan Bird
 * @version 2/11/2015
 */
public abstract class Player
{
    public int walls;
    public String name; 
    public Position position;
    boolean startedAtTop;
    boolean startedAtBottom;
    
    /**
     * Constructor for objects of class Player
     * 
     * @Param posX      the starting X position of the player
     * @Param posY      the starting Y position of the player
     * @Param walls     the amount of walls the player has
     * 
     */
    public Player(int posX, int posY, int walls, String name)
    {
        this.walls = walls;
        this.name = name;
        position = new Position(posX, posY);
    }
    
     /**
     * Gets the name of the player
     * 
     * @return name the String representing player name
     */
    public String getName()
    {
        return name;
    }
    
     /**
     * Sets the amount of walls that the player has
     * 
     * @param  set   the change to be made to the X co-ordinate
     */
    public void setWalls(int set)
    {
        walls = set;
    }
    
    /**
     * Gets the amount of walls the player has
     * 
     * @return walls the int representing wall count
     */
    public int getWalls()
    {
        return walls;
    }
    
    /**
     * Sets the X position of the Player
     * 
     * @param  set   the change to be made to the X co-ordinate
     */
    public void setX(int set)
    {
        position.setX(set);
    }
    
     /**
     * Sets the Y position of the Player
     * 
     * @param  set   the change to be made to the Y co-ordinate
     */
    public void setY(int set)
    {
        position.setY(set);
    }
    
         /**
     * Gets the position of the Player
     * 
     * @return  position   an Array containing the position
     */
    public int[] getPosition()
    {
        return position.getCoords();
    }
    
     /**
     * Gets the X position of the Player
     * 
     * @return  posX   the numeric representation of the Player's X co-ordinate
     */
    public int getX()
    {
        return position.getX();
    }
    
     /**
     * Gets the Y position of the Player
     * 
     * @return  posY   the numeric representation of the Player's Y co-ordinate
     */
    public int getY()
    {
        return position.getY();
    }
    
     /**
     * Moves the Player in direction X a value of move spaces
     * 
     * @param  move   the change to be made to the X co-ordinate
     */
    public void moveX(int move)
    {
        position.setX(position.getX() + move);
    }
    
     /**
     * Moves the Player in direction Y a value of move spaces
     * 
     * @param  move   the change to be made to the Y co-ordinate
     */
    public void moveY(int move)
    {
        position.setY(position.getY() + move);
    }
    
    /**
     * Takes input and sets the starting position accordingly
     * @Param started  where the player starts
     */
    public void setStartedAt(String started)
    {
        if (started == "top")
        {
            startedAtTop = true;
            startedAtBottom = false;
        }
        
                if (started == "bottom")
        {
            startedAtTop = false;
            startedAtBottom = true;
        }
    }
    
    
    
>>>>>>> d26dae09bb6d227a803dff424d9932fccfb6fe8f
}
