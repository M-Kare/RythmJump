package presentation.deathView;

import Level.LevelController;
import javafx.scene.control.Button;
import presentation.LevelSelectView.LevelSelectView;

public class DeathViewController {
	private Button repeat;
	private Button levelSelectButton;
	
	private LevelSelectView levelSelectView;
	private LevelController currentLevelController;
	
	private DeathView root;
	
	public DeathViewController(LevelController levelController, LevelSelectView levelSelectView) {
		root = new DeathView();
		
		this.levelSelectView = levelSelectView;
		currentLevelController = levelController;
		
		repeat = root.repeat;
		levelSelectButton = root.levelSelectButton;
		
		init();
	}
	
	
	public DeathView getRoot() {
		return root;
	}
	
	public void init() {
		repeat.setOnMouseClicked(e -> {
			currentLevelController.resetPlayer();
			root.getScene().setRoot(currentLevelController.getRoot());
			currentLevelController.getRoot().requestFocus();
		});
		
		levelSelectButton.setOnMouseClicked(e -> {
			currentLevelController.stopMusic();
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
		
		root.setOnKeyPressed(e -> {
			currentLevelController.resetPlayer();
			root.getScene().setRoot(currentLevelController.getRoot());
			currentLevelController.getRoot().requestFocus();
		});
	}
	
}
