
public interface Controller {

	public Player getCurrentPlayer();

	public void showCurrentPlayerMoves();

	/**
     * First argument must always be the top left position of the 4x4 grid of positions the walls are
     * being assigned to, as this is the one validation must be performed on
     */
	public void placeWall(int topLeftX, int topLeftY, PositionWallLocation topLeftBorder, int pos2X, int pos2Y, PositionWallLocation pos2Border, int pos3X, int pos3Y,
    		PositionWallLocation pos3Border, int pos4X, int pos4Y, PositionWallLocation pos4Border);

	public void movePawn(int posX, int posY);

    public int getPlayer1X();

    public int getPlayer1Y();

    public int getPlayer2X();

    public int getPlayer2Y();

	public void resetGame();

	public void removeWall(int topLeftPosX, int topLeftPosY, PositionWallLocation right, int topRightPosX,
			int topRightPosY, PositionWallLocation left, int bottomLeftPosX, int bottomLeftPosY, PositionWallLocation right2, int bottomRightPosX, int bottomRightPosY, PositionWallLocation left2);

}
