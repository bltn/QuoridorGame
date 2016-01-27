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

import javax.sound.midi.SysexMessage;

/**
 * @author Junaid Rasheed
 */
public class MenuGUI extends Application {

    private Scene scene;
    private GridPane introPane;
    private Text introText;
    private VBox buttonBox;
    private Button startButton;
    private Button quitButton;

    public MenuGUI() {
        introPane = new GridPane();
        introText = new Text("Quoridor");
        buttonBox = new VBox();
        startButton = new Button("Start");
        quitButton = new Button("Quit");
        scene = new Scene(introPane, 600, 400);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");
        setButtons();
        setIntroPane();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void setIntroPane() {
        introPane.setAlignment(Pos.CENTER);
        introPane.setHgap(25);
        introPane.setVgap(100);
        introPane.add(buttonBox, 0, 1);
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
        introPane.add(introText, 0, 0, 1, 1);
    }

    public void setButtons() {
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.getChildren().add(startButton);
        buttonBox.getChildren().add(quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        startButton.setPrefWidth(150);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new GameController(new BoardGUI(), new Board());
            };
        });
        quitButton.setPrefWidth(150);
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
