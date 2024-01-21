package presentation.betterSelector;

import java.util.ArrayList;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import Player.PlayerController;

public class LevelTilePaneController {
	private LevelTilePane root;

	private ArrayList<TileNode> tileNodes;
	private TileNode selectedNode;

	private PlayerController playerController;

	public LevelTilePaneController(ArrayList<Level> levelArray, PlayerController playerController) {
		root = new LevelTilePane(levelArray);
		tileNodes = root.getTileNodes();

		selectedNode = tileNodes.get(0);

		this.playerController = playerController;

		init();
	}

	public LevelTilePane getRoot() {
		return root;
	}

	public TileNode getSelected() {
		return selectedNode;
	}

	public void init() {
		for (TileNode node : tileNodes) {
			node.setOnMouseClicked(e -> {
				if (selectedNode == node) {
					clearSelection();
				} else {
					clearSelection();
					selectNode(node);
//					loadLevel();
				}
			});
		}

	}

	public void loadLevel() {
		Level newLevel = selectedNode.getLevel();

		newLevel.setLayoutY(-(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
		playerController.getPlayer().setTranslateX(newLevel.getPlayerSpawn()[Dimensions.X.getIndex()]);
		playerController.getPlayer().setTranslateY(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()]);
		playerController.setLevel(newLevel);
		
		if (newLevel.getChildren().contains(playerController.getPlayer()))
			newLevel.getChildren().removeAll(playerController.getPlayer());
		
		newLevel.getChildren().addAll(playerController.getPlayer());
		root.getScene().setRoot(newLevel);
	}

	public void clearSelection() {
		if (selectedNode == null)
			return;
//		selectedNode.getImagePane().getStyleClass().remove("selected");
		selectedNode.getStyleClass().remove("selected");
		selectedNode = null;
	}

	public void selectNode(TileNode node) {
//		node.getImagePane().getStyleClass().add("selected");
		node.getStyleClass().add("selected");
		selectedNode = node;
	}
}
