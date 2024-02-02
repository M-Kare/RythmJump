package presentation.homeView;

import business.Config;
import business.player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import presentation.settingsView.SettingsView;
import presentation.settingsView.SettingsViewController;

/**
 * Hauptmenue und Startbildschirm der Applikation
 */
public class HomeScreen extends StackPane {
	protected AnchorPane root;

	protected Label title;

	protected VBox buttons;
	protected Button play;
	protected Button levelSelect;
	protected Button tutorial;
	protected Button settingButton;

	private HBox playerPreview;
	protected Player player;

	private SettingsViewController settingsController;
	protected SettingsView settingsView;

	/**
	 * Besteht aus Buttons, die die anderen Views verbinden und einem Settings-Menue
	 */
	public HomeScreen() {
		root = new AnchorPane();

		title = new Label("Main Menu");
		title.getStyleClass().add("menuTitle");
		title.setAlignment(Pos.CENTER);
		root.setTopAnchor(title, 80.00);
		root.setLeftAnchor(title, 50.00);
		root.setRightAnchor(title, 50.00);

		play = new Button("New Game");
		play.setId("playButton");
		levelSelect = new Button("Select Level");
		levelSelect.setId("levelSelectButton");
		tutorial = new Button("Tutorial");
		tutorial.setId("tutorialButton");
		settingButton = new Button("Settings");
		settingButton.setId("settingButton");
		buttons = new VBox(play, levelSelect, tutorial, settingButton);
		buttons.setAlignment(Pos.CENTER);
		buttons.setSpacing(40);
		root.setBottomAnchor(buttons, 200.00);
		root.setLeftAnchor(buttons, 50.00);
		root.setRightAnchor(buttons, 50.00);

		player = new Player();
		player.setScaleX(4);
		player.setScaleY(4);

		playerPreview = new HBox(player);
		playerPreview.setAlignment(Pos.BOTTOM_CENTER);
		root.setLeftAnchor(playerPreview, 300.00);
		root.setBottomAnchor(playerPreview, 300.00);
		root.setTopAnchor(playerPreview, 50.00);

		root.getChildren().addAll(title, buttons, playerPreview);

		this.getChildren().addAll(root);
		this.setAlignment(Pos.CENTER);
		this.setId("HomeView");

		settingsController = new SettingsViewController(this);
		settingsView = settingsController.getRoot();
		settingsView.setId("settings");
		
		this.getStylesheets().add(getClass().getResource("styleHomeScreen.css").toExternalForm());
	}
}
