package presentation.LevelSelectView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import Player.PlayerController;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import presentation.betterSelector.LevelTilePane;
import presentation.betterSelector.LevelTilePaneController;
import presentation.betterSelector.TileNode;

public class LevelSelectViewController {
	private LevelSelectView root;
	private PlayerController playerController;
	private ArrayList<Level> levelArray;

	private LevelTilePaneController levelTilePaneController;
	private LevelTilePane levelTilePane;

	private Button selectButton;

	public LevelSelectViewController(ArrayList<Level> levelArray, PlayerController playerController) {
		root = new LevelSelectView(levelArray, playerController);
		this.playerController = playerController;
		this.levelArray = levelArray;

		levelTilePaneController = root.levelTilePaneController;
		levelTilePane = root.levelTilePane;

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
		
		levelTilePane.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				boolean dropSupported = true;
				boolean copySupported = true;
				Dragboard dragboard;
				Set<TransferMode> modes;

				dragboard = event.getDragboard();
				if (!dragboard.hasFiles())
					dropSupported = false;

				modes = dragboard.getTransferModes();
				for (TransferMode mode : modes) {
					copySupported = copySupported || TransferMode.COPY == mode;
				}
				
				for (File file : dragboard.getFiles()) {
					if (!file.getName().contains(".lvl"))
						dropSupported = false;
				}
				
				if (copySupported && dropSupported)
					event.acceptTransferModes(TransferMode.COPY);
				event.consume();
			}
		});

		levelTilePane.setOnDragDropped(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();

				List<File> files = dragboard.getFiles();
				for (File file : files) {
					levelTilePaneController.addTileNode(new Level(file));
				}
				event.setDropCompleted(true);

				event.consume();
			}

		});
	}
}
