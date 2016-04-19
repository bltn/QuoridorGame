import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * @author Khadija Patel
 *
 */
public class RulesMenuGUI extends Application {

    private Scene scene;
    private GridPane introPane;
    private Text introText;
    private Text standardText;
    private Text challengeText;
    private VBox buttonBox;
    private HBox standardButtonBox;
    private HBox challengeButtonBox;
    private HBox quitButtonBox;
    private Button backButton;
    private Button quitButton;
    private Button standardButton;
    private Button fourPlayerStandardButton;
    private Button challengeButton;
    private Button fourPlayerChallengeButton;
    private Stage primaryStage;

    public RulesMenuGUI() {
        introPane = new GridPane();
        introText = new Text("Rules");
        buttonBox = new VBox(10);
        standardText = new Text("Standard Rules:");
        challengeText= new Text("Challenge Rules:");
        standardButtonBox = new HBox(10);
        challengeButtonBox = new HBox(10);
        quitButtonBox = new HBox(10);

        
        Image standard2 = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView newStandard2 = new ImageView(standard2);
        newStandard2.setFitHeight(20);
        newStandard2.setFitWidth(20);
        standardButton = new Button("2P Standard",newStandard2);
        
        Image standard4 = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView newStandard4 = new ImageView(standard4);
        newStandard4.setFitHeight(20);
        newStandard4.setFitWidth(45);
        fourPlayerStandardButton = new Button("4P Standard",newStandard4);
        
        Image challenge2 = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView NewChallenge2 = new ImageView(challenge2);
        NewChallenge2.setFitHeight(20);
        NewChallenge2.setFitWidth(20);
        challengeButton = new Button("2P Challenge",NewChallenge2);
        
        Image challenge4 = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView NewChallenge4 = new ImageView(challenge4);
        NewChallenge4.setFitHeight(20);
        NewChallenge4.setFitWidth(45);
        fourPlayerChallengeButton = new Button("4P Challenge",NewChallenge4);
        
        Image quit = new Image(getClass().getResourceAsStream("icons/quit.png"));
        ImageView newQuit = new ImageView(quit);
        newQuit.setFitHeight(20);
        newQuit.setFitWidth(20);
        quitButton = new Button("Quit",newQuit);
        
        Image back = new Image(getClass().getResourceAsStream("icons/back.png"));
        ImageView newBack = new ImageView(back);
        newBack.setFitHeight(20);
        newBack.setFitWidth(20);
        backButton = new Button("Back",newBack);
        
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
        introPane.setHgap(25);
        introPane.setVgap(100);
        introPane.add(buttonBox, 0, 1);
        introPane.add(introText, 0, 0, 1, 1);
        introText.setId("text");
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setFont(Font.font("Agency FB", FontWeight.BOLD, 70));
    }

    /**
     * Create the buttons for the menu and set their properties
     */
    public void setButtons() {
        standardText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
        challengeText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        standardButtonBox.getChildren().addAll(standardText, standardButton, fourPlayerStandardButton);
        challengeButtonBox.getChildren().addAll(challengeText, challengeButton, fourPlayerChallengeButton);
        quitButtonBox.getChildren().addAll(backButton, quitButton);
        quitButtonBox.setAlignment(Pos.CENTER_RIGHT);
        standardButtonBox.setAlignment(Pos.CENTER_RIGHT);
        challengeButtonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(standardButtonBox, challengeButtonBox, quitButtonBox);
        standardButton.setPrefWidth(200);
        standardButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
                LocalBoardGUI gui = new LocalBoardGUI(2);
            	Board board = new StandardBoard(2);
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
	            LocalBoardGUI gui = new LocalBoardGUI(2);
	            Board board = new ChallengeBoard(2);
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
                LocalBoardGUI gui = new LocalBoardGUI(4);
                Board board = new StandardBoard(4);
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
                LocalBoardGUI gui = new LocalBoardGUI(4);
                Board board = new ChallengeBoard(4);
                Controller controller = new LocalGameController(gui, board);
                gui.setController(controller);
                gui.start(new Stage());
                primaryStage.close();
            }
        });
        
        backButton.setPrefWidth(200);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              
                        MenuGUI gui = new MenuGUI();
                        gui.start(new Stage());

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
