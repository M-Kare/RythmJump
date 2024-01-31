package business.player;

import java.net.MalformedURLException;

import business.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * Spieler den der User im Level bewegt. Velocity ist die vertikale
 * Geschwindigkeit des Spielers und die Rate, in der die Geschwindigkeit addiert
 * wird, ist die Beschleunigung
 */
public class Player extends ImageView {
	private double velocity;
	private boolean jumpable;
	private Image skin;

	/**
	 * Erzeugt den Spieler mit angegebenen Skin
	 */
	public Player(String skinName) {
		super();
		try {
			skin = new Image(Config.findFile(skinName, Config.SKINS_FOLDER).toURI().toURL().toExternalForm());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		jumpable = false;
		velocity = 0;

		this.setImage(skin);
	}

	/**
	 * Erzeugt Spieler mit Standart-Skin
	 */
	public Player() {
		this(Config.STD_SKIN);
	}

	/**
	 * Getter für die Geschwindigkeit
	 * 
	 * @return Geschwindigkeit
	 */
	public double getVelocity() {
		return velocity;
	}

	/**
	 * Setter für die Geschwindigkeit
	 * 
	 * @param y neuer Wert
	 */
	public void setVelocity(double y) {
		velocity = y;
	}

	/**
	 * Addiert Geschwindikeit hinzu (=Beschleunigung)
	 * 
	 * @param y zu addierender Wert
	 */
	public void addVelocity(double y) {
		velocity += y;
	}

	/**
	 * Getter für die Fähigkeit zu springen
	 * 
	 * @return jumpable
	 */
	public boolean getJumpable() {
		return jumpable;
	}

	/**
	 * Setter für die Fähigkeit zu springen
	 * 
	 * @param bool neuer Wert
	 */
	public void setJumpable(boolean bool) {
		jumpable = bool;
	}
}
