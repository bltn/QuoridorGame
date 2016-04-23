public class Position
{
	// Position's coordinates on the grid
    private int xCoord;
    private int yCoord;

    // is at the top or bottom of the grid
    private boolean isTop;
    private boolean isBottom;

    // is at the left or right of the grid
    private boolean isLeft;
    private boolean isRight;

    private boolean isTopLeftCorner;
    private boolean isBottomRightCorner;
    private boolean isTopRightCorner;
    private boolean isBottomLeftCorner;


    // whether the position has a right, left, top or bottom wall assigned to it
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
     * @return whether it's at the left of the board
     */
    public boolean isLeft() {
    	return isLeft;
    }
    /**
     * @return whether it's at the right of the board
     */
    public boolean isRight() {
    	return isRight;
    }
    /**
     * @return whether it's at the top left of the board
     */
	public boolean isTopLeftCorner() {
		return isTopLeftCorner;
	}

	/**
	 * @return whether it's at the bottom right of the board
	 */
	public boolean isBottomRightCorner() {
		return isBottomRightCorner;
	}

    /**
     * @return whether it's at the top right of the board
     */
    public boolean isTopRightCorner() { return isTopRightCorner; }

    /**
     * @return whether it's at the bottom left of the board
     */
    public boolean isBottomLeftCorner() { return isBottomLeftCorner; }
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
     * set as a left of the board position
     */
    public void setLeft() {
        if (!isRight) {
            isLeft = true;
        }
    }

    /**
     * set as a right of the board position
     */
    public void setRight() {
        if (!isLeft) {
            isRight = true;
        }
    }

    /**
	 * set as a top left corner of the board position
	 */
	public void setTopLeftCorner() {
		if (!isBottomRightCorner && !isBottomLeftCorner && !isTopRightCorner) {
			isTopLeftCorner = true;
		}
	}

	/**
	 * set as a bottom right corner of the board position
	 */
	public void setBottomRightCorner() {
		if (!isTopLeftCorner && !isTopRightCorner && !isBottomLeftCorner) {
			isBottomRightCorner = true;
		}
	}

    /**
     * set as a top right corner of the board
     */
    public void setTopRightCorner() {
        if (!isTopLeftCorner && !isBottomRightCorner && !isBottomLeftCorner) {
            isBottomRightCorner = true;
        }
    }

    /**
     * set as a bottom left corner of the baord
     */
    public void setBottomLeftCorner() {
        if (!isTopLeftCorner && !isTopRightCorner && !isBottomRightCorner) {
            isBottomLeftCorner = true;
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
	 * @return whether or not the position has a top wall
	 */
	public boolean hasTopWall() {
		return hasTopWall;
	}

	/**
	 * @return whether or not the position has a right wall
	 */
	public boolean hasRightWall() {
		return hasRightWall;
	}

	/**
	 * @return whether or not the position has a bottom wall
	 */
	public boolean hasBottomWall() {
		return hasBottomWall;
	}

	/**
	 * @return whether or not the position has a left wall
	 */
	public boolean hasLeftWall() {
		return hasLeftWall;
	}
}
