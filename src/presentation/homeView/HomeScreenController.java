package presentation.homeView;

import presentation.LevelSelectView.LevelSelectView;
import javafx.scene.control.Button;

public class HomeScreenController {

	private HomeScreen root;
	private Button play;
	private Button levelSelect;
	private Button tutorial;
	private LevelSelectView levelSelectView;
	
	public HomeScreenController(LevelSelectView levelSelectView) {
		root = new HomeScreen();
		play = root.play;
		levelSelect = root.levelSelect;
		tutorial = root.tutorial;
		this.levelSelectView = levelSelectView;

		init();
	}
	
	public void init() {
		
		play.setOnMouseClicked(e -> {
			//TODO select first level or random level	
		});
		
		levelSelect.setOnMouseClicked(e -> {
			root.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
		});
		
		tutorial.setOnMouseClicked(e -> {
			//TODO show popup with game explanation and control explanation
		});

	}
	
	public HomeScreen getRoot() {
		return root;
	}
}
