package presentation.playView;

import java.net.MalformedURLException;

import business.Config;
import business.level.Level;
import business.level.LevelController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;

public class PlayView extends StackPane {
	protected LevelController levelController;
	protected Level level;
	protected ImageView bgFrame;
	protected Image background;
	protected Button backButton;

	public PlayView(Level level) {
		super();
		levelController = new LevelController(level, this);
		this.level = levelController.getRoot();
		levelController.resetPlayer();
		backButton = new Button("Back");
		backButton.setFocusTraversable(false);
		
		String bgPath = null;
		if(this.level.getBackgroundPath() == null) {
			bgPath = Config.STD_BACKGROUND;
		} else {
			bgPath = this.level.getBackgroundPath();
		}
		try {
				background = new Image(Config.findFile(bgPath, Config.BACKGROUNDS_FOLDER)
						.toURI().toURL().toExternalForm());				
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		bgFrame = new ImageView(background);
		this.getChildren().addAll(bgFrame, this.level, backButton);
		this.setAlignment(Pos.TOP_LEFT);
	}

}
