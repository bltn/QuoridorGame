/**
 * @author Ben Lawton
 * @author Junaid Rasheed
 *
 * High-level contract for controllers (C) in the application's MVC architecture
 */


public interface Controller {

	public Player getCurrentPlayer();

	public void showCurrentPlayerMoves();

	public void placeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID);

	public void movePawn(int posX, int posY, int playerID);

    public int getPlayer1X();

    public int getPlayer1Y();

    public int getPlayer2X();

    public int getPlayer2Y();

    public int getPlayer3X();

    public int getPlayer3Y();

    public int getPlayer4X();

    public int getPlayer4Y();

    public void resetGame();

	public void removeWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID);


}
