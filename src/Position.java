/**
 * Models the coordination of any board object
 *
 * @author Jordan Bird
 * @author Ben Lawton
 * @version 12/02/2016
 */
public class Position
{
	// Position's coordinates on the grid
    private int xCoord;
    private int yCoord;

    // is at the top or bottom of the grid
    private boolean isTop;
    private boolean isBottom;

    // whether the position has a right, left, top or bottom wall assigned to it
    private boolean hasTopWall;
    private boolean hasRightWall;
    private boolean hasBottomWall;
    private boolean hasLeftWall;
    
    private Position camefrom;
    public Position getCamefrom() {
		return camefrom;
	}

	public void setCamefrom(Position camefrom) {
		this.camefrom = camefrom;
	}

	private boolean Visited=false;
    public boolean isVisited() {
		return Visited;
	}

	public void setVisited(boolean visited) {
		Visited = visited;
	}

	private int costsofar;
	public int getCostsofar() {
		return costsofar;
	}

	public void setCostsofar(int costsofar) {
		this.costsofar = costsofar;
	}

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

    /**
     * @return whether it's at the top of the board
     */
	public boolean isTop() {
		return isTop;
	}

	/**
	 * @return whether it's at the bottom of the board
	 */
	public boolean isBottom() {
		return isBottom;
	}

	/**
	 * set as a top of the board position
	 */
	public void setTop() {
		if (!isBottom) {
			isTop = true;
		}
	}

	/**
	 * set as a bottom of the board position
	 */
	public void setBottom() {
		if (!isTop) {
			isBottom = true;
		}
	}

	/**
	 * Assign or remove a top wall
	 */
	public void setHasTopWall(boolean hasWall) {
		hasTopWall = hasWall;
	}

	/**
	 * Assign or remove a right wall
	 */
	public void setHasRightWall(boolean hasWall) {
		hasRightWall = hasWall;
	}

	/**
	 * Assign or remove a bottom wall
	 */
	public void setHasBottomWall(boolean hasWall) {
		hasBottomWall = hasWall;
	}

	/**
	 * Assign or remove a left wall
	 * @param hasWall
	 */
	public void setHasLeftWall(boolean hasWall) {
		hasLeftWall = hasWall;
	}

	/**
	 * @return whether or note the position has a top wall
	 */
	public boolean hasTopWall() {
		return hasTopWall;
	}

	/**
	 * @return whether or note the position has a right wall
	 */
	public boolean hasRightWall() {
		return hasRightWall;
	}

	/**
	 * @return whether or note the position has a bottom wall
	 */
	public boolean hasBottomWall() {
		return hasBottomWall;
	}

	/**
	 * @return whether or note the position has a left wall
	 */
	public boolean hasLeftWall() {
		return hasLeftWall;
	}
}
