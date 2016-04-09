import javafx.stage.Stage;

public interface GUI {

	public void highlightPositionAvailability(int x, int y);

	public void updatePlayerMoveCount(int moveCount, int id);

	public void updateActivePlayer();

	public void updatePlayerPawnPosition(int x, int y, int id);

	public void updatePlayerWallCount(int i, int j);

	public void resetWalls();

	public void setController(Controller controller);

	public void start(Stage stage);

}