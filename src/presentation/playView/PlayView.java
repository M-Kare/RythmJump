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
import javafx.stage.Stage;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;

/**
 * Klasse die Level und Overlays enthält, falls nötig. View während des Spielens
 */
public class PlayView extends StackPane {
	protected LevelController levelController;
	protected Level level;
	protected ImageView bgFrame;
	protected Image background;

	/**
	 * Lädt das Level und fügt Hintergrund und Buttons hinzu
	 * 
	 * @param level
	 */
	public PlayView(Level level, Stage stage) {
		super();
		levelController = new LevelController(level, this, stage);
		this.level = levelController.getRoot();
		levelController.resetPlayer();
		String bgPath = null;
		if (this.level.getBackgroundPath() == null) {
			bgPath = Config.STD_BACKGROUND;
		} else {
			bgPath = this.level.getBackgroundPath();
		}
		try {
			background = new Image(Config.findFile(bgPath, Config.BACKGROUNDS_FOLDER).toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		bgFrame = new ImageView(background);
		bgFrame.setFitHeight(stage.getHeight());
		bgFrame.setFitWidth(stage.getWidth());
		bgFrame.preserveRatioProperty();
		this.getChildren().addAll(bgFrame, this.level);
		this.setAlignment(Pos.TOP_LEFT);
	}

}
