package presentation.LevelSelectView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import Level.LevelController;
import Player.Player;
import Player.PlayerController;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import presentation.betterSelector.LevelTilePane;
import presentation.betterSelector.LevelTilePaneController;
import presentation.betterSelector.TileNode;

public class LevelSelectViewController {
	private LevelSelectView root;

	private LevelTilePaneController levelTilePaneController;
	private LevelTilePane levelTilePane;

	private Button selectButton;

	public LevelSelectViewController(ArrayList<Level> levelArray) {
		root = new LevelSelectView(levelArray);

		levelTilePaneController = root.levelTilePaneController;
		levelTilePane = root.levelTilePane;

		selectButton = root.selectButton;

		init();
	}

	public LevelSelectView getRoot() {
		return root;
	}

	public void init() {
		root.setOnKeyPressed(e -> {
			Level selectedLevel;
			LevelController selectedLevelController;

			switch (e.getCode()) {
			case LEFT:
				levelTilePaneController.prevSelected();
				break;
			case A:
				levelTilePaneController.prevSelected();
				break;
			case UP:
				levelTilePaneController.prevSelected();
				break;
			case W:
				levelTilePaneController.prevSelected();					
				break;
			case RIGHT:
				levelTilePaneController.nextSelected();
				break;
			case D:
				levelTilePaneController.nextSelected();
				break;
			case DOWN:
				levelTilePaneController.nextSelected();
				break;
			case S:
				levelTilePaneController.nextSelected();
				break;
			case ENTER:
				selectedLevel = levelTilePaneController.getSelected().getLevel();
				selectedLevelController = new LevelController(selectedLevel.getFile(), root);

				selectedLevelController.resetPlayer();
				root.getScene().setRoot(selectedLevelController.getRoot());
				selectedLevelController.getRoot().requestFocus();
				break;
			case SPACE:
				selectedLevel = levelTilePaneController.getSelected().getLevel();
				selectedLevelController = new LevelController(selectedLevel.getFile(), root);

				selectedLevelController.resetPlayer();
				root.getScene().setRoot(selectedLevelController.getRoot());
				selectedLevelController.getRoot().requestFocus();
				break;
			case ESCAPE:
//				root.getScene().setRoot();	Home
			}
		});

		selectButton.setOnMouseClicked(e -> {
			if (levelTilePaneController.getSelected() == null)
				return;

			Level selectedLevel = levelTilePaneController.getSelected().getLevel();
			LevelController selectedLevelController = new LevelController(selectedLevel.getFile(), root);

			selectedLevelController.resetPlayer();
			root.getScene().setRoot(selectedLevelController.getRoot());
			selectedLevelController.getRoot().requestFocus();
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
