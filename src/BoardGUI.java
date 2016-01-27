import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Junaid Rasheed
 */
public class BoardGUI extends Application {

	//the scene used to display in the window
    private Scene scene;
    //root pane which contain all the information from board to statistic
    private VBox rootPane;
    // player one stats
    private HBox player1StatsPane;
    // player two stats
    private HBox player2StatsPane;
    private HBox buttonPane;
    private GridPane boardPane;
    //amount of move the player has make
    private Text player1Moves;
    //amount of move the player has place the wall
    private Text player1Walls;
    //same as player one move
    private Text player2Moves;
    //same as player one place wall
    private Text player2Walls;
    //draw the wall and movement in the board
    private Rectangle[][] button;
    private Button highlightPositionsButton;
    private Circle firstPawn;
    private Circle secondPawn;
    private int width;
    private int height;
    private boolean drawing;

    /**
     * declare everything in the board
     */
    public BoardGUI() {
        height = 18;
        width = 18;
        rootPane = new VBox();
        player1StatsPane = new HBox(260);
        player2StatsPane = new HBox(260);
        boardPane = new GridPane();
        buttonPane = new HBox(10);
        boardPane.setGridLinesVisible(true);
        button = new Rectangle[width][height];
        highlightPositionsButton = new Button("Hint");
        scene = new Scene(rootPane, 1200, 1200);
        firstPawn = new Circle(15);
        secondPawn = new Circle(15);
        drawing = true;
        player1Moves = new Text("Moves: " + 0);
        player1Walls = new Text("Walls: " + 10);
        player2Moves = new Text("Moves: " + 0);
        player2Walls = new Text("Walls: " + 10);
        
    }
   
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");
        setPanes();
        setButtons();
        setPlayerStats();
        setPawn(firstPawn, Color.BLUE, 9, 1);
        setPawn(secondPawn, Color.RED, 9, 17);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * set everything into position centre 
     * for the pane
     */
    private void setPanes() {
        rootPane.setAlignment(Pos.CENTER);
        player1StatsPane.setAlignment(Pos.CENTER);
        player2StatsPane.setAlignment(Pos.CENTER);
        boardPane.setAlignment(Pos.CENTER);
        //boardPane.setHgap(5);
        //boardPane.setVgap(5);
        rootPane.getChildren().addAll(player1StatsPane, boardPane, player2StatsPane,buttonPane);
    }
    private void setButtons(){
    	for(int x=1;x<width;x++){
    		for(int y=1;y<width;y++){
    			button[x][y] = new Rectangle();
    			// if it the middle point then it should just be a little 
    			if(x%2==0 && y%2==0){
    				
    				button[x][y].setHeight(10);
    				button[x][y].setWidth(10);
    				button[x][y].setStroke(Color.BLACK);
    				button[x][y].setFill(Color.RED);
    				boardPane.setConstraints(button[x][y],x,y);
    				boardPane.getChildren().add(button[x][y]);
    			}
    			// if it actual movable button
    			if(x%2!=0 && y%2!=0){
    				button[x][y].setHeight(40);
    				button[x][y].setWidth(40);
    				button[x][y].setStroke(Color.BLACK);
    				button[x][y].setFill(Color.WHITE);
    				boardPane.setConstraints(button[x][y],x,y);
    				boardPane.getChildren().add(button[x][y]);
    			}
    			// if it in x axis and it a wall
    			if(x%2!=0 && y%2==0){
    				button[x][y].setHeight(10);
    				button[x][y].setWidth(40);
    				button[x][y].setStroke(Color.BLACK);
    				button[x][y].setFill(Color.WHITE);
    				boardPane.setConstraints(button[x][y],x,y);
    				boardPane.getChildren().add(button[x][y]);
    				setWall(x,y);
    			}
    			//if it in y axis and it a wall
    			if(x%2==0 && y%2!=0){
    				button[x][y].setWidth(10);
    				button[x][y].setHeight(40);
    				button[x][y].setStroke(Color.BLACK);
    				button[x][y].setFill(Color.WHITE);
    				boardPane.setConstraints(button[x][y],x,y);
    				boardPane.getChildren().add(button[x][y]);
    				setWall(x,y);
    			}
    		}
    	}
    	/*
 	   highlightPositionsButton.setOnAction(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
               GameController.showCurrentPlayerMoves();
           }
       });
       buttonPane.getChildren().add(highlightPositionsButton);
       */
}
    /**
     * this method basically set the wall that the user want to 
     */
   private void setWall(int x, int y) {
    	if(x%2==0){
    		button[x][y].setOnMouseClicked(event ->{
    			if(event.getButton() == MouseButton.PRIMARY){
    				if(validateYWall(x,y)){
    					// place successfully
    				}
    				else{
    					//place fail.
    				}
    			}
    		});
    		}
    	if(y%2==0){
    		button[x][y].setOnMouseClicked(event ->{
    			if(event.getButton() == MouseButton.PRIMARY){
    				validateXWall(x,y);
    				
    			}
    		});
    	}
    }
   /**
    * this validate whether the x-axis wall can be set fill
    */
   private boolean validateXWall(int x,int y){
	   //this value used to find the previous position of wall
	   int previousX =  x-2;
	   //this value used to find the next position of wall
	   int nextX  = x+2;
	   //if the user try to place wall at the end, validate the previous wall
	   if(x == 17){
		   // if the previous button is fill, then do nothing
		   if(button[previousX][y].getFill() == Color.BLUE){
			   //you can't place wall here!
			 return  false;
		   }else{
			   //if not, then change both walls to blue
		   button[previousX][y].setFill(Color.BLUE);
		   button[x][y].setFill(Color.BLUE);
		   return true;
	   }
	   }
	   //validate the wall next to it.
	   if(button[x][y].getFill() == Color.BLUE || button[nextX][y].getFill() == Color.BLUE){
		   return false;
	   }else{
		   //if the wall is validate, then fill
		   button[x][y].setFill(Color.BLUE);
		   button[nextX][y].setFill(Color.BLUE);
		   return true;
	   }
   }
   /**
    * this validate whether the y-wall can be set fill
    * since the wall is double colour.
    */
   private boolean validateYWall(int x,int y){
	   //for more description, read validateXWall() method for more information
	   int previousY =  y-2;
	   int nextY  = y+2;
	   //if the user try to place wall at the end, validate the previous wall
	   if(y == 17){
		   if(button[x][previousY].getFill() == Color.BLUE){
			   //you can't place wall here!
			 return  false;
		   }else{
		   button[x][previousY].setFill(Color.BLUE);
		   button[x][y].setFill(Color.BLUE);
		   return true;
	   }
	   }
	   //validate the wall next to it.
	   if(button[x][y].getFill() == Color.BLUE){
		   return false;
	   }else if(button[x][nextY].getFill() == Color.BLUE){
		   return false;
	   }else{
		   button[x][y].setFill(Color.BLUE);
		   button[x][nextY].setFill(Color.BLUE);
		   return true;
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
    public boolean isDrawing() {
        return drawing;
    }

    public void setDrawing(boolean b) {
        drawing = b;
    }

    public void highlightPositionAvailability(int x, int y) {
        button[x][y].setStyle("-fx-base: #FFFF00;");
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        button[x][y].setStyle("-fx-base: #FFFFFF");
                    }
                },
                1000
        );
    }

    public void updatePlayer1MoveCount(int moveCount) {
        player1Moves.setText("Moves: " + moveCount);
    }

    public void updatePlayer2MoveCount(int moveCount) {
        player2Moves.setText("Moves: " + moveCount);
    }

    public void updatePlayer1WallCount(int wallCount) {
        player1Walls.setText("Walls: " + wallCount);
    }

    public void updatePlayer2WallCount(int wallCount) {
        player2Walls.setText("Walls: " + wallCount);
    }

    public void updatePlayer1PawnPosition(int x, int y) {
        boardPane.getChildren().remove(firstPawn);
        boardPane.setConstraints(firstPawn, x, y);
        boardPane.getChildren().add(firstPawn);
    }

    private void updatePlayer2PawnPosition(int x, int y) {
        boardPane.getChildren().remove(secondPawn);
        boardPane.setConstraints(secondPawn, x, y);
        boardPane.getChildren().add(secondPawn);
    }

    private static void main(String[] args) {
        launch(args);
    }
}