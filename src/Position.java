/**
 * Models the co ordination of any board object
 * 
 * @author Jordan Bird, Hoang Thai Khanh Con
 * @version 02/01/2015
 */
public class Position {
	private int x;
	private int y;

	private boolean topWall;
	private boolean botWall;
	private boolean rightWall;
	private boolean leftWall;

	/**
	 * Constructor for objects of class Position
	 * 
	 * @param x
	 *            the X value
	 * @param y
	 *            the Y value
	 */
	public Position(int x, int y) {
		topWall   = false;
		botWall   = false;
		rightWall = false;
		leftWall  = false;

		this.x = x;
		this.y = y;

	}

	/**
	 * Set the x co-ordinate
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the Y co-ordinate
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Find the X co ordinate within the Position
	 * 
	 * 
	 * @return the X value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Find the Y co ordinate within the Position
	 * 
	 * 
	 * @return the Y value
	 */
	public int getY() {
		return y;
	}

	public boolean isTopWall() {
		return topWall;
	}

	public void placeTopWall() {
		topWall = true;
	}

	public boolean isBotWall() {
		return botWall;
	}

	public void placeBotWall() {
		botWall = true;
	}

	public boolean isRightWall() {
		return rightWall;
	}

	public void placeRightWall() {
		rightWall = true;
	}

	public boolean isLeftWall() {
		return leftWall;
	}

	public void placeLeftWall() {
		leftWall = true;
	}

}
