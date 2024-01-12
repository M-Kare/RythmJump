import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
//	private Point2D velocity;
	private double velocity;
	private boolean jumpable;
	private Rectangle hitbox;

	public Player() {
		super(Config.PLAYER_SIZE, Config.PLAYER_SIZE * 2, Color.GREEN);
		jumpable = false;
		velocity = 0;
		hitbox = new Rectangle(this.getWidth() + 2, this.getHeight() + 2, Color.RED);
		init();
	}

	public void init() {
		this.translateXProperty().addListener((obs, oldValue, newValue) -> {
			hitbox.setTranslateX(newValue.doubleValue() - 1);
		});
		
		this.translateYProperty().addListener((obs, oldValue, newValue) -> {
			hitbox.setTranslateY(newValue.doubleValue() - 1);
		});
	}
	
	public Node getHitbox() {
		return hitbox;
	}
	
	public double getVelocity() {
		return velocity;
	}
	
	public void setVelocity(double y) {
		velocity = y;
	}
	
	public void addVelocity(double y) {
		velocity += y; 
	}

	public boolean getJumpable() {
		return jumpable;
	}

	public void setJumpable(boolean bool) {
		jumpable = bool;
	}

	public void movePlayerX(int value) {
		for (int i = 0; i < Math.abs(value); i++) {

		}
	}

	public void movePlayerY(int value) {

	}

}
