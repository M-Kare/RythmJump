package presentation.levelSelectView.levelTilePane;

import business.level.Level;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Klasse eines einzelnen Tile-Elements der TilePane (vergleich ListCell)
 */
public class TileNode extends VBox {
	private ImageView imageView;
	private Label levelName;
	private StackPane stack;
	private Rectangle rect1, rect2, rect3;

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
		levelName.setId("levelName");
		imageView = new ImageView(level.getThumbnail());
		stack = new StackPane();
//		rect1 = new Rectangle(width, 3, Color.rgb(235, 224, 205));
		rect2 = new Rectangle(width, 8, Color.rgb(40, 153, 112));
		rect3 = new Rectangle(width, 8, Color.rgb(16, 101, 180));

		imageView.setPreserveRatio(true);
		imageView.setFitHeight(height);
		imageView.setFitWidth(width);

		stack.setAlignment(Pos.BOTTOM_CENTER);
		this.setAlignment(Pos.CENTER);
		stack.setPrefSize(width, height);
		stack.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
		stack.getChildren().addAll(imageView);
		this.getChildren().addAll(stack, levelName, rect2, rect3);
		this.setSpacing(0);
		
		this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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