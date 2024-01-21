package Player;
import Application.Config;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	private double velocity;
	private boolean jumpable;
//	private Rectangle skin;

	public Player() {
		super(Config.PLAYER_SIZE, Config.PLAYER_SIZE * 2, Color.GREEN);
		jumpable = false;
		velocity = 0;
//		skin = new Rectangle(this.getWidth() + 2, this.getHeight() + 2, Color.RED);
	}
	
//	public Rectangle getSkin() {
//		return skin;
//	}
	
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
}
