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

import java.io.FileWriter;
import java.io.IOException;
import static javafx.application.Application.launch;

import javax.sound.midi.SysexMessage;

public class GameOverGUI extends Application {

    private Scene scene;
    private GridPane introPane;
    private Text introText;
    private VBox buttonBox;
    private Button quitButton;
    private Button newGameButton;
    private Controller controller;
    private Stage primaryStage;
    private int winnerID;
    private Text winnerName;
    private int numberOfPlayers;


    public GameOverGUI(Controller controller, int winnerID, int numberOfPlayers) {
    	this.controller = controller;
        this.winnerID = winnerID;
        this.numberOfPlayers = numberOfPlayers;
        introPane = new GridPane();
        introText = new Text("Game Over");
        buttonBox = new VBox();
        quitButton = new Button("Quit");
        newGameButton = new Button("New Game");
        System.out.println(winnerID);
        System.out.println(MenuGUI.player1Name.getText());
        System.out.println(MenuGUI.player2Name.getText());
        System.out.println(MenuGUI.player3Name.getText());
        System.out.println(MenuGUI.player4Name.getText());
        switch (winnerID) {
            case 1:
                winnerName = new Text("Winner: " + MenuGUI.player1Name.getText());
                break;
            case 2:
                winnerName = new Text("Winner: " + MenuGUI.player2Name.getText());
                break;
            case 3:
                winnerName = new Text("Winner: " + MenuGUI.player3Name.getText());
                break;
            case 4:
                winnerName = new Text("Winner: " + MenuGUI.player4Name.getText());
                break;
        }
        scene = new Scene(introPane, 600, 400);
        scene.getStylesheets().add(SettingsGUI.theme);
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
        buttonBox.getChildren().add(winnerName);
        buttonBox.getChildren().add(quitButton);
        buttonBox.getChildren().add(newGameButton);
        buttonBox.setAlignment(Pos.CENTER);

        quitButton.setPrefWidth(150);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });

        newGameButton.setPrefWidth(150);
        newGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	controller.resetGame();
            	primaryStage.close();
            };
        });
    }

    /**
     * Store the winner in a csv file
     */
    public void writeStatsToCSV() throws IOException{
        FileWriter fileWriter = new FileWriter("winners.csv", true);
        if (numberOfPlayers == 2) {
            // Write empty rows for players that did not player
            fileWriter.write(MenuGUI.player1Name.getText() + ", " + MenuGUI.player2Name.getText() + ", "
                    + ", " + ", " + winnerName.getText() + "\n");
        } else {
            fileWriter.write(MenuGUI.player1Name.getText() + ", " + MenuGUI.player2Name.getText() + ", "
                    + MenuGUI.player3Name.getText() + ", " + MenuGUI.player4Name.getText() + ", "
                    + winnerName.getText() + "\n");
        }
        fileWriter.close();
    }
}
