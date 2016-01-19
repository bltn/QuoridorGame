<<<<<<< HEAD

public class Position {
	
	private boolean isTop;
	private boolean isBottom;
	
	public Position() {
		isTop = false;
		isBottom = false;
	}
	
	public void setWinningTop() {
		if (!isBottom) {
			isTop = true;
		}
	}
	
	public void setWinningBottom() {
		if (!isTop) {
			isBottom = true;
		}
	}
	
	public boolean isTop() {
		return isTop;
	}
	
	public boolean isBottom() {
		return isBottom; 
	}

	public void setLeftWall() {}

	public void setTopWall() {}
	
	public void setRightWall() {}
	
	public void setBottomWall() {}

=======
/**
 * Models the co ordination of any board object
 * 
 * @author Jordan Bird
 * @version 02/01/2015
 */
public class Position
{
    private int x;
    private int y;
    int coords[];

    /**
     * Constructor for objects of class Position
     * 
     * @param  x   the X value
     * @param  y   the Y value
     */
    public Position(int x, int y)
    {
        this.x = x;
        this.y = y;
        coords = new int[1];
        updateArray();
    }
    
     /**
     * Set the x co-ordinate
     */
    public void setX(int x)
    {
        this.x = x;
    }
    
         /**
     * Set the Y co-ordinate
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * Find the X co ordinate within the Position
     * 
     * 
     * @return     the X value
     */
    public int getX()
    {
        return x;
    }
    
     /**
     * Find the Y co ordinate within the Position
     * 
     * 
     * @return     the Y value
     */
    public int getY()
    {
        return y;
    }
    
     /**
     * Find both of the co-ordinates and return them both, in Array[x,y]
     * 
     * 
     * @return     the co-ordinate set
     */
    public int[] getCoords()
    {
        updateArray();
        return coords;
    }
    
     /**
     * Push the co-ordinates into the Array
     */
    private void updateArray()
    {
        coords[0] = x;
        coords[1] = y;
    }
>>>>>>> d26dae09bb6d227a803dff424d9932fccfb6fe8f
}
