import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;

import javax.sound.midi.SysexMessage;

/**
 * @author Khadija Patel
 *
 */
public class RulesMenuGUI extends Application {

    private Scene scene;
    private GridPane introPane;
    private Text introText;
    private VBox buttonBox;
    private HBox standardButtonBox;
    private HBox challengeButtonBox;
    private Button startButton;
    private Button quitButton;
    private Button multiplayerButton;
    private Button standardButton;
    private Button fourPlayerStandardButton;
    private Button challengeButton;
    private Button fourPlayerChallengeButton;
    private Stage primaryStage;

    public RulesMenuGUI() {
        introPane = new GridPane();
        introText = new Text("Please pick the set of rules you would like to play with:");
        buttonBox = new VBox(10);
        standardButtonBox = new HBox(10);
        challengeButtonBox = new HBox(10);
        quitButton = new Button("Quit");
        standardButton = new Button("2P Standard");
        fourPlayerStandardButton = new Button("4P Standard");
        challengeButton = new Button("2P Challenge");
        fourPlayerChallengeButton = new Button("4P Challenge");
        scene = new Scene(introPane, 600, 400);
        scene.getStylesheets().add("Theme.css");
    }

    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        primaryStage.setTitle("Quoridor");
        setButtons();
        setIntroPane();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * create the pane for the title
     */
    public void setIntroPane() {
        introPane.setAlignment(Pos.CENTER);
        introPane.setHgap(15);
        introPane.setVgap(80);
        introPane.add(buttonBox, 0, 1);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setFont(Font.font("Calibri", FontWeight.BOLD, 15));
        introPane.add(introText, 0, 0, 1, 1);
    }

    /**
     * Create the buttons for the menu and set their properties
     */
    public void setButtons() {
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        standardButtonBox.getChildren().addAll(standardButton, fourPlayerStandardButton);
        challengeButtonBox.getChildren().addAll(challengeButton, fourPlayerChallengeButton);
        buttonBox.getChildren().addAll(standardButtonBox, challengeButtonBox, quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        standardButton.setPrefWidth(200);
        standardButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
    			LocalBoardGUI gui = new LocalBoardGUI();
            	Board board = new StandardBoard();
            	Controller controller = new LocalGameController(gui, board);
            	gui.setController(controller);
            	gui.start(new Stage());
            	primaryStage.close();
        	}
        });

        challengeButton.setPrefWidth(200);
        challengeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
	            LocalBoardGUI gui = new LocalBoardGUI();
	            Board board = new ChallengeBoard();
	            Controller controller = new LocalGameController(gui, board);
	            gui.setController(controller);
	            gui.start(new Stage());
	            primaryStage.close();
			}
        });

        fourPlayerStandardButton.setPrefWidth(200);
        fourPlayerStandardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalBoardGUI gui = new LocalBoardGUI();
                Board board = new StandardBoard();
                Controller controller = new LocalGameController(gui, board);
                gui.setController(controller);
                gui.start(new Stage());
                primaryStage.close();
            }
        });
        fourPlayerChallengeButton.setPrefWidth(200);
        fourPlayerChallengeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LocalBoardGUI gui = new LocalBoardGUI();
                Board board = new ChallengeBoard();
                Controller controller = new LocalGameController(gui, board);
                gui.setController(controller);
                gui.start(new Stage());
                primaryStage.close();
            }
        });
        quitButton.setPrefWidth(200);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }
}
