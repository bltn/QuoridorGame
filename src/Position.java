/**
 * Models the coordination of any board object
 * 
 * @author Jordan Bird
 * @author Ben Lawton
 * @version 12/02/2016
 */
public class Position
{
    private int xCoord;
    private int yCoord;
    
    private boolean isTop;
    private boolean isBottom;
    
    private boolean hasTopWall;
    private boolean hasRightWall;
    private boolean hasBottomWall;
    private boolean hasLeftWall;

    /**
     * Constructor for objects of class Position
     * @param  x   the X value
     * @param  y   the Y value
     */
    public Position(int x, int y)
    {
        xCoord = x;
        yCoord = y;
    }

    /**
     * Find the X coordinate within the Position
     * @return the X value
     */
    public int getX()
    {
        return xCoord;
    }
    
    /**
     * Find the Y coordinate within the Position
     * @return the Y value
    */
    public int getY()
    {
        return yCoord;
    }

	public boolean isTop() {
		return isTop;
	}
	
	public boolean isBottom() {
		return isBottom;
	}
	
	public void setTop() {
		if (!isBottom) {
			isTop = true;
		}
	}
	
	public void setBottom() {
		if (!isTop) {
			isBottom = true;
		}
	}
	
	public void placeTopWall() {
		hasTopWall = true;
	}
	
	public void placeRightWall() {
		hasRightWall = true;
	}
	
	public void placeBottomWall() {
		hasBottomWall = true;
	}
	
	public void placeLeftWall() {
		hasLeftWall = true;
	}
	
	public boolean hasTopWall() {
		return hasTopWall;
	}
	
	public boolean hasRightWall() {
		return hasRightWall;
	}
	
	public boolean hasBottomWall() {
		return hasBottomWall;
	}
	
	public boolean hasLeftWall() {
		return hasLeftWall;
	}
}
