package presentation.levelSelectView.levelTilePane;

import java.util.ArrayList;
import java.util.HashMap;

import business.level.Level;

/**
 * Controller für die LevelTilePane, der die Selection verwaltet
 */
public class LevelTilePaneController {
	private LevelTilePane root;

	private ArrayList<TileNode> tileNodes;
	private TileNode selectedNode;

	/**
	 * Erzeugt die LevelTilePane und holt sich die Liste der Nodes
	 * 
	 * @param levelArray Level-Liste
	 */
	public LevelTilePaneController(HashMap<String, Level> levelArray) {
		root = new LevelTilePane(levelArray);
		tileNodes = root.nodes;

		selectedNode = null;

		init();
	}

	/**
	 * Getter für die LevelTilePane
	 * 
	 * @return LevelTilePane
	 */
	public LevelTilePane getRoot() {
		return root;
	}

	/**
	 * Getter für den ausgewählten Node
	 * 
	 * @return Level-Node
	 */
	public TileNode getSelected() {
		return selectedNode;
	}

	/**
	 * Fügt den Level-Nodes einen Eventhandler hinzu für die Selections-Logik
	 */
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

	/**
	 * Zum Scrollen durch die Level-Nodes
	 * 
	 * @return next Level-Node
	 */
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

	/**
	 * Zum Scrollen durch die Level-Nodes
	 * 
	 * @return prev Level-Node
	 */
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

	/**
	 * Fügt eine neue Level-Node hinzu und setzt das Styling und die Handler
	 * 
	 * @param level
	 */
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

	/**
	 * Setzt die ausgewählte Node zurück auf Null
	 */
	public void clearSelection() {
		if (selectedNode == null)
			return;
		selectedNode.getStyleClass().remove("selected");
		selectedNode = null;
	}

	/**
	 * Setzt den ausgewählten Node
	 * 
	 * @param node auszuwählender Node
	 * @return ausgewählter Node
	 */
	public TileNode selectNode(TileNode node) {
		node.getStyleClass().add("selected");
		selectedNode = node;
		return selectedNode;
	}
}
