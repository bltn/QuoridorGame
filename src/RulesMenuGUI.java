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
 * RulesMenuGUI allows a player to choose the rules for the game they are about to play.
 * It is only used for a local game. It allows to player to choose whether the game is
 * two players or four players and whether the game is going to use the standard rules
 * or the challenge rules. It also allows the player to play a practice game against an
 * AI player. This class extends the Application class from JavaFX.
 *
 * @author Khadija Patel
 * @author Ben Lawton
 * @author Junaid Rasheed
 * @author Thai Hoang
 */
public class RulesMenuGUI extends Application {

    private Scene scene;
    private GridPane pane;
    private Text introText;
    private Text standardText;
    private Text challengeText;
    private Text practiseText;
    private VBox buttonBox;
    private HBox standardButtonBox;
    private HBox challengeButtonBox;
    private HBox practiseButtonBox;
    private HBox quitButtonBox;
    private Button backButton;
    private Button quitButton;
	private Button practiseButton;
    private Button standardButton;
    private Button fourPlayerStandardButton;
    private Button challengeButton;
    private Button fourPlayerChallengeButton;
    private Stage primaryStage;

    public RulesMenuGUI() {
    	pane = new GridPane();
        introText = new Text(Translate.rules());
        buttonBox = new VBox(70);
        practiseText = new Text(Translate.practiseMode() + ":");
        standardText = new Text(Translate.standardRules() + ":");
        challengeText= new Text(Translate.challengeRules() + ":");

        practiseButtonBox = new HBox(10);
        standardButtonBox = new HBox(10);
        challengeButtonBox = new HBox(10);
        quitButtonBox = new HBox(10);

        // add an icon to the practise mode button
        Image practiseImg = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView newPractiseImg = new ImageView(practiseImg);
        newPractiseImg.setFitHeight(20);
        newPractiseImg.setFitWidth(20);
        practiseButton = new Button(Translate.practiseMode(), newPractiseImg);

        //add an icon into the 2P standard button
        Image standard2 = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView newStandard2 = new ImageView(standard2);
        newStandard2.setFitHeight(20);
        newStandard2.setFitWidth(20);
        standardButton = new Button(Translate.twoPlayerStandard(), newStandard2);

        //add an icon into the 4P standard button
        Image standard4 = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView newStandard4 = new ImageView(standard4);
        newStandard4.setFitHeight(20);
        newStandard4.setFitWidth(45);
        fourPlayerStandardButton = new Button(Translate.fourPlayerStandard(), newStandard4);

        //add an icon into the 2P challenge button
        Image challenge2 = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView NewChallenge2 = new ImageView(challenge2);
        NewChallenge2.setFitHeight(20);
        NewChallenge2.setFitWidth(20);
        challengeButton = new Button(Translate.twoPlayerChallenge(), NewChallenge2);

        //add an icon into the 4P challenge button
        Image challenge4 = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView NewChallenge4 = new ImageView(challenge4);
        NewChallenge4.setFitHeight(20);
        NewChallenge4.setFitWidth(45);
        fourPlayerChallengeButton = new Button(Translate.fourPlayerChallenge(), NewChallenge4);

        // add an icon into the quit button
        Image quit = new Image(getClass().getResourceAsStream("icons/quit.png"));
        ImageView newQuit = new ImageView(quit);
        newQuit.setFitHeight(20);
        newQuit.setFitWidth(20);
        quitButton = new Button(Translate.quit(), newQuit);

        // add an icon into the back button
        Image back = new Image(getClass().getResourceAsStream("icons/back.png"));
        ImageView newBack = new ImageView(back);
        newBack.setFitHeight(20);
        newBack.setFitWidth(20);
        backButton = new Button(Translate.back(), newBack);

        // add background image
        Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
        BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage bimg = new BackgroundImage(background,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER, size);

        pane.setBackground(new Background(bimg));
        scene = new Scene(pane, 600, 400);
        scene.getStylesheets().add(SettingsGUI.theme);
    }

    /**
     * Set up the GUI and then draw it
     * @param primaryStage The window to use to draw the GUI
     */
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
    	pane.setAlignment(Pos.CENTER);
        pane.setHgap(25);
        pane.setVgap(70);
        pane.add(buttonBox, 0, 1);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setFont(Font.font("Agency FB", FontWeight.BOLD, 70));
        pane.add(introText, 0, 0, 1, 1);
        introText.setId("text");
    }

    /**
     * Create the buttons for the menu and set their properties
     */
    public void setButtons() {
    	practiseText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
        standardText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
        challengeText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(introText, practiseButtonBox, standardButtonBox, challengeButtonBox,quitButtonBox);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 15, 15, 15));

		backButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
		quitButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	quitButtonBox.getChildren().addAll(backButton, quitButton);
    	quitButtonBox.setSpacing(10);
    	quitButtonBox.setAlignment(Pos.CENTER_RIGHT);

    	practiseButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	practiseButtonBox.getChildren().addAll(practiseText, practiseButton);
    	practiseButtonBox.setSpacing(10);
    	practiseButtonBox.setAlignment(Pos.CENTER);

    	standardButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	fourPlayerStandardButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	standardButtonBox.getChildren().addAll(standardText, standardButton, fourPlayerStandardButton);
    	standardButtonBox.setSpacing(10);
    	standardButtonBox.setAlignment(Pos.CENTER);

    	challengeButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	fourPlayerChallengeButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	challengeButtonBox.getChildren().addAll(challengeText, challengeButton, fourPlayerChallengeButton);
    	challengeButtonBox.setSpacing(10);
    	challengeButtonBox.setAlignment(Pos.CENTER);

        practiseButton.setPrefWidth(200);
        practiseButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
    			LocalBoardGUI gui = new LocalBoardGUI(false);
    			StandardBoard board = new StandardBoard(false);
            	Controller controller = new AIGameController(gui, board);
            	gui.setController(controller);
            	gui.start(new Stage());
            	primaryStage.close();
        	}
        });

        standardButton.setPrefWidth(200);
        standardButton.setOnAction(new EventHandler<ActionEvent>(){
        	@Override
        	public void handle(ActionEvent event) {
                PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(false, "standard");
                playerNamesGUI.start(primaryStage);
        	}
        });

        challengeButton.setPrefWidth(200);
        challengeButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(false, "challenge");
                playerNamesGUI.start(primaryStage);
			}
        });

        fourPlayerStandardButton.setPrefWidth(200);
        fourPlayerStandardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(true, "standard");
                playerNamesGUI.start(primaryStage);
            }
        });
        fourPlayerChallengeButton.setPrefWidth(200);
        fourPlayerChallengeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(true, "challenge");
                playerNamesGUI.start(primaryStage);
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
