package presentation.endview;

import Level.LevelController;
import javafx.scene.control.Button;
import presentation.LevelSelectView.LevelSelectView;
import presentation.homeView.HomeScreen;

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
			LevelController repeatLevelController = new LevelController(currentLevelController.getRoot(), levelSelectView, homeScreen);
			repeatLevelController.resetPlayer();
			root.getScene().setRoot(repeatLevelController.getRoot());
			repeatLevelController.getRoot().requestFocus();
			repeatLevelController.playMusic();
		});
	}

	public TheEnd getRoot() {
		return root;
	}

}
