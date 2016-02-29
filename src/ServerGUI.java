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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.net.InetAddress;

public class ServerGUI extends Application {
    private Scene scene;
    private GridPane serverPane;
    private Text serverText;
    private VBox buttonBox;
    private Button connectButton;
    private Label IPandPortInfo;
    private TextField IP;
    private TextField port;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String IPAddress = "localhost";
    private int portAddress = 33333;
    private boolean accepted;

    public ServerGUI() {
        serverPane = new GridPane();
        serverText = new Text("Join Multiplayer");
        buttonBox = new VBox();
        connectButton = new Button("Connect");
        scene = new Scene(serverPane, 600, 800);
        scene.getStylesheets().add("Theme.css");
        //set up a textfield and a button for user to enter their ip and port detail
        IPandPortInfo = new Label("Please Enter the port and IP you want to connect?");
        IP = new TextField("Enter IP");
        port = new TextField("Enter Port");
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
        buttonBox.getChildren().addAll(IPandPortInfo, IP, port, connectButton);
        buttonBox.setAlignment(Pos.CENTER);
        connectButton.setPrefWidth(150);
        connectButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setupIPandPort();
            }
        });
    }

    private void setTextFields() {
        IP.setLayoutX(50);
        IP.setLayoutY(50);
        IPandPortInfo.setLayoutX(50);
        IPandPortInfo.setLayoutY(IP.getLayoutY() - 20);
        port.setLayoutX(IP.getLayoutX());
        port.setLayoutY(IP.getLayoutY() + 30);
    }

    /**
     * setting up the server
     * Include add ip and port text field on the right hand side for people to fill in
     */
    private void setupIPandPort(){
        String tempIP = IP.getText();
        String tempPort = port.getText();
        //check if text field is a integer
        // then convert from String to integer.
        if (tempIP.matches("[0-9]*") && tempPort.matches("[0-9]*")) {
            IPAddress = tempIP;
            portAddress = Integer.parseInt(tempPort);
            if(portAddress > 0 && portAddress < 65535){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("IP and Port successfully saved!");
                alert.showAndWait();
                //Check if server already exist.
                if(!connect()){
                    initializeServer();
                }
            }}
    }
    private boolean connect(){
        try {
            socket = new Socket(IPAddress,portAddress);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            accepted = true;
        } catch (Exception e) {
            System.out.println("Unable to connect to the address: " + IPAddress + ":" + portAddress + " | Starting a server");
            return false;
        }
        System.out.println("Successfully connected to the server.");
        return true;
    }
    private void initializeServer(){
    	try {
			serverSocket = new ServerSocket(portAddress, 8, InetAddress.getByName(IPAddress));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			Alert a = new Alert(AlertType.INFORMATION);
			a.setTitle("Information");
			a.setContentText("Client has joined the server");
			accepted = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
