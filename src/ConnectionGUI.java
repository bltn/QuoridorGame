import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
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

public class ConnectionGUI extends Application {

	private Scene scene;
	private GridPane pane;
	private Text joinText;
	private VBox buttonBox;
	private HBox quitButtonBox;
	private Button createServerButton;
	private Button connectToServerButton;
	private Button connectTo4PServerButton;
	private Text IPandPortInfo;
	private TextField IPAddressField;
	private TextField portField;
	private String IPAddress;
	private int portNumber;

    private Button backButton;
    private Button quitButton;
    private Stage primaryStage;

	public ConnectionGUI() {
		IPAddress = "localhost";
		portNumber = 33333;
		pane = new GridPane();
		joinText = new Text(Translate.joinGame() + ": ");
		buttonBox = new VBox();
		createServerButton = new Button(Translate.createServer() + ": ");
		connectToServerButton = new Button(Translate.connectToGame());
		connectTo4PServerButton = new Button(Translate.connectToFourPlayerGame());
		quitButtonBox = new HBox();

		scene = new Scene(pane, 600, 800);
		IPandPortInfo = new Text(Translate.enterIPAndPort());
		IPAddressField = new TextField(IPAddress);
		portField = new TextField("" + portNumber);

        //add a icon into the game server button
		Image server = new Image(getClass().getResourceAsStream("icons/server.png"));
        ImageView newServerButton = new ImageView(server);
        newServerButton.setFitHeight(20);
        newServerButton.setFitWidth(40);
        createServerButton = new Button(Translate.createServer(),newServerButton);

        //add a icon into the 2P game button
		Image standard = new Image(getClass().getResourceAsStream("icons/multiplayers.png"));
        ImageView newStandardButton = new ImageView(standard);
        newStandardButton.setFitHeight(20);
        newStandardButton.setFitWidth(20);
        connectToServerButton = new Button(Translate.connectToTwoPlayerGame(),newStandardButton);

        //add a icon into the 4P game button
        Image fourplayer = new Image(getClass().getResourceAsStream("icons/4players.png"));
        ImageView new4PButton = new ImageView(fourplayer);
        new4PButton.setFitHeight(20);
        new4PButton.setFitWidth(45);
        connectTo4PServerButton = new Button(Translate.connectToFourPlayerGame(),new4PButton);

        // add a icon into the quit button
        Image quit = new Image(getClass().getResourceAsStream("icons/quit.png"));
        ImageView newQuit = new ImageView(quit);
        newQuit.setFitHeight(20);
        newQuit.setFitWidth(20);
        quitButton = new Button(Translate.quit(),newQuit);

        // add a icon into the back button
        Image back = new Image(getClass().getResourceAsStream("icons/back.png"));
        ImageView newBack = new ImageView(back);
        newBack.setFitHeight(20);
        newBack.setFitWidth(20);
        backButton = new Button(Translate.back(),newBack);

        //add a background image
	    Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
	    BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
	    BackgroundSize.AUTO, false, false, true, true);
	    BackgroundImage bimg = new BackgroundImage(background,
	    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
	    BackgroundPosition.CENTER, size);
	    pane.setBackground(new Background(bimg));

	    scene.getStylesheets().add(SettingsGUI.theme);
	}

	@Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        primaryStage.setTitle(Translate.joinGame());
        setButtons();
        setServerPane();
        setTextFields();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public void setTextFields() {
        IPAddressField.setLayoutX(50);
        IPAddressField.setLayoutY(50);
        IPandPortInfo.setLayoutX(50);
        IPandPortInfo.setLayoutY(IPAddressField.getLayoutY() - 20);
        portField.setLayoutX(IPAddressField.getLayoutX());
        portField.setLayoutY(IPAddressField.getLayoutY() + 30);
    }

	public void setServerPane() {
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(25);
        pane.setVgap(150);
        pane.add(buttonBox, 0, 1);
        joinText.setTextAlignment(TextAlignment.CENTER);
        joinText.setFont(Font.font("Agency FB", FontWeight.BOLD, 70));
        pane.add(joinText, 0, 0, 1, 1);
        joinText.setId("text");
    }

	public void setButtons() {
		createServerButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
		connectToServerButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
		connectTo4PServerButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
		IPandPortInfo.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
		backButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));

        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(IPandPortInfo, IPAddressField, portField, createServerButton, connectToServerButton, connectTo4PServerButton, quitButtonBox);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
		quitButton.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 15));
    	quitButtonBox.getChildren().addAll(backButton, quitButton);
    	quitButtonBox.setSpacing(10);
    	quitButtonBox.setAlignment(Pos.CENTER);
        createServerButton.setPrefWidth(250);
        createServerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	SystemLogger.init();
            	String mode = askForGameMode();
            	GameServer server;
				IPAddress = IPAddressField.getText();
				portNumber = Integer.parseInt(portField.getText());
            	if (mode.equals(Translate.twoPlayerChallenge())) {
            		server = new GameServer(new NetworkedGameController(new ChallengeBoard(false)), 2);
					PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(false, "multiplayer", server, IPAddress, portNumber);
					playerNamesGUI.start(new Stage());
            	} else if (mode.equals(Translate.twoPlayerStandard())){
            		server = new GameServer(new NetworkedGameController(new StandardBoard(false)), 2);
					PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(false, "multiplayer", server, IPAddress, portNumber);
					playerNamesGUI.start(new Stage());
            	} else if (mode.equals(Translate.fourPlayerStandard())) {
					server = new GameServer(new NetworkedGameController(new StandardBoard(true)), 4);
					PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(true, "multiplayer", server, IPAddress, portNumber);
					playerNamesGUI.start(new Stage());
				} else {
					server = new GameServer(new NetworkedGameController(new ChallengeBoard(true)), 4);
					PlayerNamesGUI playerNamesGUI = new PlayerNamesGUI(true, "multiplayer", server, IPAddress, portNumber);
					playerNamesGUI.start(new Stage());
				}
            }
        });
        connectToServerButton.setPrefWidth(250);
        connectToServerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	SystemLogger.init();
                IPAddress = IPAddressField.getText();
                portNumber = Integer.parseInt(portField.getText());
                GUI gui = new NetworkedBoardGUI(2);
				GameClient client = new GameClient(gui);
                client.connectToServer(IPAddress, portNumber);
                while (client.guiIsLaunched() == false) {
                	try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						SystemLogger.logError(e.getMessage());
					}
                	if (client.guiCanBeLaunched()) {
                		client.setGUILaunched(true);
                        ((NetworkedBoardGUI) gui).setClient(client);
                		gui.start(new Stage());
                	}
                }
            }
        });
		connectTo4PServerButton.setPrefWidth(250);
		connectTo4PServerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				IPAddress = IPAddressField.getText();
				portNumber = Integer.parseInt(portField.getText());
				GUI gui = new NetworkedBoardGUI(4);
				GameClient client = new GameClient(gui);
				client.connectToServer(IPAddress, portNumber);
				while (client.guiIsLaunched() == false) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						SystemLogger.logError(e.getMessage());
					}
					if (client.guiCanBeLaunched()) {
						client.setGUILaunched(true);
						((NetworkedBoardGUI) gui).setClient(client);
						gui.start(new Stage());
					}
				}
			}
		});
        backButton.setPrefWidth(120);
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                        MenuGUI gui = new MenuGUI();
                        gui.start(new Stage());
                        primaryStage.close();
            }
        });
        quitButton.setPrefWidth(120);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

	private String askForGameMode() {
		String mode = null;
		ArrayList<String> choices = new ArrayList();
		choices.add(Translate.twoPlayerStandard());
		choices.add(Translate.twoPlayerChallenge());
		choices.add(Translate.fourPlayerStandard());
		choices.add(Translate.fourPlayerChallenge());

		ChoiceDialog<String> dialog = new ChoiceDialog<>(Translate.twoPlayerStandard(), choices);
		dialog.setTitle(Translate.gameModes());
		dialog.setHeaderText(Translate.chooseGameMode());
		dialog.setContentText(Translate.mode() + ": ");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			mode = result.get();
		}
		return mode;
	}
}
