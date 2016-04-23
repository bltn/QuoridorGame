import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class MenuGUI extends Application {

    private Scene scene;
    private GridPane introPane;
    private Text introText;
    private VBox buttonBox;
    private Button startButton;
    private Button quitButton;
    private Button multiplayerButton;

    public MenuGUI() {
    	LanguageFileHandler.setLanguage("English");
    	introPane = new GridPane();
    	introText = new Text("Quoridor");
    	buttonBox = new VBox();
    	introText.setId("text");

    	// add a icon into the start button
    	Image start = new Image(getClass().getResourceAsStream("icons/start.png"));
    	ImageView changeSizeOfStart = new ImageView(start);
    	changeSizeOfStart.setFitHeight(20);
    	changeSizeOfStart.setFitWidth(20);
    	startButton = new Button(LanguageFileHandler.getLocalMode(),changeSizeOfStart);

    	//add a icon into quit button
    	Image quit = new Image(getClass().getResourceAsStream("icons/quit.png"));
    	ImageView newQuit = new ImageView(quit);
    	newQuit.setFitHeight(20);
    	newQuit.setFitWidth(20);
    	quitButton = new Button(LanguageFileHandler.getQuit(),newQuit);

    	//add a icon into the multiplayer button
    	Image multiplayer = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
    	ImageView NewMultiplayer = new ImageView(multiplayer);
    	NewMultiplayer.setFitHeight(20);
    	NewMultiplayer.setFitWidth(20);
    	multiplayerButton = new Button(LanguageFileHandler.getMultiplayer(),NewMultiplayer);

    	//add background image
    	Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
    	BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
		BackgroundSize.AUTO, false, false, true, true);
    	BackgroundImage bimg = new BackgroundImage(background,
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
		BackgroundPosition.CENTER, size);

    	introPane.setBackground(new Background(bimg));
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
        introText.setFont(Font.font("Agency FB", FontWeight.BOLD, 70));
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
        startButton.setPrefWidth(350);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	RulesMenuGUI gui = new RulesMenuGUI();
            	gui.start(new Stage());
            };
        });
        quitButton.setPrefWidth(350);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        multiplayerButton.setPrefWidth(350);
        multiplayerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ConnectionGUI connGUI = new ConnectionGUI();
				connGUI.start(new Stage());
			}
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
