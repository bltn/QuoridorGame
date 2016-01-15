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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class BoardGUI extends Application {

    private Scene scene;
    private GridPane boardPane;
    private Button[][] button;
    private int width;
    private int height;

    public BoardGUI() {
        height = 9;
        width = 9;
        boardPane = new GridPane();
        button = new Button[width][height];
        scene = new Scene(boardPane, 600, 400);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");
        setBoardPane();
        setButtons();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setBoardPane() {
        boardPane.setAlignment(Pos.CENTER);
    }

    public void setButtons() {

        for(int x = 0; x < width; x++) {
            for (int y = 0; y < width; y++) {
                button[x][y] = new Button();
                button[x][y].setPrefHeight(40);
                button[x][y].setPrefWidth(40);
                boardPane.setConstraints(button[x][y], y, x);
                boardPane.getChildren().add(button[x][y]);
            }
        }
    }

    //public void setPawn(Position x, Position y) {

    //}

    //public void setWall(Position x, Position y) {

    //}

    //public boolean isDrawing() {
    //    return drawing;
    //}

    //public void setDrawing() {
    //    drawing = true;
    //}

    //public void highlightPositionAvailability(Position x, Position y) {

    //}

    //public void updatePlayer1MoveCount(int moveCount) {

    //}

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
