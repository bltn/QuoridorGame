
public interface Controller {

	public Player getCurrentPlayer();

	public void showCurrentPlayerMoves();

	public void placeWall(int pos1X, int pos1Y, PositionWallLocation pos1Border, int pos2X, int pos2Y, PositionWallLocation pos2Border, int pos3X, int pos3Y,
    		PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border);

	public void movePawn(int posX, int posY);

}
