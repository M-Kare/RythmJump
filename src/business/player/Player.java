package business.player;

import java.net.MalformedURLException;

import business.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Player extends ImageView {
	private double velocity;
	private boolean jumpable;
	private Image skin;

	public Player() {
		super();
		try {
			skin = new Image(Config.findFile("player-trashcan.png", "./assets/textures/skins").toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jumpable = false;
		velocity = 0;
		
		this.setImage(skin);
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
