package presentation.LevelSelectView;

import java.util.ArrayList;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import Player.PlayerController;
import javafx.scene.control.Button;
import presentation.betterSelector.LevelTilePaneController;
import presentation.betterSelector.TileNode;

public class LevelSelectViewController {
	private LevelSelectView root;
	private PlayerController playerController;
	private ArrayList<Level> levelArray;

	private LevelTilePaneController levelTilePaneController;

	private Button selectButton;

	public LevelSelectViewController(ArrayList<Level> levelArray, PlayerController playerController) {
		root = new LevelSelectView(levelArray, playerController);
		this.playerController = playerController;
		this.levelArray = levelArray;

		levelTilePaneController = root.levelTilePaneController;

		selectButton = root.selectButton;

		init();
	}

	public LevelSelectView getRoot() {
		return root;
	}

	public void init() {
		selectButton.setOnMouseClicked(e -> {
			if (levelTilePaneController.getSelected() == null)
				return;

			Level newLevel = levelTilePaneController.getSelected().getLevel();

			newLevel.setLayoutY(
					-(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
			playerController.getPlayer().setTranslateX(newLevel.getPlayerSpawn()[Dimensions.X.getIndex()]);
			playerController.getPlayer().setTranslateY(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()]);
			playerController.setLevel(newLevel);

			if (newLevel.getChildren().contains(playerController.getPlayer()))
				newLevel.getChildren().remove(playerController.getPlayer());

			newLevel.getChildren().add(playerController.getPlayer());

			root.getScene().setRoot(newLevel);
		});
	}
}
