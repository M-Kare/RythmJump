package presentation.PlayView;

import Level.Level;
import Level.LevelController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import presentation.LevelSelectView.LevelSelectView;
import presentation.homeView.HomeScreen;

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
