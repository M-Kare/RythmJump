package presentation.endview;

import Level.LevelController;
import javafx.scene.control.Button;
import presentation.LevelSelectView.LevelSelectView;

public class TheEndController {

	private TheEnd root;
	private Button home;
	private Button levelSelect;
	private Button repeat;
	private LevelSelectView levelSelectView;

	private LevelController currentLevelController;

	public TheEndController(LevelSelectView levelSelectView, LevelController currentLevelController) {
		root = new TheEnd();
		home = root.home;
		levelSelect = root.levelSelect;
		repeat = root.repeat;
		this.levelSelectView = levelSelectView;

		this.currentLevelController = currentLevelController;

		init();
	}

	public void init() {
		home.setOnMouseClicked(e -> {
			// Ayoub
			
		});
		levelSelect.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});

		repeat.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			LevelController repeatLevelController = new LevelController(currentLevelController.getRoot(), levelSelectView);
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
