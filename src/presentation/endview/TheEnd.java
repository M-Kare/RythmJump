package presentation.endview;

import business.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * View, wenn man ein Level gewinnt
 */
public class TheEnd extends AnchorPane {
	protected Label title;

	protected VBox middle;
	protected HBox navigation;
	protected Button home, levelSelect, repeat;

	protected HBox stats;
	protected VBox leftBox;
	protected Label jumpText;
	protected Label deathText;
	protected Label missedText;
	protected Label beatsText;
	protected VBox rightBox;
	protected Label jumps;
	protected Label missedJumps;
	protected Label beats;
	protected Label deaths;

	/**
	 * Enthält Stats- und Navigationselemente
	 */
	public TheEnd() {
		// stats
		jumpText = new Label("Jumps:");
		missedText = new Label("Off-Beat Jumps:");
		beatsText = new Label("Beats:");
		deathText = new Label("Deaths:");
		leftBox = new VBox(jumpText, missedText, beatsText, deathText);

		jumps = new Label("0");
		missedJumps = new Label("0");
		beats = new Label("0");
		deaths = new Label("0");
		rightBox = new VBox(jumps, missedJumps, beats, deaths);

		stats = new HBox(leftBox, rightBox);
		stats.setSpacing(5);

		this.getChildren().add(stats);
		this.setTopAnchor(stats, 20.00);
		this.setRightAnchor(stats, 20.00);

		// Middle-Box
		title = new Label("Level Clear");
		title.setId("clearText");

		home = new Button("Home");
		home.setId("homeButton");
		levelSelect = new Button("Level Select");
		levelSelect.setId("levelSelectButton");
		repeat = new Button("Repeat");
		repeat.setId("repeatLevelButton");

		navigation = new HBox(home, levelSelect, repeat);
		navigation.setAlignment(Pos.CENTER);

		middle = new VBox(title, navigation);
		middle.setAlignment(Pos.CENTER);
		middle.setSpacing(200);

		this.getChildren().addAll(middle);
		this.setBottomAnchor(middle, 10.00);
		this.setTopAnchor(middle, 10.00);
		this.setRightAnchor(middle, 10.00);
		this.setLeftAnchor(middle, 10.00);

		this.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		this.setId("theEndView");
	}

	/**
	 * Setter für den Jump-Stat
	 * 
	 * @param value JumpCount
	 */
	public void setJumps(int value) {
		jumps.setText(Integer.toString(value));
	}

	/**
	 * Setter für den Tode-Stat
	 * 
	 * @param value DeathCount
	 */
	public void setDeaths(int value) {
		deaths.setText(Integer.toString(value));
	}

	/**
	 * Setter für den Beats-Stat
	 * 
	 * @param value BeatsCount
	 */
	public void setBeats(int value) {
		beats.setText(Integer.toString(value));
	}

	/**
	 * Setter für den verfehlte Sprünge-Stat
	 * 
	 * @param value missedJumpCount
	 */
	public void setMissedJumps(int value) {
		missedJumps.setText(Integer.toString(value));
	}

}
