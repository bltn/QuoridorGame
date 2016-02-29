/**
 * Created by Junaid on 29/02/2016.
 */
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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ServerGUI extends Application {
    private Scene scene;
    private GridPane serverPane;
    private Text serverText;
    private VBox buttonBox;
    private Button createButton;
    private Button connectButton;
    private Label IPandPortInfo;
    private TextField IPTextField;
    private TextField portTextField;
    private String IPAddress = "localhost";
    private int portAddress = 33333;
    private static GameController controller;

    public ServerGUI() {
        try {
            controller = new GameController(new BoardGUI(), new Board(), new Server());
        } catch (IOException e) {
            System.out.println("constructing error for GameController in ServerGUI");
        }
        serverPane = new GridPane();
        serverText = new Text("Join Multiplayer");
        buttonBox = new VBox();
        createButton = new Button("Create");
        connectButton = new Button("Connect");
        scene = new Scene(serverPane, 600, 800);
        scene.getStylesheets().add("Theme.css");
        //set up a textfield and a button for user to enter their ip and port detail
        IPandPortInfo = new Label("Please Enter the port and IP you want to connect?");
        IPTextField = new TextField(IPAddress);
        portTextField = new TextField("" + portAddress);
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

    /**
     * create the pane for the title
     */
    public void setServerPane() {
        serverPane.setAlignment(Pos.CENTER);
        serverPane.setHgap(25);
        serverPane.setVgap(100);
        serverPane.add(buttonBox, 0, 1);
        serverText.setTextAlignment(TextAlignment.CENTER);
        serverText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
        serverPane.add(serverText, 0, 0, 1, 1);
    }

    /**
     * Create the buttons for the menu and set their properties
     */
    public void setButtons() {
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().addAll(IPandPortInfo, IPTextField, portTextField, createButton, connectButton);
        buttonBox.setAlignment(Pos.CENTER);
        createButton.setPrefWidth(150);
        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IPAddress = IPTextField.getText();
                portAddress = Integer.parseInt(portTextField.getText());
                controller.initializeServer(IPAddress, portAddress);
            }
        });
        connectButton.setPrefWidth(150);
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                IPAddress = IPTextField.getText();
                portAddress = Integer.parseInt(portTextField.getText());
                controller.connectToServer(IPAddress, portAddress);
            }
        });
    }

    public void setTextFields() {
        IPTextField.setLayoutX(50);
        IPTextField.setLayoutY(50);
        IPandPortInfo.setLayoutX(50);
        IPandPortInfo.setLayoutY(IPTextField.getLayoutY() - 20);
        portTextField.setLayoutX(IPTextField.getLayoutX());
        portTextField.setLayoutY(IPTextField.getLayoutY() + 30);
    }
}
