import javafx.stage.Stage;

public class GUIMock implements GUI {

	public void GUIStub() {}

	@Override
	public void highlightPositionAvailability(int x, int y) {/**STUB**/}

	@Override
	public void updatePlayerMoveCount(int moveCount, int id) {/**STUB**/}

	@Override
	public void updateActivePlayer(int playerID) {/**STUB**/}

	@Override
	public void updatePlayerPawnPosition(int x, int y, int id) {/**STUB**/}

	@Override
	public void updatePlayerWallCount(int wallCount, int playerID) {/**STUB**/}

	@Override
	public void resetWalls() {/**STUB**/}

	@Override
	public void setController(Controller controller) {/**STUB**/}

	@Override
	public void start(Stage stage) {/**STUB**/}

	@Override
	public void displayWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {/**STUB**/}

	@Override
	public void removeWallDisplay(int topLeftX, int topLeftY, WallPlacement orientation) {/**STUB**/}

	@Override
	public void displayErrorMessage(String message) {/**STUB**/}

}
