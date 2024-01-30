package presentation.homeView;

import Application.Config;
import Player.Player;
import javafx.geometry.*;
import javafx.scene.layout.*;
import presentation.settingsView.SettingsView;
import presentation.settingsView.SettingsViewController;
import javafx.scene.control.*;

public class HomeScreen extends StackPane{
	
	protected Label header;
	protected VBox root;
	protected VBox buttons;
	protected HBox playerPreview;
	protected Button play;
	protected Button levelSelect;
	protected Button tutorial;
	protected Player player;
	
	protected Button settingButton;
	
	private SettingsViewController settingsController;
	protected SettingsView settingsView;
	
	public HomeScreen() {
		settingButton = new Button("Settings");
		
		header = new Label("Main Menu");
		header.getStyleClass().add("menuTitle");
		header.setAlignment(Pos.TOP_CENTER);
		
		play = new Button("New Game");
		play.setId("playButton");
		
		levelSelect = new Button("Select Level");
		levelSelect.setId("levelSelectButton");
		tutorial = new Button("Tutorial");
		tutorial.setId("tutorialButton");
		
		player = new Player();
		player.setHeight(player.getHeight()*5);
		player.setWidth(player.getWidth()*5);
		
		buttons = new VBox(play, levelSelect, tutorial, settingButton);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		playerPreview = new HBox(player);
		playerPreview.setAlignment(Pos.CENTER_LEFT);
		
		root = new VBox(header, playerPreview, buttons);
		root.setAlignment(Pos.CENTER);
		root.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		
		this.getChildren().addAll(root);
		this.setAlignment(Pos.CENTER);
		this.setId("HomeView");
		
		settingsController = new SettingsViewController(this);
		settingsView = settingsController.getRoot();
	}
	
	public SettingsViewController getSettingsController() {
		return settingsController;
	}
}
