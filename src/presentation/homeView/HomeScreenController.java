package presentation.homeView;

import presentation.levelSelectView.LevelSelectView;
import presentation.levelSelectView.LevelSelectViewController;
import presentation.playView.PlayViewController;
import presentation.settingsView.SettingsView;

import java.util.ArrayList;
import java.util.HashMap;

import business.level.Level;
import business.level.LevelController;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

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

		play.setOnMouseClicked(e -> {
			// TODO select first level or random level
		});
		
        play.setOnMouseEntered(e -> play.setGraphic(root.playImgView_hover));
        play.setOnMouseExited(e -> play.setGraphic(root.playImgView));

		levelSelect.setOnMouseClicked(e -> {
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
		
		levelSelect.setOnMouseEntered(e -> levelSelect.setGraphic(root.selectImgView_hover));
        levelSelect.setOnMouseExited(e -> levelSelect.setGraphic(root.selectImgView));

		tutorial.setOnMouseClicked(e -> {
			Level tutorialLevel = levelArray.get("tutorial");

			PlayViewController playViewController = new PlayViewController(levelSelectView, root, tutorialLevel);
			playViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(playViewController.getRoot());
			playViewController.getLevelController().getRoot().requestFocus();
		});
		
		tutorial.setOnMouseEntered(e -> tutorial.setGraphic(root.tutorialImgView_hover));
	    tutorial.setOnMouseExited(e -> tutorial.setGraphic(root.tutorialImgView));

		settingsButton.setOnMouseClicked(e -> {
			root.getChildren().add(settingsView);
		});
		
		settingsButton.setOnMouseEntered(e -> settingsButton.setGraphic(root.settingsImgView_hover));
	    settingsButton.setOnMouseExited(e -> settingsButton.setGraphic(root.settingsImgView));
	    
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
