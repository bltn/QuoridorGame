import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PlayerNamesGUI extends Application {

    private Scene scene;
    private GridPane namesPane;
    private Text namesText;
    private Text instructionsText;
    public static TextField player1Name;
    public static TextField player2Name;
    public static TextField player3Name;
    public static TextField player4Name;
    private HBox nameBoxRow1;
    private HBox nameBoxRow2;
    private VBox buttonBox;
    private Button startButton;
    private boolean fourPlayerMode;
    private String gameMode;
    private Stage stage;
    private GameServer server;
    private String IPAddress;
    private int portNumber;

    /**
     * A GUI which allows players to set their names. These
     * names are used to store the win/loss stats in a csv
     * file.
     * @param numberOfPlayers The number of players in the game
     * @param gameMode The name of the game mode
     */
    public PlayerNamesGUI(boolean fourPlayerMode, String gameMode) {
        Translate.setLanguage(SettingsGUI.language);
        this.fourPlayerMode = fourPlayerMode;
        this.gameMode = gameMode;

        namesPane = new GridPane();
        namesText = new Text(Translate.name());
        instructionsText = new Text(Translate.enterNames());
        player1Name = new TextField("Player 1");
        player2Name = new TextField("Player 2");
        player3Name = new TextField("Player 3");
        player4Name = new TextField("Player 4");
        nameBoxRow1 = new HBox();
        nameBoxRow2 = new HBox();
        buttonBox = new VBox();
        namesText.setId("text");

        // add a icon into the start button
        Image start = new Image(getClass().getResourceAsStream("icons/start.png"));
        ImageView changeSizeOfStart = new ImageView(start);
        changeSizeOfStart.setFitHeight(20);
        changeSizeOfStart.setFitWidth(20);
        startButton = new Button(Translate.play(), changeSizeOfStart);

        //add background image
        Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage bimg = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        namesPane.setBackground(new Background(bimg));
        scene = new Scene(namesPane, 600, 400);
        scene.getStylesheets().add(SettingsGUI.theme);
    }

    /**
     * A GUI which allows players to set their names. These
     * names are used to store the win/loss stats in a csv
     * file. This constructor is used for multiplayer games.
     * @param numberOfPlayers The number of players in the game
     * @param gameMode The name of the game mode
     * @param server The server to be used
     * @param IPAddress IP Address of the server
     * @param portNumber The port number for the server
     */
    public PlayerNamesGUI(boolean fourPlayerMode, String gameMode, GameServer server, String IPAddress, int portNumber) {
        Translate.setLanguage(SettingsGUI.language);
        this.fourPlayerMode = fourPlayerMode;
        this.gameMode = gameMode;
        this.server = server;
        this.IPAddress = IPAddress;
        this.portNumber = portNumber;

        namesPane = new GridPane();
        namesText = new Text(Translate.name());
        instructionsText = new Text(Translate.enterNames());
        player1Name = new TextField("Player 1");
        player2Name = new TextField("Player 2");
        player3Name = new TextField("Player 3");
        player4Name = new TextField("Player 4");
        nameBoxRow1 = new HBox();
        nameBoxRow2 = new HBox();
        buttonBox = new VBox();
        namesText.setId("text");

        // add a icon into the start button
        Image start = new Image(getClass().getResourceAsStream("icons/start.png"));
        ImageView changeSizeOfStart = new ImageView(start);
        changeSizeOfStart.setFitHeight(20);
        changeSizeOfStart.setFitWidth(20);
        startButton = new Button(Translate.play(), changeSizeOfStart);

        //add background image
        Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage bimg = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        namesPane.setBackground(new Background(bimg));
        scene = new Scene(namesPane, 600, 400);
        scene.getStylesheets().add(SettingsGUI.theme);

    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.setTitle("Quoridor");
        setButtons();
        setIntroPane();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * create the pane for the title
     */
    public void setIntroPane() {
        namesPane.setAlignment(Pos.CENTER);
        namesPane.setHgap(25);
        namesPane.setVgap(50);
        namesPane.add(buttonBox, 0, 1);
        namesText.setTextAlignment(TextAlignment.CENTER);
        namesText.setFont(Font.font("Agency FB", FontWeight.BOLD, 70));
        namesPane.add(namesText, 0, 0, 1, 1);
    }

    /**
     * Create the buttons for the menu and set their properties
     */
    public void setButtons() {
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        nameBoxRow1.setSpacing(10);
        nameBoxRow2.setSpacing(10);
        nameBoxRow1.getChildren().addAll(player1Name, player2Name);
        nameBoxRow2.getChildren().addAll(player3Name, player4Name);
        instructionsText.setTextAlignment(TextAlignment.CENTER);
        instructionsText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
        buttonBox.getChildren().add(instructionsText);
        buttonBox.getChildren().add(nameBoxRow1);
        if (fourPlayerMode) {
            buttonBox.getChildren().add(nameBoxRow2);
        }
        buttonBox.getChildren().add(startButton);
        buttonBox.setAlignment(Pos.CENTER);
        startButton.setPrefWidth(300);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (gameMode.equals("standard")) {
                    LocalBoardGUI gui = new LocalBoardGUI(fourPlayerMode);
                    Board board = new StandardBoard(fourPlayerMode);
                    Controller controller = new LocalGameController(gui, board);
                    gui.setController(controller);
                    gui.start(new Stage());
                    stage.close();
                } else if (gameMode.equals("challenge")) {
                    LocalBoardGUI gui = new LocalBoardGUI(fourPlayerMode);
                    Board board = new ChallengeBoard(fourPlayerMode);
                    Controller controller = new LocalGameController(gui, board);
                    gui.setController(controller);
                    gui.start(new Stage());
                    stage.close();
                } else if (gameMode.equals("multiplayer")) {
                    server.initialiseServer(IPAddress, portNumber);
                }
            };
        });
    }
}


