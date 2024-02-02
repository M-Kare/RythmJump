package presentation.endview;

import business.level.LevelController;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;
import presentation.playView.PlayViewController;

/**
 * Controller für den End-View
 */
public class TheEndController {
	private Stage stage;
	private TheEnd root;
	private Button home;
	private Button repeat;
	private Button levelSelect;
	private LevelSelectView levelSelectView;
	private HomeScreen homeScreen;
	private LevelController currentLevelController;

	/**
	 * Menues zum Navigieren und Buttons
	 * 
	 * @param levelSelectView        zum Navigieren
	 * @param currentLevelController zum Wiederholen des Levels (wird neu erzeugt)
	 * @param homeScreen             zum Navigieren
	 */
	public TheEndController(LevelSelectView levelSelectView, LevelController currentLevelController,
			HomeScreen homeScreen, Stage stage) {
		this.stage = stage;
		root = new TheEnd();
		home = root.home;
		repeat = root.repeat;
		levelSelect = root.levelSelect;
		this.levelSelectView = levelSelectView;
		this.homeScreen = homeScreen;

		this.currentLevelController = currentLevelController;

		init();
	}

	/**
	 * OnMouseClick-Events für die Buttons
	 */
	public void init() {
		/**
		 * Navigiert zum HomeScreen
		 */
		home.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			root.getScene().setRoot(homeScreen);
			homeScreen.requestFocus();

		});

		/**
		 * Navigiert zum LevelSelect
		 */
		levelSelect.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});

		/**
		 * Startet das Level neu (neu erzeugt)
		 */
		repeat.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			PlayViewController repeatPlayViewController = new PlayViewController(levelSelectView, homeScreen,
					currentLevelController.getRoot(), stage);
			repeatPlayViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(repeatPlayViewController.getRoot());
			repeatPlayViewController.getLevelController().getRoot().requestFocus();
		});
	}

	/**
	 * Getter für End-View
	 * 
	 * @return End-View
	 */
	public TheEnd getRoot() {
		return root;
	}

}
