package presentation.betterSelector;

import java.util.ArrayList;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import Level.LevelController;
import Player.PlayerController;

public class LevelTilePaneController {
	private LevelTilePane root;

	private ArrayList<TileNode> tileNodes;
	private TileNode selectedNode;

//	private PlayerController playerController;

	public LevelTilePaneController(ArrayList<LevelController> levelControllerArray) {
		root = new LevelTilePane(levelControllerArray);
		tileNodes = root.nodes;

		selectedNode = tileNodes.get(0);

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
//				tileNodes = root.nodes;
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
	
	public void addTileNode(LevelController levelController) {
		root.oddTile = !root.oddTile;

		TileNode tileNode = new TileNode(levelController, root.nodeWidth, root.nodeHeight);

		if (root.oddTile) {
			tileNode.getImagePane().getStyleClass().add("oddTile");
		} else {
			tileNode.getImagePane().getStyleClass().add("evenTile");
		}

		tileNode.getStyleClass().add("tileNode");
		tileNodes.add(tileNode);
		root.tilePane.getChildren().add(tileNode);
		
		tileNode.setOnMouseClicked(e -> {
//			tileNodes = root.nodes;
			if (selectedNode == tileNode) {
				clearSelection();
			} else {
				clearSelection();
				selectNode(tileNode);
//				loadLevel();
			}
		});
	}

	public void loadLevel() {
		LevelController selectedLevelController = selectedNode.getLevelController();
		Level newLevel = selectedLevelController.getRoot();

//		newLevel.setLayoutY(-(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
//		playerController.getPlayer().setTranslateX(newLevel.getPlayerSpawn()[Dimensions.X.getIndex()]);
//		playerController.getPlayer().setTranslateY(newLevel.getPlayerSpawn()[Dimensions.Y.getIndex()]);
//		playerController.setLevel(newLevel);
//		
//		if (newLevel.getChildren().contains(playerController.getPlayer()))
//			newLevel.getChildren().removeAll(playerController.getPlayer());
//		
//		newLevel.getChildren().addAll(playerController.getPlayer());
		selectedLevelController.resetPlayer();
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
