import javafx.stage.Stage;

public interface GUI {

	public void highlightPositionAvailability(int x, int y);

	public void updatePlayerMoveCount(int moveCount, int id);

	public void updateActivePlayer(int playerID);

	public void updatePlayerPawnPosition(int x, int y, int id);

	public void updatePlayerWallCount(int i, int j);

	public void resetWalls();

	public void setController(Controller controller);

	public void start(Stage stage);

	public void displayWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID);

	public void removeWallDisplay(int topLeftX, int topLeftY, WallPlacement orientation);

	public void displayErrorMessage(String message);
}