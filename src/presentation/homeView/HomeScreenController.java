package presentation.homeView;

import presentation.LevelSelectView.LevelSelectView;
import presentation.LevelSelectView.LevelSelectViewController;
import presentation.settingsView.SettingsView;
import presentation.settingsView.SettingsViewController;

import java.util.ArrayList;

import Level.Level;
import Level.LevelController;
import javafx.scene.control.Button;

public class HomeScreenController {

	private HomeScreen root;
	private Button play;
	private Button levelSelect;
	private Button tutorial;
	private LevelSelectViewController levelSelectViewController;
	private LevelSelectView levelSelectView;
	
	private Button settingsButton;
	private SettingsViewController settingsController;
	private SettingsView settingsView;
	private ArrayList<Level> levelArray;

	public HomeScreenController(ArrayList<Level> levelArray) {
		root = new HomeScreen();
		play = root.play;
		levelSelect = root.levelSelect;
		tutorial = root.tutorial;
		settingsButton = root.settingButton;
		
		this.levelArray = levelArray;

		levelSelectViewController = new LevelSelectViewController(levelArray, root);
		levelSelectView = levelSelectViewController.getRoot();
		
		settingsView = root.settingsView;
		
		init();
	}

	public void init() {

		play.setOnMouseClicked(e -> {
			// TODO select first level or random level
		});

		levelSelect.setOnMouseClicked(e -> {
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});

		tutorial.setOnMouseClicked(e -> {
				Level selectedLevel = null;
				for (Level level : this.levelArray) {
					if (level.getLevelName().contains("tutorial")) {
						selectedLevel = level;
						break;
					}
				}
				LevelController selectedLevelController = new LevelController(selectedLevel, levelSelectView, root);

				selectedLevelController.resetPlayer();
				root.getScene().setRoot(selectedLevelController.getRoot());
				selectedLevelController.getRoot().requestFocus();

				selectedLevelController.playMusic();
		});
		
		settingsButton.setOnMouseClicked(e-> {
			root.getChildren().add(settingsView);
		});

	}

	public HomeScreen getRoot() {
		return root;
	}
}
