import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.Set;

public class LocalBoardGUI extends Application implements GUI {

	//the scene used to display in the window
    private final Scene scene;
    private final int width = 17;
    private final int height = 17;
    private final HBox player2StatsPane = new HBox(120);
    private final HBox player3StatsPane = new HBox(120);
    private final HBox player4StatsPane = new HBox(120);
    private final HBox buttonPane = new HBox(10);

    private final HBox currentPlayerPane = new HBox();

    private final HBox errorPane = new HBox();
    private Text errorPaneText;

    //root pane which contain all the information from board to statistic
    private VBox rootPane;
    // player one stats
    private final HBox player1StatsPane = new HBox(120);
    // player two stats
    private GridPane boardPane;
    //amount of moves the player has made
    private Text player1Moves;
    private Text currentPlayerText;
    //amount of walls the player has
    private int player1WallCount;
    private Text player1Walls;
    //same as player one move
    private Text player2Moves;
    //same as player one place wall
    private int player2WallCount;
    private Text player2Walls;

    private Text player3Moves;
    private int player3WallCount;
    private Text player3Walls;

    private Text player4Moves;
    private int player4WallCount;
    private Text player4Walls;

    //draw the wall and movement in the board
    private Rectangle[][] grids;
    private Button highlightPositionsButton;
    private Circle firstPawn;
    private Circle secondPawn;
    private Circle thirdPawn;
    private Circle fourthPawn;
    private boolean drawing;

    private Controller controller;
    private int numberOfPlayers;


    /**
     * Constructor for objects of class BoardGUI
     * Models and creates a GUI for the game itself
     * @param numberOfPlayers
     */
    public LocalBoardGUI(int numberOfPlayers) {
        rootPane = new VBox();
        boardPane = new GridPane();
        boardPane.setGridLinesVisible(true);
        grids = new Rectangle[height][width];
        highlightPositionsButton = new Button(Translate.availableMoves());
        scene = new Scene(rootPane, 800, 800);
        firstPawn = new Circle(15);
        secondPawn = new Circle(15);
        thirdPawn = new Circle(15);
        fourthPawn = new Circle(15);
        drawing = true;
        player1Moves = new Text((Translate.moves() + ": ") + 0);
        currentPlayerText = new Text("Player 1's turn...");
        player1WallCount = 10;
        player1Walls = new Text((Translate.walls() + ": ") + player1WallCount);
        player2Moves = new Text((Translate.moves() + ": ") + 0);
        player2WallCount = 10;
        player2Walls = new Text((Translate.walls() + ": ") + player2WallCount);
        player3Moves = new Text((Translate.moves() + ": ") + 0);
        player3WallCount = 10;
        player3Walls = new Text((Translate.walls() + ": ") + player3WallCount);
        player4Moves = new Text((Translate.moves() + ": ") + 0);
        player4WallCount = 10;
        player4Walls = new Text((Translate.walls() + ": ") + player3WallCount);
        errorPaneText = new Text("");
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setController(Controller controller) {
    	this.controller = controller;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");
        setPanes();
        initialiseBoardSpaces();
        setPlayerStats();
        setPawn(firstPawn, Color.ORANGE, (controller.getPlayer1X() * 2), (controller.getPlayer1Y() * 2));
        setPawn(secondPawn, Color.GREEN, (controller.getPlayer2X() * 2), (controller.getPlayer2Y() * 2));
        if (numberOfPlayers == 4) {
            setPawn(thirdPawn, Color.BLUE, (controller.getPlayer3X() * 2), (controller.getPlayer3Y() * 2));
            setPawn(fourthPawn, Color.RED, (controller.getPlayer4X() * 2), (controller.getPlayer4Y() * 2));
        }
        scene.getStylesheets().add(SettingsGUI.theme);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BOARD");
        primaryStage.show();
    }



   /**
    * Removes all of the walls on the board
    */
   public void resetWalls() {
	   for (int y = 0; y < 17; y += 2) {
		   for (int x = 1; x < 17; x+= 2) {
			   grids[y][x].setFill(Color.GREY);
			   grids[y][x].setStroke(Color.GREY);
		   }
	   }
	   for (int y = 1; y < 17; y += 2) {
		   for (int x = 0; x < 17; x++) {
			   grids[y][x].setFill(Color.GREY);
			   grids[y][x].setStroke(Color.GREY);
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
        currentPlayerText.setFont(Font.font("Calibri", FontWeight.BOLD, FontPosture.ITALIC, 15));
        errorPaneText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        errorPaneText.setFill(Color.RED);
        player1Moves.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        Text player1Title = new Text("Player 1");
        player1Title.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        player1StatsPane.getChildren().addAll(player1Moves, player1Title, player1Walls);
        currentPlayerPane.getChildren().addAll(currentPlayerText);
        errorPane.getChildren().addAll(errorPaneText);
        player2Walls.setTextAlignment(TextAlignment.CENTER);
        player2Walls.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        player2Moves.setTextAlignment(TextAlignment.CENTER);
        player2Moves.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
        Text player2Title = new Text("Player 2");
        player2Title.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        player2StatsPane.getChildren().addAll(player2Moves, player2Title, player2Walls);
        if (numberOfPlayers == 4) {
            player3Walls.setTextAlignment(TextAlignment.CENTER);
            player3Walls.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
            player3Moves.setTextAlignment(TextAlignment.CENTER);
            player3Moves.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
            Text player3Title = new Text("Player 3");
            player3Title.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
            player3StatsPane.getChildren().addAll(player3Moves, player3Title, player3Walls);

            player4Walls.setTextAlignment(TextAlignment.CENTER);
            player4Walls.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
            player4Moves.setTextAlignment(TextAlignment.CENTER);
            player4Moves.setFont(Font.font("Calibri", FontWeight.NORMAL, 15));
            Text player4Title = new Text("Player 4");
            player4Title.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
            player4StatsPane.getChildren().addAll(player4Moves, player4Title, player4Walls);
        }
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
        pawn.setStroke(colour);
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

        grids[y][x].setFill(Color.YELLOW);
        grids[y][x].setStroke(Color.YELLOW);
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        for (int x1 = 0; x1 < width; x1++) {
                            for (int y1 = 0; y1 < width; y1++) {
                                if(x1 % 2 == 0 && y1 % 2 == 0) {
                                    grids[y1][x1].setFill(Color.WHITE);
                                    grids[y1][x1].setStroke(Color.WHITE);
                                }
                            }
                        }
                    }
                },
                1000
        );
    }

    public void updatePlayerMoveCount(int moveCount, int playerID) {
    	if (playerID == 1) {
    		player1Moves.setText((Translate.moves() + ": ") + moveCount);
    	}
    	else if (playerID == 2) {
    		player2Moves.setText((Translate.moves() + ": ") + moveCount);
    	} else if (playerID == 3) {
            player3Moves.setText((Translate.moves() + ": ") + moveCount);
        } else if (playerID == 4) {
            player4Moves.setText((Translate.moves() + ": ") + moveCount);
        }
    }

    public void updatePlayerWallCount(int wallCount, int playerID) {
    	if (playerID == 1) {
    		player1WallCount = wallCount;
            player1Walls.setText((Translate.walls() + ": ") + player1WallCount);
    	}
    	else if (playerID == 2) {
    		player2WallCount = wallCount;
            player2Walls.setText((Translate.walls() + ": ") + player2WallCount);
    	} else if (playerID == 3) {
            player3WallCount = wallCount;
            player3Walls.setText((Translate.walls() + ": ") + player3WallCount);
        } else if (playerID == 4) {
            player4WallCount = wallCount;
            player4Walls.setText((Translate.walls() + ": ") + player4WallCount);
        }
    }

    public void updatePlayerPawnPosition(int x, int y, int playerID) {
    	// convert the 9x9 coordinates from the controller to 18x8 coordinates for the GUI
    	int eighteenByEighteenX = x * 2;
    	int eighteenByEighteenY = y * 2;

    	if (playerID == 1) {
    		boardPane.getChildren().remove(firstPawn);
            boardPane.setConstraints(firstPawn, eighteenByEighteenX, eighteenByEighteenY);
            boardPane.getChildren().add(firstPawn);
    	}
    	else if (playerID == 2) {
    		boardPane.getChildren().remove(secondPawn);
            boardPane.setConstraints(secondPawn, eighteenByEighteenX, eighteenByEighteenY);
            boardPane.getChildren().add(secondPawn);
    	}
        else if (playerID == 3) {
            boardPane.getChildren().remove(thirdPawn);
            boardPane.setConstraints(thirdPawn, eighteenByEighteenX, eighteenByEighteenY);
            boardPane.getChildren().add(thirdPawn);
        }
        else if (playerID == 4) {
            boardPane.getChildren().remove(fourthPawn);
            boardPane.setConstraints(fourthPawn, eighteenByEighteenX, eighteenByEighteenY);
            boardPane.getChildren().add(fourthPawn);
        }
    }

    /**
     * change the active player to the next player
     */
    public void updateActivePlayer(int playerID) {
        if (playerID == 1) {
            currentPlayerText.setText("Player 1's turn...");
        }
        else if (playerID == 2) {
            currentPlayerText.setText("Player 2's turn...");
        } else if (playerID == 3) {
            currentPlayerText.setText("Player 3's turn...");
        } else if (playerID == 4) {
            currentPlayerText.setText("Player 4's turn...");
        }
    }

    public void displayWall(int topLeftX, int topLeftY, WallPlacement orientation, int playerID) {
    	if (orientation == WallPlacement.VERTICAL) {
    		int topX = ((topLeftX * 2) + 1);
    		int topY = topLeftY * 2;
    		int bottomX = topX;
    		int bottomY = topY + 2;

    		if (playerID == 1) {
	    		grids[topY][topX].setFill(Color.ORANGE);
	    		grids[topY][topX].setStroke(Color.ORANGE);
	    		grids[bottomY][bottomX].setFill(Color.ORANGE);
	    		grids[bottomY][bottomX].setStroke(Color.ORANGE);
    		} else if (playerID == 2) {
    			grids[topY][topX].setFill(Color.GREEN);
	    		grids[topY][topX].setStroke(Color.GREEN);
	    		grids[bottomY][bottomX].setFill(Color.GREEN);
	    		grids[bottomY][bottomX].setStroke(Color.GREEN);
    		} else if (playerID == 3) {
                grids[topY][topX].setFill(Color.BLUE);
                grids[topY][topX].setStroke(Color.BLUE);
                grids[bottomY][bottomX].setFill(Color.BLUE);
                grids[bottomY][bottomX].setStroke(Color.BLUE);
            } else if (playerID == 4) {
                grids[topY][topX].setFill(Color.RED);
                grids[topY][topX].setStroke(Color.RED);
                grids[bottomY][bottomX].setFill(Color.RED);
                grids[bottomY][bottomX].setStroke(Color.RED);
            }
    		grids[topY][topX].setOnMouseClicked(new EventHandler<MouseEvent>() {
        		@Override
        		public void handle(MouseEvent event) {
        			controller.removeWall((topX / 2), (topY / 2), WallPlacement.VERTICAL, controller.getCurrentPlayer().getID());
        		}
        	});
    	} else if (orientation == WallPlacement.HORIZONTAL) {
    		int leftX = topLeftX * 2;
    		int leftY = ((topLeftY * 2) + 1);
    		int rightX = leftX + 2;
    		int rightY = leftY;

    		if (playerID == 1) {
    			grids[leftY][leftX].setFill(Color.ORANGE);
	    		grids[leftY][leftX].setStroke(Color.ORANGE);
	    		grids[rightY][rightX].setFill(Color.ORANGE);
	    		grids[rightY][rightX].setStroke(Color.ORANGE);
    		} else if (playerID == 2) {
    			grids[leftY][leftX].setFill(Color.GREEN);
	    		grids[leftY][leftX].setStroke(Color.GREEN);
	    		grids[rightY][rightX].setFill(Color.GREEN);
	    		grids[rightY][rightX].setStroke(Color.GREEN);
            } else if (playerID == 3) {
                grids[leftY][leftX].setFill(Color.BLUE);
                grids[leftY][leftX].setStroke(Color.BLUE);
                grids[rightY][rightX].setFill(Color.BLUE);
                grids[rightY][rightX].setStroke(Color.BLUE);
            } else if (playerID == 4) {
                grids[leftY][leftX].setFill(Color.RED);
                grids[leftY][leftX].setStroke(Color.RED);
                grids[rightY][rightX].setFill(Color.RED);
                grids[rightY][rightX].setStroke(Color.RED);
            }
    		grids[leftY][leftX].setOnMouseClicked(new EventHandler<MouseEvent>() {
        		@Override
        		public void handle(MouseEvent event) {
        			controller.removeWall((leftX / 2), (leftY / 2), WallPlacement.HORIZONTAL, controller.getCurrentPlayer().getID());
        		}
        	});
    	}
    }

    public void removeWallDisplay(int topLeftX, int topLeftY, WallPlacement orientation) {
    	if (orientation == WallPlacement.VERTICAL) {
    		int topX = ((topLeftX * 2) + 1);
    		int topY = topLeftY * 2;
    		int bottomX = topX;
    		int bottomY = topY + 2;

			grids[topY][topX].setFill(Color.GREY);
    		grids[topY][topX].setStroke(Color.GREY);
    		grids[bottomY][bottomX].setFill(Color.GREY);
    		grids[bottomY][bottomX].setStroke(Color.GREY);
    		grids[topY][topX].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    placeWall(topX, topY);
                }
            });
    	} else if (orientation == WallPlacement.HORIZONTAL) {
    		int leftX = topLeftX * 2;
    		int leftY = ((topLeftY * 2) + 1);
    		int rightX = leftX + 2;
    		int rightY = leftY;

			grids[leftY][leftX].setFill(Color.GREY);
    		grids[leftY][leftX].setStroke(Color.GREY);
    		grids[rightY][rightX].setFill(Color.GREY);
    		grids[rightY][rightX].setStroke(Color.GREY);
    		grids[leftY][leftX].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    placeWall(leftX, leftY);
                }
            });
    	}
    }

    public void displayErrorMessage(String message) {
    	errorPaneText.setText(message);
    	new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        errorPaneText.setText("");
                    }
                },
                1000
        );
    }

    /**
     * Set the alignment of all instantiated fields to the centre of the pane
     */
    private void setPanes() {
        rootPane.setAlignment(Pos.CENTER);
        player1StatsPane.setAlignment(Pos.CENTER);
        currentPlayerPane.setAlignment(Pos.CENTER_RIGHT);
        errorPane.setAlignment(Pos.CENTER);
        player2StatsPane.setAlignment(Pos.CENTER);
        boardPane.setAlignment(Pos.CENTER);
        buttonPane.setAlignment(Pos.CENTER);
        //boardPane.setHgap(5);
        //boardPane.setVgap(5);
        if (numberOfPlayers == 2) {
            rootPane.getChildren().addAll(currentPlayerPane, player1StatsPane, boardPane, player2StatsPane, buttonPane, errorPane);
        } else {
            rootPane.getChildren().addAll(currentPlayerPane, player1StatsPane, player3StatsPane, boardPane, player2StatsPane, player4StatsPane, buttonPane, errorPane);
        }
        player1StatsPane.setPadding(new Insets(5, 0, 5, 0));
        player2StatsPane.setPadding(new Insets(5, 0, 5, 0));
        player3StatsPane.setAlignment(Pos.CENTER);
        player3StatsPane.setPadding(new Insets(5, 0, 5, 0));
        player4StatsPane.setAlignment(Pos.CENTER);
        player4StatsPane.setPadding(new Insets(5, 0, 5, 0));
        currentPlayerPane.setPadding(new Insets(0, 180, 0, 0));
        errorPane.setPadding(new Insets(5, 0, 0, 0));
    }

    /**
     * Create the board spaces and add them to the 2D array
     */
    private void initialiseBoardSpaces() {
    	for(int x = 0 ; x < width; x++){
    		for(int y = 0; y < width; y++){
    			grids[y][x] = new Rectangle();
    			// middle points between walls
    			if(x % 2 != 0 && y % 2 != 0) {
                    initialiseUnusedSquare(x, y);
    			}
    			// occupiable position
    			if(x % 2 == 0 && y % 2 == 0) {
                    initialiseOccupiableGrid(x, y);
    			}
    			// wide, short walls
    			if(x % 2 == 0 && y % 2 != 0) {
                    initialiseWideWall(x, y);
    			}
    			// tall, thin walls
    			if(x % 2 != 0 && y % 2 == 0) {
                    initialiseThinWall(x, y);
    			}
    		}
    	}
 	   highlightPositionsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               controller.showCurrentPlayerMoves();
           }
       });
       buttonPane.getChildren().add(highlightPositionsButton);
    }

    /**
     * Allows a user to place a wall on a wall space that is available
     */
   private void placeWall(int x, int y) {
       // tall, thin wall
	   if (x % 2 != 0) {
           placeThinWall(x, y);
       }
       //short, wide wall
       if (y % 2 != 0) {
    	   placeWideWall(x, y);
       }
   }

    /**
     * Set the occupiable positions
     * @param x
     * @param y
     */
    private void initialiseOccupiableGrid(int x, int y) {
        grids[y][x].setHeight(40);
        grids[y][x].setWidth(40);
        grids[y][x].setStroke(Color.WHITE);
        grids[y][x].setFill(Color.WHITE);
        boardPane.setConstraints(grids[y][x],x,y);
        boardPane.getChildren().add(grids[y][x]);
        grids[y][x].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // convert the 18x18 GUI coordinates to the 9x9 coordinates for the controller (the controller has a 9x9 model of the board)
                int nineByNineX = x / 2;
                int nineByNineY = y / 2;
            	controller.movePawn(nineByNineX, nineByNineY, controller.getCurrentPlayer().getID());
            }
        });
    }

    private void initialiseWideWall(int x, int y) {
        grids[y][x].setHeight(10);
        grids[y][x].setWidth(40);
        grids[y][x].setStroke(Color.GREY);
        grids[y][x].setFill(Color.GREY);
        boardPane.setConstraints(grids[y][x],x,y);
        boardPane.getChildren().add(grids[y][x]);
        grids[y][x].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                placeWall(x, y);
            }
        });
    }

    private void initialiseThinWall(int x, int y) {
        grids[y][x].setWidth(10);
        grids[y][x].setHeight(40);
        grids[y][x].setStroke(Color.GREY);
        grids[y][x].setFill(Color.GREY);
        boardPane.setConstraints(grids[y][x],x,y);
        boardPane.getChildren().add(grids[y][x]);
        grids[y][x].setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                placeWall(x, y);
            }
        });
    }

    private void initialiseUnusedSquare(int x, int y) {
        grids[y][x].setHeight(10);
        grids[y][x].setWidth(10);
        grids[y][x].setStroke(Color.rgb(192, 192, 192));
        grids[y][x].setFill(Color.rgb(192, 192, 192));
        boardPane.setConstraints(grids[y][x],x,y);
        boardPane.getChildren().add(grids[y][x]);
    }

    private void placeThinWall(int x, int y) {
        // coordinates of the position to the top left of the horizontal wall
        int topLeftPosX = x / 2;
        int topLeftPosY = y / 2;
        controller.placeWall(topLeftPosX, topLeftPosY, WallPlacement.VERTICAL, controller.getCurrentPlayer().getID());
    }

    private void placeWideWall(int x, int y) {
        // coordinates of the position to the top left of the vertical wall
        int topLeftPosX = x / 2;
        int topLeftPosY = y / 2;
    	controller.placeWall(topLeftPosX, topLeftPosY, WallPlacement.HORIZONTAL, controller.getCurrentPlayer().getID());
    }
}
