package presentation.betterSelector;

import java.util.ArrayList;

import Level.Level;

public class LevelTilePaneController {
	private LevelTilePane root;

	private ArrayList<TileNode> tileNodes;
	private TileNode selectedNode;

	public LevelTilePaneController(ArrayList<Level> levelArray) {
		root = new LevelTilePane(levelArray);
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
				if (selectedNode == node) {
					clearSelection();
				} else {
					clearSelection();
					selectNode(node);
				}
			});
		}

	}

	public void addTileNode(Level level) {
		root.oddTile = !root.oddTile;

		TileNode tileNode = new TileNode(level, root.nodeWidth, root.nodeHeight);

		if (root.oddTile) {
			tileNode.getImagePane().getStyleClass().add("oddTile");
		} else {
			tileNode.getImagePane().getStyleClass().add("evenTile");
		}

		tileNode.getStyleClass().add("tileNode");
		tileNodes.add(tileNode);
		root.tilePane.getChildren().add(tileNode);

		tileNode.setOnMouseClicked(e -> {
			if (selectedNode == tileNode) {
				clearSelection();
			} else {
				clearSelection();
				selectNode(tileNode);
			}
		});
	}

	public void clearSelection() {
		if (selectedNode == null)
			return;
		selectedNode.getStyleClass().remove("selected");
		selectedNode = null;
	}

	public void selectNode(TileNode node) {
		node.getStyleClass().add("selected");
		selectedNode = node;
	}
}
