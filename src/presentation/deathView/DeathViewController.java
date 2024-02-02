package presentation.deathView;

import javafx.scene.control.Button;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;
import presentation.playView.PlayViewController;

/**
 * Controller für die DeathView
 */
public class DeathViewController {
	
	private DeathView root;
	private Button home;
	private Button repeat;
	private Button levelSelect;
	private HomeScreen homeScreen;
	private LevelSelectView levelSelectView;
	private PlayViewController currentPlayViewController;

	/**
	 * Buttons und Views zur Navigation
	 * 
	 * @param playViewController Root zum hinzufügen der View
	 * @param levelSelectView    zum Navigieren
	 */
	public DeathViewController(PlayViewController playViewController, LevelSelectView levelSelectView,
			HomeScreen homescreen) {
		root = new DeathView();
		home = root.home;
		repeat = root.repeat;
		levelSelect = root.levelSelect;
		this.levelSelectView = levelSelectView;
		this.homeScreen = homescreen;
		currentPlayViewController = playViewController;
		

		currentPlayViewController.getLevelController().resetKeys();
		init();
	}

	/**
	 * Getter für die DeathView
	 * 
	 * @return DeathView
	 */
	public DeathView getRoot() {
		return root;
	}

	/**
	 * Fügt MouseClick-Event zu den Buttons hinzu
	 */
	public void init() {
		
		/**
		 * Navigiert zum HomeScreen
		 */
		home.setOnMouseClicked(e -> {
			currentPlayViewController.getLevelController().stopMusic();
			currentPlayViewController.getRoot().getScene().setRoot(homeScreen);
			levelSelectView.requestFocus();
		});
		
		/**
		 * Respawnt den Spieler und lässt das Level weiter laufen
		 */
		repeat.setOnMouseClicked(e -> {
			currentPlayViewController.getLevelController().resetPlayer();
			currentPlayViewController.getRoot().getChildren().remove(root);
			currentPlayViewController.getLevelController().getRoot().requestFocus();
		});

		/**
		 * Respawnt den Spieler und lässt das Level weiter laufen
		 */
		root.setOnKeyPressed(e -> {
			currentPlayViewController.getLevelController().resetPlayer();
			currentPlayViewController.getRoot().getChildren().remove(root);
			currentPlayViewController.getLevelController().getRoot().requestFocus();
		});

		/**
		 * Navigiert zum LevelSelect
		 */
		levelSelect.setOnMouseClicked(e -> {
			currentPlayViewController.getLevelController().stopMusic();
			currentPlayViewController.getRoot().getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
	}
}
