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
import javafx.scene.input.MouseEvent;
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
 * @author Jack Zhang
 * @author Ben Lawton
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
    //amount of walls the player has
    private int player1WallCount;
    private Text player1Walls;
    //same as player one move
    private Text player2Moves;
    //same as player one place wall
    private int player2WallCount;
    private Text player2Walls;
    // current player
    private int currentPlayer;
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
        scene = new Scene(rootPane, 800, 800);
        firstPawn = new Circle(15);
        secondPawn = new Circle(15);
        drawing = true;
        player1Moves = new Text("Moves: " + 0);
        player1WallCount = 10;
        player1Walls = new Text("Walls: " + player1WallCount);
        player2Moves = new Text("Moves: " + 0);
        player2WallCount = 10;
        player2Walls = new Text("Walls: " + player2WallCount);
        currentPlayer = 1;
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
        buttonPane.setAlignment(Pos.CENTER);
        //boardPane.setHgap(5);
        //boardPane.setVgap(5);
        rootPane.getChildren().addAll(player1StatsPane, boardPane, player2StatsPane,buttonPane);
    }
    private void setButtons(){
    	for(int x=1;x<width;x++){
    		for(int y=1;y<width;y++){
                final int X = x;
                final int Y = y;
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
                    button[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            GameController.movePawn(X, Y);
                            changeActivePlayer();
                        }
                    });
    			}
    			// if it in x axis and it a wall
    			if(x%2!=0 && y%2==0){
    				button[x][y].setHeight(10);
    				button[x][y].setWidth(40);
    				button[x][y].setStroke(Color.BLACK);
    				button[x][y].setFill(Color.WHITE);
    				boardPane.setConstraints(button[x][y],x,y);
    				boardPane.getChildren().add(button[x][y]);
                    button[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (currentPlayer == 1) {
                                if (player1WallCount > 0) {
                                    setWall(X, Y);
                                    changeActivePlayer();
                                }
                            }
                            else if (player2WallCount > 0) {
                                setWall(X, Y);
                                changeActivePlayer();
                            }
                        }
                    });
    			}
    			//if it in y axis and it a wall
    			if(x%2==0 && y%2!=0){
    				button[x][y].setWidth(10);
    				button[x][y].setHeight(40);
    				button[x][y].setStroke(Color.BLACK);
    				button[x][y].setFill(Color.WHITE);
    				boardPane.setConstraints(button[x][y],x,y);
    				boardPane.getChildren().add(button[x][y]);
                    button[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (currentPlayer == 1) {
                                if (player1WallCount > 0) {
                                    setWall(X, Y);
                                    changeActivePlayer();
                                }
                            }
                            else if (player2WallCount > 0) {
                                setWall(X, Y);
                            }
                        }
                    });
    			}
    		}
    	}
 	   highlightPositionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               GameController.showCurrentPlayerMoves();
           }
       });
       buttonPane.getChildren().add(highlightPositionsButton);

}
    /**
     * this method basically set the wall that the user want to
     */
   private void setWall(int x, int y) {
       if (y % 2 == 0) {
           if (button[x][y].getFill() == Color.WHITE) {
               if (x != 17) {
                   if (button[x + 2][y].getFill() == Color.WHITE) {
                       if (button[x + 1][y - 1].getFill() == Color.WHITE || button[x + 1][y + 1].getFill() == Color.WHITE) {
                           button[x][y].setFill(Color.BLUE);
                           button[x + 2][y].setFill(Color.BLUE);
                           int x1 = x;
                           int y1 = y - 1;
                           x1 = (x - 1) / 2;
                           y1 = (y - 1) / 2;
                           int x2 = x1 + 1;
                           int y2 = y1;
                           GameController.placeWall(x1, y1, -1, x2, y2, -1);
                       }
                   }
               }
           }
       }

       if (x % 2 == 0) {
           if (button[x][y].getFill() == Color.WHITE) {
               if (y != 17) {
                   if (button[x + 1][y].getFill() == Color.WHITE) {
                       if (button[x][y + 2].getFill() == Color.WHITE) {
                           if (button[x - 1][y + 1].getFill() == Color.WHITE || button[x + 1][y + 1].getFill() == Color.WHITE) {
                               button[x][y].setFill(Color.BLUE);
                               button[x][y + 2].setFill(Color.BLUE);
                               int x1 = x - 1;
                               int y1 = y;
                               x1 = (x - 1) / 2;
                               y1 = (y - 1) / 2;
                               int x2 = x1;
                               int y2 = y1 + 1;
                               GameController.placeWall(x1, y1, 1, x2, y2, 1);
                           }
                       }
                   }
               }
           }
       }
   }

   public void resetWalls() {
	   for (int y = 1; y < 18; y += 2) {
		   for (int x = 2; x < 18; x+= 2) {
			   button[x][y].setFill(Color.WHITE);
		   }
	   }
	   for (int y = 2; y < 18; y += 2) {
		   for (int x = 1; x < 18; x+= 2) {
			   button[x][y].setFill(Color.WHITE);
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
    public void setPawn(Circle pawn, Color colour, int x, int y) {
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
        button[x][y].setFill(Color.YELLOW);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (int x1 = 1; x1 < width; x1++) {
                            for (int y1 = 1; y1 < width; y1++) {
                                if(x1 % 2 != 0 && y1 % 2 != 0) {
                                    button[x1][y1].setFill(Color.WHITE);
                                }
                            }
                        }
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
        player1WallCount = wallCount;
        player1Walls.setText("Walls: " + player1WallCount);
    }

    public void updatePlayer2WallCount(int wallCount) {
        player2WallCount = wallCount;
        player2Walls.setText("Walls: " + player2WallCount);
    }

    public void updatePlayer1PawnPosition(int x, int y) {
        boardPane.getChildren().remove(firstPawn);
        boardPane.setConstraints(firstPawn, x, y);
        boardPane.getChildren().add(firstPawn);
    }

    public void updatePlayer2PawnPosition(int x, int y) {
        boardPane.getChildren().remove(secondPawn);
        boardPane.setConstraints(secondPawn, x, y);
        boardPane.getChildren().add(secondPawn);
    }

    public void changeActivePlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        }
        else {
            currentPlayer = 1;
        }
    }
}