package presentation.deathView;

import business.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * View, die angezeigt wird, wenn der Spieler stirbt
 */
public class DeathView extends AnchorPane {
	private VBox middle;
	private Label hauptText;
	private Label description;
	protected Button levelSelectButton;
	protected Button repeat;

	/**
	 * Buttons zur Navigation und zwischen Bildschirm
	 */
	public DeathView() {
		hauptText = new Label("You Dieded");
		hauptText.setId("mainText");
		description = new Label("Press any key");
		description.setId("description");
		levelSelectButton = new Button();
		levelSelectButton.getStyleClass().add("iconButton");
		levelSelectButton.setId("backButton");
		repeat = new Button();
		repeat.getStyleClass().add("iconButton");
		repeat.setId("repeat");

		middle = new VBox(hauptText, description, repeat);
		middle.setAlignment(Pos.CENTER);
		middle.setSpacing(80);

		this.getChildren().addAll(middle, levelSelectButton);

		this.setLeftAnchor(levelSelectButton, 10.00);
		this.setTopAnchor(levelSelectButton, 10.00);

		this.setLeftAnchor(middle, 10.00);
		this.setRightAnchor(middle, 10.00);
		this.setTopAnchor(middle, 10.00);
		this.setBottomAnchor(middle, 10.00);

		this.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		this.setId("DeathView");
		
		this.getStylesheets().add(getClass().getResource("styleDeathView.css").toExternalForm());
	}
}
