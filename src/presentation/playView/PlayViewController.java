package presentation.playView;

import business.level.Level;
import business.level.LevelController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import presentation.deathView.DeathViewController;
import presentation.endview.TheEnd;
import presentation.endview.TheEndController;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;

/**
 * Controller für die PlayView
 */
public class PlayViewController {
	private PlayView root;

	private LevelSelectView levelSelectView;
	private HomeScreen homeScreen;

	private TheEndController theEndController;
	private TheEnd theEndScreen;

	private LevelController levelController;
	private SimpleBooleanProperty paused;

	private Button backButton;

	private ImageView bgFrame;

	/**
	 * Startet die Musik und setzt den Spieler im Level
	 * 
	 * @param levelSelect
	 * @param homeScreen
	 * @param level
	 */
	public PlayViewController(LevelSelectView levelSelect, HomeScreen homeScreen, Level level) {
		super();
		root = new PlayView(level);
		this.levelSelectView = levelSelect;
		this.homeScreen = homeScreen;

		levelController = root.levelController;
		levelController.resetPlayer();
		levelController.playMusic();

		paused = levelController.getPaused();

		backButton = root.backButton;

		bgFrame = root.bgFrame;

		init();
	}

	/**
	 * Getter für die PlayView
	 * 
	 * @return PlayView
	 */
	public PlayView getRoot() {
		return root;
	}

	/**
	 * Getter für den aktuellen LevelController
	 * 
	 * @return LevelControllers
	 */
	public LevelController getLevelController() {
		return levelController;
	}

	/**
	 * Methode zum erstellen und anzeigen des Sieges- / EndScreens. Setzt die Stats
	 */
	public void showEnd() {
		theEndController = new TheEndController(levelSelectView, levelController, homeScreen);
		theEndScreen = theEndController.getRoot();

		theEndScreen.setBeats(levelController.getBeatCount());
		theEndScreen.setJumps(levelController.getJumpCount());
		theEndScreen.setMissedJumps(levelController.getMissedJumpCount());
		theEndScreen.setDeaths(levelController.getDeathCount());

		root.setLayoutX(0);
		root.setLayoutY(0);
		root.getChildren().add(theEndScreen);
	}

	/**
	 * Methode zum erzeugen und anzeigen des DeathScreens
	 */
	public void showDeathView() {
		DeathViewController dvc = new DeathViewController(this, levelSelectView);
		root.getChildren().add(dvc.getRoot());
		root.setLayoutX(0);
		root.setLayoutY(0);
		dvc.getRoot().requestFocus();
	}

	/**
	 * Fügt Handler und Listener für Buttons und Properties hinzu
	 */
	public void init() {
		/**
		 * Damit der Hintergrund sich mit dem Spieler / Layout bewegt
		 */
		bgFrame.translateXProperty().bind(root.layoutXProperty().multiply(-1));
		bgFrame.translateYProperty().bind(root.layoutYProperty().multiply(-1));

		/**
		 * Damit der Button sich mit dem Spieler / Layout bewegt
		 */
		backButton.translateXProperty().bind(root.layoutXProperty().multiply(-1));
		backButton.translateYProperty().bind(root.layoutYProperty().multiply(-1));

		/**
		 * Property-Listener, der zum LevelSelect navigiert, wenn paused gesetzt wird
		 */
		paused.addListener(e -> {
			if (paused.get()) {
				levelController.stopMusic();
				root.getScene().setRoot(levelSelectView);
				levelSelectView.requestFocus();
			}
		});

		/**
		 * Property-Listener, der die DeathView anzeigt
		 */
		levelController.getDieded().addListener(e -> {
			if (levelController.getDieded().get() && !levelController.getWon().get()) {
				showDeathView();
			}
		});

		/**
		 * Property-Listener, der den End- / SiegesScreen anzeigt
		 */
		levelController.getWon().addListener(e -> {
			if (levelController.getWon().get()) {
				showEnd();
			}
		});

		/**
		 * Navigiert zum LevelSelectView, wenn der BackButton gedrückt wird
		 */
		backButton.setOnMouseClicked(e -> {
			levelController.stopMusic();
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
	}
}
