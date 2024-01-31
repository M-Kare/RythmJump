package presentation.deathView;

import Level.LevelController;
import javafx.scene.control.Button;
import presentation.LevelSelectView.LevelSelectView;
import presentation.PlayView.PlayViewController;

public class DeathViewController {
	private Button repeat;
	private Button levelSelectButton;
	
	private LevelSelectView levelSelectView;
	private PlayViewController currentPlayViewController;
	
	private DeathView root;
	
	public DeathViewController(PlayViewController playViewController, LevelSelectView levelSelectView) {
		root = new DeathView();
		
		this.levelSelectView = levelSelectView;
		currentPlayViewController = playViewController;
		
		repeat = root.repeat;
		levelSelectButton = root.levelSelectButton;
		
		init();
	}
	
	
	public DeathView getRoot() {
		return root;
	}
	
	public void init() {
		repeat.setOnMouseClicked(e -> {
			currentPlayViewController.getLevelController().resetPlayer();
			currentPlayViewController.getLevelController().resetKeys();
			currentPlayViewController.getRoot().getChildren().remove(root);
			currentPlayViewController.getLevelController().getRoot().requestFocus();
		});
		
		levelSelectButton.setOnMouseClicked(e -> {
			currentPlayViewController.getLevelController().stopMusic();
			currentPlayViewController.getRoot().getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
		
		root.setOnKeyPressed(e -> {
			currentPlayViewController.getLevelController().resetPlayer();
			currentPlayViewController.getLevelController().resetKeys();
			currentPlayViewController.getRoot().getChildren().remove(root);
			currentPlayViewController.getLevelController().getRoot().requestFocus();
		});
	}
	
}
