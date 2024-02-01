package presentation.levelSelectView.levelTilePane;

import business.level.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Klasse eines einzelnen Tile-Elements der TilePane (vergleich ListCell)
 */
public class TileNode extends VBox {
	private ImageView imageView;
	private Label levelName;
	private StackPane stack;

	private Level level;

	/**
	 * Fügt Name und Thumbnail des Levels hinzu
	 * 
	 * @param level  Level
	 * @param width  Breite der Node
	 * @param height Höhe der Node
	 */
	public TileNode(Level level, int width, int height) {
		super();
		this.level = level;
		levelName = new Label(this.level.getLevelName());
		imageView = new ImageView(level.getThumbnail());
		stack = new StackPane();

		imageView.setPreserveRatio(true);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);

		stack.setAlignment(Pos.BOTTOM_CENTER);
		this.setAlignment(Pos.CENTER);
		stack.setPrefSize(width, height);
		stack.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);

		stack.getChildren().addAll(imageView);
		this.getChildren().addAll(stack, levelName);
		this.setSpacing(3);
	}

	/**
	 * Konstruktor für mit Standart-Größen
	 * 
	 * @param level Level
	 */
	public TileNode(Level level) {
		this(level, 200, 200);
	}

	/**
	 * Getter für den StackPane, der das Thumbnail enthält
	 * 
	 * @return StackPane
	 */
	public Pane getImagePane() {
		return stack;
	}

	/**
	 * Getter für das hinterlegte Level
	 * 
	 * @return Level
	 */
	public Level getLevel() {
		return level;
	}
}