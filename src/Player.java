public class Player
{
	private int ID;
    private Position position;
    private boolean startedAtTop;
    private boolean startedAtBottom;
    private int wallCount;
    private int moveCount;

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

	public void incrementWallCount() {
		wallCount++;
	}
}
