package presentation.endview;

import business.level.LevelController;
import javafx.scene.control.Button;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;
import presentation.playView.PlayViewController;

public class TheEndController {

	private TheEnd root;
	private Button home;
	private Button levelSelect;
	private Button repeat;
	private LevelSelectView levelSelectView;
	private HomeScreen homeScreen;
	private LevelController currentLevelController;

	public TheEndController(LevelSelectView levelSelectView, LevelController currentLevelController, HomeScreen homeScreen) {
		root = new TheEnd();
		home = root.home;
		levelSelect = root.levelSelect;
		repeat = root.repeat;
		this.levelSelectView = levelSelectView;
		this.homeScreen = homeScreen;

		this.currentLevelController = currentLevelController;

		init();
	}

	public void init() {
		home.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			root.getScene().setRoot(homeScreen);
			homeScreen.requestFocus();
			
		});
		levelSelect.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});

		repeat.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			PlayViewController repeatPlayViewController = new PlayViewController(levelSelectView, homeScreen, currentLevelController.getRoot());
			repeatPlayViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(repeatPlayViewController.getRoot());
			repeatPlayViewController.getLevelController().getRoot().requestFocus();
		});
	}

	public TheEnd getRoot() {
		return root;
	}

}
