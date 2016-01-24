import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BoardGUI extends Application {

    private Scene scene;
    private VBox rootPane;
    private HBox player1StatsPane;
    private HBox player2StatsPane;
    private GridPane boardPane;
    private Text player1Moves;
    private Text player1Walls;
    private Text player2Moves;
    private Text player2Walls;
    private Button[][] button;
    private Circle firstPawn;
    private Circle secondPawn;
    private int width;
    private int height;
    private int player1MoveCount;
    private int player1WallCount;
    private int player2MoveCount;
    private int player2WallCount;
    private boolean drawing;

    public BoardGUI() {
        height = 9;
        width = 9;
        rootPane = new VBox();
        player1StatsPane = new HBox(265);
        player2StatsPane = new HBox(265);
        boardPane = new GridPane();
        button = new Button[width][height];
        scene = new Scene(rootPane, 600, 600);
        firstPawn = new Circle(15);
        secondPawn = new Circle(15);
        drawing = true;
        player1MoveCount = 0;
        player1WallCount = 10;
        player1Moves = new Text("Moves: " + player1MoveCount);
        player1Walls = new Text("Walls: " + player1WallCount);
        player2MoveCount = 0;
        player2WallCount = 10;
        player2Moves = new Text("Moves: " + player2MoveCount);
        player2Walls = new Text("Walls: " + player2WallCount);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");
        setPanes();
        setButtons();
        setPlayerStats();
        setPawn(firstPawn, Color.BLUE, 4, 0);
        setPawn(secondPawn, Color.RED, 4, 8);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setPanes() {
        rootPane.setAlignment(Pos.CENTER);
        player1StatsPane.setAlignment(Pos.CENTER);
        player2StatsPane.setAlignment(Pos.CENTER);
        boardPane.setAlignment(Pos.CENTER);
        boardPane.setHgap(5);
        boardPane.setVgap(5);
        rootPane.getChildren().addAll(player1StatsPane, boardPane, player2StatsPane);
    }

    public void setButtons() {
        for(int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                button[x][y] = new Button();
                button[x][y].setPrefHeight(40);
                button[x][y].setPrefWidth(40);
                boardPane.setConstraints(button[x][y], x, y);
                boardPane.getChildren().add(button[x][y]);
            }
        }
    }

    public void setPlayerStats() {
        player1Walls.setTextAlignment(TextAlignment.CENTER);
        player1Walls.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        player1Moves.setTextAlignment(TextAlignment.CENTER);
        player1Moves.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        player1StatsPane.getChildren().addAll(player1Moves, player1Walls);
        player2Walls.setTextAlignment(TextAlignment.CENTER);
        player2Walls.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        player2Moves.setTextAlignment(TextAlignment.CENTER);
        player2Moves.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        player2StatsPane.getChildren().addAll(player2Moves, player2Walls);
    }

    /**
     * draw a pawn
     */
    private void setPawn(Circle pawn, Color colour, int x, int y) {
        pawn.setFill(colour);
        pawn.setStroke(Color.BLACK);
        pawn.setTranslateX(5);
        boardPane.setConstraints(pawn, x, y);
        boardPane.getChildren().add(pawn);
    }

    //public void setWall(Position x, Position y) {

    //}

    public boolean isDrawing() {
        return drawing;
    }

    public void setDrawing(boolean b) {
        drawing = b;
    }

    //public void highlightPositionAvailability(Position x, Position y) {

    //}

    public void updatePlayer1MoveCount(int moveCount) {

    }

    //public void updatePlayer2MoveCount(int moveCount) {

    //}

    //public void updatePlayer1PawnPosition(Position x, Position y) {

    //}

    //public void updatePlayer2PawnPosition(Position x, Position y) {

    //}

    //public void updateCurrentPlayer(Player currentPlayer) {

    //}

    public static void main(String[] args) {
        launch(args);
    }
}
