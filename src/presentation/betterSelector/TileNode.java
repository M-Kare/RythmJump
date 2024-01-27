package presentation.betterSelector;

import Level.Level;
import Level.LevelController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileNode extends VBox{
	private ImageView imageView;
	private Label levelName;
	private StackPane stack;
//	private Rectangle background;
	
	private LevelController levelController;
	
	public TileNode(LevelController levelController, int width, int height, Color color) {
		super();
		this.levelController = levelController;
		levelName = new Label(this.levelController.getRoot().getLevelName());
		imageView = new ImageView(levelController.getRoot().getThumbnail());
		stack = new StackPane();
//		background = new Rectangle(width, height, color);
		
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
	
	public TileNode(LevelController levelController) {
		this(levelController, 200, 200, Color.WHITE);
	}
	
	public TileNode(LevelController levelController, int width, int height) {
		this(levelController, width, height, Color.WHITE);
	}
	
	public Pane getImagePane() {
		return stack;
	}
	
	public LevelController getLevelController() {
		return levelController;
	}
	
//	public Rectangle getTileBackground() {
//		return background;
//	}
//	
//	public void setBackgroundColor(Color color) {
//		background.setFill(color);
//	}	
}
