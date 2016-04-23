import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ConnectionGUI extends Application {

	private Scene scene;
	private GridPane pane;
	private Label joinText;
    private static StringProperty joinTextValue;
	private VBox buttonBox;
	private Button createServerButton;
	private Button connectToServerButton;
	private Button connectTo4PServerButton;
	private Label IPandPortInfo;
	private TextField IPAddressField;
	private TextField portField;
	private String IPAddress;
	private int portNumber;

	public ConnectionGUI() {
		IPAddress = "localhost";
		portNumber = 33333;
		pane = new GridPane();
		joinText = new Label();
        joinTextValue = new SimpleStringProperty("Join the game");
        joinText.textProperty().bind(joinTextValue);
		buttonBox = new VBox();
		createServerButton = new Button("Create game server");
		connectToServerButton = new Button("Connect to 2P game");
		connectTo4PServerButton = new Button("Connect to 4P game");
		scene = new Scene(pane, 600, 800);
		scene.getStylesheets().add("Theme.css");
		IPandPortInfo = new Label("Enter the IP and port address for your machine.");
		IPAddressField = new TextField(IPAddress);
		portField = new TextField("" + portNumber);
	}

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Join Multiplayer");
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
        pane.setVgap(100);
        pane.add(buttonBox, 0, 1);
        joinText.setTextAlignment(TextAlignment.CENTER);
        joinText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
        pane.add(joinText, 0, 0, 1, 1);
    }

	public void setButtons() {
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(IPandPortInfo, IPAddressField, portField, createServerButton, connectToServerButton, connectTo4PServerButton);
        buttonBox.setAlignment(Pos.CENTER);
        createServerButton.setPrefWidth(270);
        createServerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	SystemLogger.init();
            	String mode = askForGameMode();
            	GameServer server;
            	if (mode.equals("2P Challenge")) {
            		server = new GameServer(new NetworkedGameController(new ChallengeBoard(2)), 2);
            	} else if (mode.equals("2P Standard")){
            		server = new GameServer(new NetworkedGameController(new StandardBoard(2)), 2);
            	} else if (mode.equals("4P Standard")) {
					server = new GameServer(new NetworkedGameController(new StandardBoard(4)), 4);
				} else {
					server = new GameServer(new NetworkedGameController(new ChallengeBoard(4)), 4);
				}
                IPAddress = IPAddressField.getText();
                portNumber = Integer.parseInt(portField.getText());
                server.initialiseServer(IPAddress, portNumber);
            }
        });
        connectToServerButton.setPrefWidth(270);
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
		connectTo4PServerButton.setPrefWidth(270);
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
						System.out.println(e.getMessage());
					}
					if (client.guiCanBeLaunched()) {
						client.setGUILaunched(true);
						((NetworkedBoardGUI) gui).setClient(client);
						gui.start(new Stage());
					}
				}
			}
		});
    }

	private String askForGameMode() {
		String mode = null;
		ArrayList<String> choices = new ArrayList();
		choices.add("2P Standard");
		choices.add("2P Challenge");
		choices.add("4P Standard");
		choices.add("4P Challenge");

		ChoiceDialog<String> dialog = new ChoiceDialog<>("2P Standard", choices);
		dialog.setTitle("Game modes");
		dialog.setHeaderText("Choose a game mode");
		dialog.setContentText("Choose a mode:");

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			mode = result.get();
		}
		return mode;
	}
}
