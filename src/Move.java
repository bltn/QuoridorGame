

public class Move {


	private int x;
	
	private int y;
	private WallPlacement orientation;

	public Move(int x, int y, WallPlacement orientation) {
		this.x = x;
		this.y = y;

		this.orientation = orientation;
	}
	public WallPlacement getOrientation() {
		return orientation;
	}

	public void setOrientation(WallPlacement orientation) {
		this.orientation = orientation;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

}
