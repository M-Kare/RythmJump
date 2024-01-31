package presentation.playView;

import business.level.Level;
import business.level.LevelController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;

public class PlayView extends StackPane {
	protected LevelController levelController;
	protected Level level;
	
	protected Button backButton;

	public PlayView(Level level) {
		super();
		levelController = new LevelController(level, this);
		this.level = levelController.getRoot();
		levelController.resetPlayer();
		backButton = new Button("Back");
		this.getChildren().addAll(this.level, backButton);
		this.setAlignment(Pos.TOP_LEFT);
	}

}
