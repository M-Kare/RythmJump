package Player;

import Application.Config;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
	private double velocity;
	private boolean jumpable;

	public Player() {
		super(Config.PLAYER_SIZE, Config.PLAYER_SIZE * 2, Color.GREEN);
		jumpable = false;
		velocity = 0;
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
}
