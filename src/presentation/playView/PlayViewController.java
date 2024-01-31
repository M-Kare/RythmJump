package presentation.playView;

import business.Config;
import business.level.Level;
import business.level.LevelController;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import presentation.deathView.DeathViewController;
import presentation.endview.TheEnd;
import presentation.endview.TheEndController;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;

public class PlayViewController {
	private PlayView root;

	private LevelSelectView levelSelectView;
	private HomeScreen homeScreen;

	private TheEndController theEndController;
	private TheEnd theEndScreen;

	private LevelController levelController;

	private Button backButton;
	
	private ImageView bgFrame;

	public PlayViewController(LevelSelectView levelSelect, HomeScreen homeScreen, Level level) {
		super();
		root = new PlayView(level);
		this.levelSelectView = levelSelect;
		this.homeScreen = homeScreen;

		levelController = root.levelController;
		levelController.resetPlayer();
		levelController.playMusic();

		backButton = root.backButton;
		
		bgFrame = root.bgFrame;
		
		init();
	}

	public PlayView getRoot() {
		return root;
	}
	
	public LevelController getLevelController() {
		return levelController;
	}

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

	public void showDeathView() {
		DeathViewController dvc = new DeathViewController(this, levelSelectView);
		root.getChildren().add(dvc.getRoot());
		root.setLayoutX(0);
		root.setLayoutY(0);
		dvc.getRoot().requestFocus();
	}

	public void init() {
		
		bgFrame.translateXProperty().bind(root.layoutXProperty().multiply(-1));
		bgFrame.translateYProperty().bind(root.layoutYProperty().multiply(-1));
		backButton.translateXProperty().bind(root.layoutXProperty().multiply(-1));
		backButton.translateYProperty().bind(root.layoutYProperty().multiply(-1));
		
		levelController.getDieded().addListener(e -> {
			if (levelController.getDieded().get() && !levelController.getWon().get()) {
				showDeathView();
			}
		});

		levelController.getWon().addListener(e -> {
			if (levelController.getWon().get()) {
				showEnd();
			}
		});

		backButton.setOnMouseClicked(e -> {
			levelController.stopMusic();
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
	}
}
