import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SettingsGUI extends Application{

    private Scene scene;
    private GridPane settingsPane;
    private Text languageText;
    private Text colourText;
    private HBox languageBox;
    private HBox colourBox;
    private Button englishButton;
    private Button frenchButton;
    private Button spanishButton;
    private Button chineseButton;
    private Button standardButton;
    private Button protanopiaButton;
    private Button tritanopiaButton;
    private Button deuteranopiaButton;
    private MenuGUI menuGUI;

    public static String language = "English";
    public static String theme = "Theme.css";

    public SettingsGUI(MenuGUI menuGUI) {
        this.menuGUI = menuGUI;

        settingsPane = new GridPane();

        languageText = new Text("Language");
        languageBox = new HBox();
        languageText.setId("text");

        colourText = new Text("Colour");
        colourBox = new HBox();
        colourText.setId("text");

        englishButton = new Button("English");
        frenchButton = new Button("Français");
        spanishButton = new Button("Español");
        chineseButton = new Button("中文");

        standardButton = new Button("Standard");
        protanopiaButton = new Button("Protanopia");
        tritanopiaButton = new Button("Tritanopia");
        deuteranopiaButton = new Button("Deuteranopia");

        // Background
        Image background = new Image(getClass().getResourceAsStream("icons/backgrounds.png"));
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage bimg = new BackgroundImage(background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size);

        settingsPane.setBackground(new Background(bimg));
        scene = new Scene(settingsPane, 1000, 400);
        scene.getStylesheets().add(theme);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Settings");
        setButtons();
        setSettingsPane();
        stage.setScene(scene);
        stage.show();
    }

    public void setButtons() {
        languageBox.setPadding(new Insets(15, 15, 15, 15));
        languageBox.setSpacing(10);
        languageBox.getChildren().addAll(englishButton, frenchButton, spanishButton, chineseButton);
        languageBox.setAlignment(Pos.CENTER);

        englishButton.setPrefWidth(175);
        englishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "English";
                LanguageFileHandler.setLanguage(language);
                menuGUI.updateLanguage();
            };
        });

        frenchButton.setPrefWidth(175);
        frenchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "French";
                LanguageFileHandler.setLanguage(language);
                menuGUI.updateLanguage();
            };
        });

        spanishButton.setPrefWidth(175);
        spanishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "Spanish";
                LanguageFileHandler.setLanguage(language);
                menuGUI.updateLanguage();
            };
        });

        chineseButton.setPrefWidth(175);
        chineseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "Chinese";
                LanguageFileHandler.setLanguage(language);
                menuGUI.updateLanguage();
            };
        });

        colourBox.setPadding(new Insets(15, 15, 15, 15));
        colourBox.setSpacing(10);
        colourBox.getChildren().addAll(standardButton, protanopiaButton, tritanopiaButton, deuteranopiaButton);
        colourBox.setAlignment(Pos.CENTER);

        standardButton.setPrefWidth(175);
        standardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                theme = "Theme.css";
                updateTheme();
                menuGUI.updateTheme();
            };
        });

        protanopiaButton.setPrefWidth(175);
        protanopiaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                theme = "protanopia.css";
                updateTheme();
                menuGUI.updateTheme();
            };
        });

        tritanopiaButton.setPrefWidth(175);
        tritanopiaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                theme = "tritanopia.css";
                updateTheme();
                menuGUI.updateTheme();
            };
        });

        deuteranopiaButton.setPrefWidth(175);
        deuteranopiaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                theme = "deuteranopia.css";
                updateTheme();
                menuGUI.updateTheme();
            };
        });
    }

    public void setSettingsPane() {
        settingsPane.setAlignment(Pos.CENTER);
        settingsPane.setHgap(25);
        settingsPane.setVgap(10);

        settingsPane.add(languageBox, 0, 1);
        languageText.setTextAlignment(TextAlignment.CENTER);
        languageText.setFont(Font.font("Agency FB", FontWeight.BOLD, 50));
        settingsPane.add(languageText, 0, 0, 1, 1);

        settingsPane.add(colourBox, 0, 5);
        colourText.setTextAlignment(TextAlignment.CENTER);
        colourText.setFont(Font.font("Agency FB", FontWeight.BOLD, 50));
        settingsPane.add(colourText, 0, 0, 1, 13);
    }

    private void updateTheme() {
        // Remove the stylesheet currently in use
        scene.getStylesheets().remove(scene.getStylesheets().get(0));
        // Add the new stylesheet.
        scene.getStylesheets().add(theme);
    }
}
