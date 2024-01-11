import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	private Point2D velocity;
	private boolean jumpable;

	public Player() {
		super(Config.PLAYER_SIZE, Config.PLAYER_SIZE * 2, Color.RED);
		jumpable = false;
		velocity = new Point2D(0, 0);
	}

	public Point2D getVelocity() {
		return velocity;
	}

	public void setVelocity(double x, double y) {
		velocity = velocity.add(x, y);
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
