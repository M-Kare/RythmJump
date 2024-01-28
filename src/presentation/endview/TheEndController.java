package presentation.endview;

import Level.Level;
import Level.LevelController;
import javafx.scene.control.Button;
import presentation.LevelSelectView.LevelSelectView;

public class TheEndController {

	private TheEnd root;
	private Button home;
	private Button levelSelect;
	private Button repeat;
	private LevelSelectView levelSelectView;

	private Level currentLevel;

	public TheEndController(LevelSelectView levelSelectView, Level currentLevel) {
		root = new TheEnd();
		home = root.home;
		levelSelect = root.levelSelect;
		repeat = root.repeat;
		this.levelSelectView = levelSelectView;

		this.currentLevel = currentLevel;

		init();
	}

	public void init() {
		home.setOnMouseClicked(e -> {
			// Ayoub
		});
		levelSelect.setOnMouseClicked(e -> {
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});

		repeat.setOnMouseClicked(e -> {
			LevelController repeatLevelController = new LevelController(currentLevel, levelSelectView);
			repeatLevelController.resetPlayer();
			root.getScene().setRoot(repeatLevelController.getRoot());
			repeatLevelController.getRoot().requestFocus();
		});
	}

	public TheEnd getRoot() {
		return root;
	}

}
