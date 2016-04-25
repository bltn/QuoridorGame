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


    public GameOverGUI(Controller controller) {
    	this.controller = controller;
        introPane = new GridPane();
        introText = new Text(Translate.gameOver());
        buttonBox = new VBox();
        quitButton = new Button(Translate.quit());
        newGameButton = new Button(Translate.newGame());
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
}
