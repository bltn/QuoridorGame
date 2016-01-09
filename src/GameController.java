
/**
 *
 * @author Khadija
 */
public class GameController {

    private Board board;
    private HumanPawn player1;
    private HumanPawn player2;
    private HumanPawn currentPlayer;
    private int player1WallCount;
    private int player2WallCount;
    private int player1MoveCount;
    private int player2MoveCount;

    public GameController() {
        board = new Board("player1", "player2");
        player1 = new HumanPawn(5, 0, 10, "player1");
        player2 = new HumanPawn(5, 9, 10, "player2");
        currentPlayer = player1;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    public void showCurrentPlayerMoves() {
    }

    public void changePlayer() {
    }

    public void placeWall(int x, int y) {
    }

    public void movePawn(int posX, int posY) {
    }

    public void decrementPlayer1WallCount() {
    }

    public void decrementPlayer2WallCount() {
    }

    public void gameOver(HumanPawn player) {
    }

    public void endGame() {
    }

}