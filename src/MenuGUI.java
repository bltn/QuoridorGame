import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuGUI extends Application {

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Quoridor");

        // Pane for the intro text                                      
        GridPane introPane = new GridPane();
        introPane.setAlignment(Pos.CENTER);
        introPane.setHgap(25);
        introPane.setVgap(100);

        //set up the intro window                                 
        Text introText = new Text("Quoridor");
        introText.setTextAlignment(TextAlignment.CENTER);
        introText.setFont(Font.font("Calibri", FontWeight.BOLD, 50));
        introPane.add(introText, 0, 0, 1, 1);

        //set up some vertical buttons
        VBox buttonBox = new VBox();
        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);

        Button StartButton = new Button("Start");
        StartButton.setPrefWidth(150);
        Button quitButton = new Button("Quit");
        quitButton.setPrefWidth(150);
        buttonBox.getChildren().add(StartButton);
        buttonBox.getChildren().add(quitButton);
        buttonBox.setAlignment(Pos.CENTER);
        introPane.add(buttonBox, 0, 1);

        Scene scene = new Scene(introPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
