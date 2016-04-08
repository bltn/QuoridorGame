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
 * @author Junaid Rasheed
 * @author Jordan Bird
 *
 * @version 08/04/2016
 */
public class MenuGUI extends Application {

    private Scene scene;
    private GridPane introPane;
    private Text introText;
    private VBox buttonBox;
    private Button startButton;
    private Button quitButton;
    private Button multiplayerButton;

    public MenuGUI() throws IOException {
    	LanguageFileHandler language = new LanguageFileHandler("English");
        introPane = new GridPane();
        introText = new Text("Quoridor");
        buttonBox = new VBox();
        startButton = new Button(language.getStart());
        quitButton = new Button(language.getQuit());
        multiplayerButton = new Button(language.getMultiplayer());
        scene = new Scene(introPane, 600, 400);
        scene.getStylesheets().add("Theme.css");
        
        
        
    }

    @Override
    public void start(Stage primaryStage) {
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
        introPane.setHgap(25);
        introPane.setVgap(100);
        introPane.add(buttonBox, 0, 1);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
        introPane.add(introText, 0, 0, 1, 1);
    }

    /**
     * Create the buttons for the menu and set their properties
     */
    public void setButtons() {
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().add(startButton);
        buttonBox.getChildren().add(multiplayerButton);
        buttonBox.getChildren().add(quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        startButton.setPrefWidth(150);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	LocalBoardGUI gui = new LocalBoardGUI();
            	Board board = new Board();
            	Controller controller = new LocalGameController(gui, board);
            	gui.setController(controller);
            	gui.start(new Stage());
            };
        });
        quitButton.setPrefWidth(150);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        multiplayerButton.setPrefWidth(150);
        multiplayerButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				GUI gui = new NetworkedBoardGUI();
				GameClient client = new GameClient(gui);
				GameServer server = new GameServer(new NetworkedGameController(new Board()));
				ConnectionGUI connGUI = new ConnectionGUI(server, client);
				connGUI.start(new Stage());
			}
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
