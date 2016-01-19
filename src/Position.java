
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

}
