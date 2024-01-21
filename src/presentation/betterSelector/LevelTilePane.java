package presentation.betterSelector;

import java.util.ArrayList;

import Level.Level;
import javafx.geometry.Pos;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class LevelTilePane extends TilePane {
	private int nodeWidth;
	private int nodeHeight;

	private ArrayList<Level> levelArray;
	private ArrayList<TileNode> nodes;
	private boolean oddTile = true;

	public LevelTilePane(ArrayList<Level> levelArray, int nodeWidth, int nodeHeight) {
		super();
		this.levelArray = levelArray;
		nodes = new ArrayList<>();

		this.setVgap(100);
		this.setHgap(100);
		this.setPrefColumns(3);
		this.setAlignment(Pos.TOP_CENTER);

		this.nodeHeight = nodeHeight;
		this.nodeWidth = nodeWidth;

		for (Level level : this.levelArray) {
			addTileNode(level);
		}
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
//			tileNode.setBackgroundColor(Color.LIGHTGRAY);

		nodes.add(tileNode);
		this.getChildren().add(tileNode);
	}

}
