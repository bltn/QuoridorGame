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
import java.io.IOException;

import javax.sound.midi.SysexMessage;

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
    private Button startButton;
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
        introText = new Text("Rules");
        buttonBox = new VBox(100);
        practiseText = new Text("Practise mode:");
        standardText = new Text("Standard Rules:");
        challengeText= new Text("Challenge Rules:");

        practiseButtonBox = new HBox(10);
        standardButtonBox = new HBox(10);
        challengeButtonBox = new HBox(10);
        quitButtonBox = new HBox(10);

        // add icon to the practise mode button
        Image practiseImg = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView newPractiseImg = new ImageView(practiseImg);
        newPractiseImg.setFitHeight(20);
        newPractiseImg.setFitWidth(20);
        practiseButton = new Button("Practise mode", newPractiseImg);

        //add a icon into the 2P standard button
        Image standard2 = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView newStandard2 = new ImageView(standard2);
        newStandard2.setFitHeight(20);
        newStandard2.setFitWidth(20);
        standardButton = new Button("2P Standard", newStandard2);

        //add a icon into the 4P standard button
        Image standard4 = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView newStandard4 = new ImageView(standard4);
        newStandard4.setFitHeight(20);
        newStandard4.setFitWidth(45);
        fourPlayerStandardButton = new Button("4P Standard", newStandard4);

        //add a icon into the 2P challenge button
        Image challenge2 = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView NewChallenge2 = new ImageView(challenge2);
        NewChallenge2.setFitHeight(20);
        NewChallenge2.setFitWidth(20);
        challengeButton = new Button("2P Challenge", NewChallenge2);

        //add a icon into the 4P challenge button
        Image challenge4 = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView NewChallenge4 = new ImageView(challenge4);
        NewChallenge4.setFitHeight(20);
        NewChallenge4.setFitWidth(45);
        fourPlayerChallengeButton = new Button("4P Challenge", NewChallenge4);

        // add a icon into the quit button
        Image quit = new Image(getClass().getResourceAsStream("icons/quit.png"));
        ImageView newQuit = new ImageView(quit);
        newQuit.setFitHeight(20);
        newQuit.setFitWidth(20);
        quitButton = new Button("Quit", newQuit);

        // add a icon into the back button
        Image back = new Image(getClass().getResourceAsStream("icons/back.png"));
        ImageView newBack = new ImageView(back);
        newBack.setFitHeight(20);
        newBack.setFitWidth(20);
        backButton = new Button("Back", newBack);

        //add background image
        Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
        BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage bimg = new BackgroundImage(background,
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.CENTER, size);

        pane.setBackground(new Background(bimg));
        scene = new Scene(pane, 600, 400);
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
    	pane.setAlignment(Pos.CENTER);
        pane.setHgap(25);
        pane.setVgap(100);
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
    			LocalBoardGUI gui = new LocalBoardGUI(2);
    			StandardBoard board = new StandardBoard(2);
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
