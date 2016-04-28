import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private VBox buttonBox;
    private HBox doneBox;
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
    private Button doneButton;
    private MenuGUI menuGUI;
    private Stage stage;

    public static String language = "English";
    public static String theme = "Theme.css";

    public SettingsGUI(MenuGUI menuGUI) {
        this.menuGUI = menuGUI;
        settingsPane = new GridPane();
        buttonBox = new VBox();
        languageText = new Text(Translate.languages());
        languageBox = new HBox();
        languageText.setId("text");
        doneBox = new HBox();

        colourText = new Text(Translate.colour());
        colourBox = new HBox();
        colourText.setId("text");

        // add a icon into the English button
    	Image english = new Image(getClass().getResourceAsStream("icons/gbr.png"));
    	ImageView newEnglishButton = new ImageView(english);
    	newEnglishButton.setFitHeight(20);
    	newEnglishButton.setFitWidth(20);
    	englishButton = new Button("English",newEnglishButton);
    	
        // add a icon into the French button
    	Image french = new Image(getClass().getResourceAsStream("icons/fr.png"));
    	ImageView newFrenchButton = new ImageView(french);
    	newFrenchButton.setFitHeight(20);
    	newFrenchButton.setFitWidth(20);
    	frenchButton = new Button("Français",newFrenchButton);
    	
        // add a icon into the Spanish button
    	Image spanish = new Image(getClass().getResourceAsStream("icons/sp.png"));
    	ImageView newSpanishButton = new ImageView(spanish);
    	newSpanishButton.setFitHeight(20);
    	newSpanishButton.setFitWidth(20);
    	spanishButton = new Button("Español",newSpanishButton);
    	
        // add a icon into the Chinese button
    	Image chinese = new Image(getClass().getResourceAsStream("icons/cn.png"));
    	ImageView newChineseButton = new ImageView(chinese);
    	newChineseButton.setFitHeight(20);
    	newChineseButton.setFitWidth(20);
    	chineseButton = new Button("中文",newChineseButton);

        standardButton = new Button("Standard");
        protanopiaButton = new Button("Protanopia");
        tritanopiaButton = new Button("Tritanopia");
        deuteranopiaButton = new Button("Deuteranopia");

        // add a icon into the done button
    	Image done = new Image(getClass().getResourceAsStream("icons/done.png"));
    	ImageView newDoneButton = new ImageView(done);
    	newDoneButton.setFitHeight(20);
    	newDoneButton.setFitWidth(20);
    	doneButton = new Button(Translate.done(),newDoneButton);

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
        this.stage = stage;
        stage.setTitle("Settings");
        setButtons();
        setSettingsPane();
        stage.setScene(scene);
        stage.show();
    }

    public void setButtons() {
        languageText.setTextAlignment(TextAlignment.CENTER);
        languageText.setFont(Font.font("Agency FB", FontWeight.BOLD, 50));

        colourText.setTextAlignment(TextAlignment.CENTER);
        colourText.setFont(Font.font("Agency FB", FontWeight.BOLD, 50));

        buttonBox.setPadding(new Insets(15, 15, 15, 15));
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(languageText, languageBox, colourText, colourBox, doneBox);

        languageBox.setPadding(new Insets(15, 15, 15, 15));
        languageBox.setSpacing(10);
        languageBox.getChildren().addAll(englishButton, frenchButton, spanishButton, chineseButton);
        languageBox.setAlignment(Pos.CENTER);

        doneBox.getChildren().addAll(doneButton);
        doneBox.setAlignment(Pos.CENTER);


        englishButton.setPrefWidth(175);
        englishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "English";
                Translate.setLanguage(language);
                updateLanguage();
                menuGUI.updateLanguage();
            };
        });

        frenchButton.setPrefWidth(175);
        frenchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "French";
                Translate.setLanguage(language);
                updateLanguage();
                menuGUI.updateLanguage();
            };
        });

        spanishButton.setPrefWidth(175);
        spanishButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "Spanish";
                Translate.setLanguage(language);
                updateLanguage();
                menuGUI.updateLanguage();
            };
        });

        chineseButton.setPrefWidth(175);
        chineseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                language = "Chinese";
                Translate.setLanguage(language);
                updateLanguage();
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

        doneButton.setPrefWidth(175);
        doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            };
        });
    }


    
    public void setSettingsPane() {
        settingsPane.setAlignment(Pos.CENTER);
        settingsPane.setHgap(25);
        settingsPane.setVgap(10);
        settingsPane.add(buttonBox, 0, 1);

        settingsPane.add(languageBox, 0, 1);
        languageText.setTextAlignment(TextAlignment.CENTER);
        languageText.setFont(Font.font("Agency FB", FontWeight.BOLD, 50));
        settingsPane.add(languageText, 0, 0, 1, 1);

        settingsPane.add(colourBox, 0, 5);
        colourText.setTextAlignment(TextAlignment.CENTER);
        colourText.setFont(Font.font("Agency FB", FontWeight.BOLD, 50));
        settingsPane.add(colourText, 0, 0, 1, 11);

        settingsPane.add(doneBox, 0, 10);
    }

    private void updateTheme() {
        // Remove the stylesheet currently in use
        scene.getStylesheets().remove(scene.getStylesheets().get(0));
        // Add the new stylesheet.
        scene.getStylesheets().add(theme);
    }

    private void updateLanguage() {
        languageText.setText(Translate.languages());
        colourText.setText(Translate.colour());
        doneButton.setText(Translate.done());
    }
}
