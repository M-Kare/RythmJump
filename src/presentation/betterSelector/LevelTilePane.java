package presentation.betterSelector;

import java.util.ArrayList;

import Level.Level;
import Level.LevelController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.TilePane;

public class LevelTilePane extends ScrollPane {
	protected int nodeWidth;
	protected int nodeHeight;
	
	protected TilePane tilePane;

	private ArrayList<LevelController> levelControllerArray;
	protected ArrayList<TileNode> nodes;
	SimpleObjectProperty<TileNode>[] no;
	protected boolean oddTile = true;

	public LevelTilePane(ArrayList<LevelController> levelControllerArray, int nodeWidth, int nodeHeight) {
		super();
		this.levelControllerArray = levelControllerArray;
		nodes = new ArrayList<>();

		tilePane = new TilePane();
		
		tilePane.setVgap(100);
		tilePane.setHgap(100);
		tilePane.setPrefColumns(3);
		tilePane.setAlignment(Pos.TOP_LEFT);

		this.nodeHeight = nodeHeight;
		this.nodeWidth = nodeWidth;

		for (LevelController levelCon : this.levelControllerArray) {
			addTileNode(levelCon);
		}
		
		this.setContent(tilePane);
	}

	public LevelTilePane(ArrayList<LevelController> levelControllerArray) {
		this(levelControllerArray, 200, 200);
	}

	public ArrayList<TileNode> getTileNodes() {
		return nodes;
	}

	public void addTileNode(LevelController levelController) {
		oddTile = !oddTile;

		TileNode tileNode = new TileNode(levelController, nodeWidth, nodeHeight);

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
