package presentation.deathView;

import javafx.scene.control.Button;
import presentation.levelSelectView.LevelSelectView;
import presentation.playView.PlayViewController;

/**
 * Controller für die DeathView
 */
public class DeathViewController {
	private Button repeat;
	private Button levelSelectButton;

	private LevelSelectView levelSelectView;
	private PlayViewController currentPlayViewController;

	private DeathView root;

	/**
	 * Buttons und Views zur Navigation
	 * 
	 * @param playViewController Root zum hinzufügen der View
	 * @param levelSelectView    zum Navigieren
	 */
	public DeathViewController(PlayViewController playViewController, LevelSelectView levelSelectView) {
		root = new DeathView();

		this.levelSelectView = levelSelectView;
		currentPlayViewController = playViewController;

		repeat = root.repeat;
		levelSelectButton = root.levelSelectButton;

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
		levelSelectButton.setOnMouseClicked(e -> {
			currentPlayViewController.getLevelController().stopMusic();
			currentPlayViewController.getRoot().getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
	}
}
