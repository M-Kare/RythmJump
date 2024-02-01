package presentation.levelSelectView.levelTilePane;

import java.util.ArrayList;
import java.util.HashMap;

import business.level.Level;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

/**
 * Eine ScrollPane, in der die Level mit Thumnbnails dargestellt werden.
 */
public class LevelTilePane extends ScrollPane {
	protected int nodeWidth;
	protected int nodeHeight;

	protected TilePane tilePane;

	private HashMap<String, Level> levelArray;
	protected ArrayList<TileNode> nodes;
	SimpleObjectProperty<TileNode>[] no;
	protected boolean oddTile = true;

	/**
	 * Ein Level besteht aus einer TilePane, welche ein Bild des Levels und deren
	 * Namen enthalten. Es werden drei Level pro Reihe dargestellt.
	 * 
	 * @param levelArray Alles geladenen Level
	 * @param nodeWidth  Breite einer Node
	 * @param nodeHeight Höhe einer Node
	 */
	public LevelTilePane(HashMap<String, Level> levelArray, int nodeWidth, int nodeHeight) {
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

		this.levelArray.forEach((name, level) -> {
			addTileNode(level);
		});
		this.setContent(tilePane);
	}

	/**
	 * Konstruktor, der die Standart-Größen für die Nodes verwendet
	 * 
	 * @param levelArray
	 */
	public LevelTilePane(HashMap<String, Level> levelArray) {
		this(levelArray, 200, 200);
	}

	/**
	 * Getter für die Liste, die alle Level-Nodes enthält
	 * 
	 * @return Level-Node List
	 */
	public ArrayList<TileNode> getTileNodes() {
		return nodes;
	}

	/**
	 * Fügt einen neuen Level-Node hinzu
	 * 
	 * @param level
	 */
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
