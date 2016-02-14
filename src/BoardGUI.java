import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
 * @author Jordan Bird
 * 
 * @version 12/02/2016
 */
public class BoardGUI extends Application {

	//the scene used to display in the window
    private final Scene scene;
    private final int width = 17;
    private final int height = 17;
    private final HBox player2StatsPane = new HBox(260);
    private final HBox buttonPane = new HBox(10);
    
    
    //root pane which contain all the information from board to statistic
    private VBox rootPane;
    // player one stats
    private final HBox player1StatsPane = new HBox(260);
    // player two stats
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
    private boolean drawing;

    /**
     * Constructor for objects of class BoardGUI
     * Models and creates a GUI for the game itself
     */
    public BoardGUI() {
        rootPane = new VBox();
        boardPane = new GridPane();
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
        setPawn(firstPawn, Color.BLUE, 8, 0);
        setPawn(secondPawn, Color.RED, 8, 16);
        scene.getStylesheets().add("Theme.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Set the alignment of all instantiated fields to the centre of the pane
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

    /**
     * Create the board spaces and add them to the 2D array 
     */
    private void setButtons() {
    	for(int x = 0 ; x < width; x++){
    		for(int y = 0; y < width; y++){
                final int X = x;
                final int Y = y;
    			button[x][y] = new Rectangle();
    			// middle points between walls
    			if(x % 2 != 0 && y % 2 != 0) {
                    setUnusedSquare(x, y, X, Y);
    			}
    			// occupiable position
    			if(x % 2 == 0 && y % 2 == 0) {
                    setOccupiablePosition(x, y, X, Y);
    			}
    			// wide, short walls
    			if(x % 2 == 0 && y % 2 != 0) {
                    setWideWall(x, y, X, Y);
    			}
    			// tall, thin walls
    			if(x % 2 != 0 && y % 2 == 0) {
                    setThinWall(x, y, X, Y);
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
     * Allows a user to place a wall on a wall space that is available
     */
   private void setWall(int x, int y) {
       // tall, thin wall
	   if (y % 2 != 0) {
           if (button[x][y].getFill() == Color.WHITE) {
               if (x != 16) {
                   if (button[x + 2][y].getFill() == Color.WHITE) {
                       // avoid making a cross with the walls
                       if (button[x + 1][y - 1].getFill() == Color.WHITE || button[x + 1][y + 1].getFill() == Color.WHITE) {
                           placeThinWall(x, y);
                       }
                   }
               }
           }
       }
       //short, wide wall
       if (x % 2 != 0) {
           if (button[x][y].getFill() == Color.WHITE) {
               if (y != 16) {
                   if (button[x + 1][y].getFill() == Color.WHITE) {
                       if (button[x][y + 2].getFill() == Color.WHITE) {
                           // avoid making a cross with the walls
                           if (button[x - 1][y + 1].getFill() == Color.WHITE || button[x + 1][y + 1].getFill() == Color.WHITE) {
                               placeWideWall(x, y);
                           }
                       }
                   }
               }
           }
       }
   }

   /**
    * Removes all of the walls on the board and gives them back to their respective players  
    */
   public void resetWalls() {
	   for (int y = 0; y < 17; y += 2) {
		   for (int x = 1; x < 17; x+= 2) {
			   button[x][y].setFill(Color.WHITE);
		   }
	   }
	   for (int y = 1; y < 17; y += 2) {
		   for (int x = 0; x < 17; x+= 2) {
			   button[x][y].setFill(Color.WHITE);
		   }
	   }
   }
   
   
/**
 * Sets the player stats so they can be displayed in the UI
 */
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
     * Draw a player piece at a position on the board
     * 
     * @param pawn	the Player to be drawn
     * @param colour	the colour of the piece
     * @param x		the horizontal co-ordinate of the player
     * @param y		the vertical co-ordinate of the player
     */
    public void setPawn(Circle pawn, Color colour, int x, int y) {
        pawn.setFill(colour);
        pawn.setStroke(Color.BLACK);
        pawn.setTranslateX(5);
        boardPane.setConstraints(pawn, x, y);
        boardPane.getChildren().add(pawn);
    }
    
    /**
     *  Checks whether the program is currently drawing
     * @return		the value of isDrawing
     */
    public boolean isDrawing() {
        return drawing;
    }

    /**
     * Sets the drawing boolean to the passed boolean
     * @param b		is the program drawing or not
     */
    public void setDrawing(boolean b) {
        drawing = b;
    }

    /**
     * Highlights all of the surrounding valid places for the player to occupy
     * @param x		the x co-ordinate
     * @param y		the y co-ordinate
     */
    public void highlightPositionAvailability(int x, int y) {
        button[x][y].setFill(Color.YELLOW);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (int x1 = 0; x1 < width; x1++) {
                            for (int y1 = 0; y1 < width; y1++) {
                                if(x1 % 2 == 0 && y1 % 2 == 0) {
                                    button[x1][y1].setFill(Color.WHITE);
                                }
                            }
                        }
                    }
                },
                1000
        );
    }

    /**
     * Set the player move count to the selected value
     * @param moveCount		the value to change movecount to
     */
    public void updatePlayer1MoveCount(int moveCount) {
        player1Moves.setText("Moves: " + moveCount);
    }

    /**
     * 
     * @param moveCount		the value to change movecount to
     */
    public void updatePlayer2MoveCount(int moveCount) {
        player2Moves.setText("Moves: " + moveCount);
    }

    /**
     * Set the player wall count to the selected value
     * @param wallCount		the value to change walls to
     */
    public void updatePlayer1WallCount(int wallCount) {
        player1WallCount = wallCount;
        player1Walls.setText("Walls: " + player1WallCount);
    }

    /**
     * Set the player wall count to the selected value
     * @param wallCount		the value to change walls to
     */
    public void updatePlayer2WallCount(int wallCount) {
        player2WallCount = wallCount;
        player2Walls.setText("Walls: " + player2WallCount);
    }

    /**
     * Updates the player's pawn position to the selected position
     * @param x		x co-ordinate
     * @param y		y co-ordinate 
     */
    public void updatePlayer1PawnPosition(int x, int y) {
    	// convert the 9x9 coordinates from the controller to 18x8 coordinates for the GUI
    	int eighteenByEighteenX = x * 2;
    	int eighteenByEighteenY = y * 2;
        boardPane.getChildren().remove(firstPawn);
        boardPane.setConstraints(firstPawn, eighteenByEighteenX, eighteenByEighteenY);
        boardPane.getChildren().add(firstPawn);
    }

    /**
     * Updates the player's pawn position to the selected position
     * @param x		x co-ordinate
     * @param y		y co-ordinate 
     */
    public void updatePlayer2PawnPosition(int x, int y) {
    	// convert the 9x9 coordinates from the controller to 18x8 coordinates for the GUI
    	int eighteenByEighteenX = x * 2;
    	int eighteenByEighteenY = y * 2;
        boardPane.getChildren().remove(secondPawn);
        boardPane.setConstraints(secondPawn, eighteenByEighteenX, eighteenByEighteenY);
        boardPane.getChildren().add(secondPawn);
    }

    /**
     * change the active player to the next player
     */
    public void changeActivePlayer() {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        }
        else {
            currentPlayer = 1;
        }
    }

    /**
     * Set the occupiable positions 
     * @param x
     * @param y
     * @param X
     * @param Y
     */
    private void setOccupiablePosition(int x, int y, int X, int Y) {
        button[x][y].setHeight(40);
        button[x][y].setWidth(40);
        button[x][y].setStroke(Color.BLACK);
        button[x][y].setFill(Color.WHITE);
        boardPane.setConstraints(button[x][y],x,y);
        boardPane.getChildren().add(button[x][y]);
        button[x][y].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // convert the 18x18 GUI coordinates to the 9x9 coordinates for the controller (the controller has a 9x9 model of the board)
                int nineByNineX = X / 2;
                int nineByNineY = Y / 2;
                GameController.movePawn(nineByNineX, nineByNineY);
                changeActivePlayer();
            }
        });
    }

    private void setWideWall(int x, int y, int X, int Y) {
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

    private void setThinWall(int x, int y, int X, int Y) {
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

    private void setUnusedSquare(int x, int y, int X, int Y) {
        button[x][y].setHeight(10);
        button[x][y].setWidth(10);
        button[x][y].setStroke(Color.BLACK);
        button[x][y].setFill(Color.RED);
        boardPane.setConstraints(button[x][y],x,y);
        boardPane.getChildren().add(button[x][y]);
    }

    private void placeThinWall(int x, int y) {
        button[x][y].setFill(Color.BLUE);
        button[x + 2][y].setFill(Color.BLUE);
        // coordinates of the position to the top left of the horizontal wall
        int topLeftPosX = x / 2;
        int topLeftPosY = y / 2;
        // coordinates of the position to the bottom left of the horizontal wall
        int bottomLeftPosX = topLeftPosX;
        int bottomLeftPosY = topLeftPosY + 1;
        // coordinates of the position to the top right of the horizontal wall
        int topRightPosX = topLeftPosX + 1;
        int topRightPosY = topLeftPosY;
        // coordinates of the position to the bottom right of the horizontal wall
        int bottomRightPosX = topLeftPosX + 1;
        int bottomRightPosY = bottomLeftPosY;
        PositionWallLocation bottom = PositionWallLocation.BOTTOM;
        PositionWallLocation top = PositionWallLocation.TOP;
        GameController.placeWall(topLeftPosX, topLeftPosY, bottom, topRightPosX, topRightPosY, bottom, bottomLeftPosX, bottomLeftPosY, top, bottomRightPosX, bottomRightPosY, top);
    }

    private void placeWideWall(int x, int y) {
        button[x][y].setFill(Color.BLUE);
        button[x][y + 2].setFill(Color.BLUE);
        // coordinates of the position to the top left of the vertical wall
        int topLeftPosX = x / 2;
        int topLeftPosY = y / 2;
        // coordinates of the position to the bottom left of the vertical wall
        int bottomLeftPosX = topLeftPosX;
        int bottomLeftPosY = topLeftPosY + 1;
        // coordinates of the position to the top right of the vertical wall
        int topRightPosX = topLeftPosX + 1;
        int topRightPosY = topLeftPosY;
        // coordinates of the position to the bottom right of the vertical wall
        int bottomRightPosX = topLeftPosX + 1;
        int bottomRightPosY = bottomLeftPosY;
        PositionWallLocation right = PositionWallLocation.RIGHT;
        PositionWallLocation left = PositionWallLocation.LEFT;
        GameController.placeWall(topLeftPosX, topLeftPosY, right, bottomLeftPosX, bottomLeftPosY, right, topRightPosX, topRightPosY, left, bottomRightPosX, bottomRightPosY, left);
    }
}
