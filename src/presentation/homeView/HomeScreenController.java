package presentation.homeView;

import presentation.levelSelectView.LevelSelectView;
import presentation.levelSelectView.LevelSelectViewController;
import presentation.playView.PlayViewController;
import presentation.settingsView.SettingsView;

import java.util.ArrayList;
import java.util.HashMap;

import business.Config;
import business.level.Level;
import business.level.LevelController;
import javafx.scene.control.Button;

/**
 * Controller für die HomeScreen-Klasse
 */
public class HomeScreenController {

	private HomeScreen root;
	private Button play;
	private Button levelSelect;
	private Button tutorial;
	private LevelSelectViewController levelSelectViewController;
	private LevelSelectView levelSelectView;

	private Button settingsButton;
	private SettingsView settingsView;
	private HashMap<String, Level> levelArray;

	/**
	 * Logik für die Buttons des HomeScreen-Menues
	 * 
	 * @param levelArray Liste der verfügbaren Level zum laden.
	 */
	public HomeScreenController(HashMap<String, Level> levelArray) {
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

	/**
	 * Setzt Listener und Handler für die Buttons
	 */
	public void init() {

		/**
		 * Sucht sich ein zufälliges Level aus der LevelListe und läd es
		 */
		play.setOnMouseClicked(e -> {
			ArrayList<Level> levelList = new ArrayList<>(levelArray.values());
			int randomIndex = Config.getRandomNumber(0, levelList.size() - 1);

			PlayViewController playViewController = new PlayViewController(levelSelectView, root,
					levelList.get(randomIndex));
			playViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(playViewController.getRoot());
			playViewController.getLevelController().getRoot().requestFocus();
		});

		levelSelect.setOnMouseClicked(e -> {
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});

		tutorial.setOnMouseClicked(e -> {
			Level tutorialLevel = levelArray.get("tutorial");

			PlayViewController playViewController = new PlayViewController(levelSelectView, root, tutorialLevel);
			playViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(playViewController.getRoot());
			playViewController.getLevelController().getRoot().requestFocus();
		});

		settingsButton.setOnMouseClicked(e -> {
			root.getChildren().add(settingsView);
		});

	}

	/**
	 * Getter für HomeScreen (Root)
	 * 
	 * @return root (HomesScreen)
	 */
	public HomeScreen getRoot() {
		return root;
	}
}
