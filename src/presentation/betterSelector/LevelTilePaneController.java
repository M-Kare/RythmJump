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

		selectedNode = null;

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

	public TileNode nextSelected() {
		int index;
		if (selectedNode == null) {
			index = 0;
		} else {
			index = tileNodes.indexOf(selectedNode) + 1;
			if (index >= tileNodes.size()) {
				index = 0;
			}
		}
		clearSelection();
		return selectNode(tileNodes.get(index));
	}

	public TileNode prevSelected() {
		int index;
		if (selectedNode == null) {
			index = 0;
		} else {
			index = tileNodes.indexOf(selectedNode) - 1;
			if (index < 0) {
				index = tileNodes.size() - 1;
			}
		}
		clearSelection();
		return selectNode(tileNodes.get(index));
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

	public TileNode selectNode(TileNode node) {
		node.getStyleClass().add("selected");
		selectedNode = node;
		return selectedNode;
	}
}
