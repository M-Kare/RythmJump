package presentation.levelSelectView.levelTilePane;

import java.util.ArrayList;

import business.level.Level;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

public class LevelTilePane extends ScrollPane {
	protected int nodeWidth;
	protected int nodeHeight;

	protected TilePane tilePane;

	private ArrayList<Level> levelArray;
	protected ArrayList<TileNode> nodes;
	SimpleObjectProperty<TileNode>[] no;
	protected boolean oddTile = true;

	public LevelTilePane(ArrayList<Level> levelArray, int nodeWidth, int nodeHeight) {
		super();
		this.levelArray = levelArray;
		nodes = new ArrayList<>();

		tilePane = new TilePane();

		tilePane.setVgap(100);
		tilePane.setHgap(100);
		tilePane.setPrefColumns(3);
		tilePane.setAlignment(Pos.TOP_LEFT);

		this.nodeHeight = nodeHeight;
		this.nodeWidth = nodeWidth;

		for (Level level : this.levelArray) {
			addTileNode(level);
		}
		this.setContent(tilePane);
	}

	public LevelTilePane(ArrayList<Level> levelArray) {
		this(levelArray, 200, 200);
	}

	public ArrayList<TileNode> getTileNodes() {
		return nodes;
	}

	public void addTileNode(Level level) {
		oddTile = !oddTile;

		TileNode tileNode = new TileNode(level, nodeWidth, nodeHeight);

		if (oddTile) {
			tileNode.getImagePane().getStyleClass().add("oddTile");
		} else {
			tileNode.getImagePane().getStyleClass().add("evenTile");
		}

		tileNode.getStyleClass().add("tileNode");
		nodes.add(tileNode);
		tilePane.getChildren().add(tileNode);
	}

}
