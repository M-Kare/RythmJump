package presentation.deathView;

import business.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * View, die angezeigt wird, wenn der Spieler stirbt
 */
public class DeathView extends AnchorPane {
	
	private Label title;
	private Label description;
	
	private VBox middle;
	private HBox navigation;
	protected Button home, levelSelect, repeat;

	/**
	 * Buttons zur Navigation und zwischen Bildschirm
	 */
	public DeathView() {
		title = new Label("You Dieded");
		title.setId("deathText");
		description = new Label("Press any key");
		description.setId("description");
		
		home = new Button("Home");
		home.getStyleClass().add("endButton");
		
		levelSelect = new Button("Level Select");
		levelSelect.getStyleClass().add("endButton");
		
		repeat = new Button("Repeat");
		repeat.getStyleClass().add("endButton");
		
		navigation = new HBox(home, levelSelect, repeat);
		navigation.setSpacing(20);
		navigation.setAlignment(Pos.CENTER);

		middle = new VBox(title, description, navigation);
		middle.setAlignment(Pos.CENTER);
		middle.setSpacing(150);

		this.getChildren().addAll(middle);

		this.setLeftAnchor(levelSelect, 10.00);
		this.setTopAnchor(levelSelect, 10.00);

		this.setLeftAnchor(middle, 10.00);
		this.setRightAnchor(middle, 10.00);
		this.setTopAnchor(middle, 10.00);
		this.setBottomAnchor(middle, 10.00);

		this.setId("deathView");
		
		this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	}
}
