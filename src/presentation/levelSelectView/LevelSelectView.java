package presentation.levelSelectView;

import java.util.HashMap;

import business.level.Level;
import business.player.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.levelSelectView.levelTilePane.LevelTilePane;
import presentation.levelSelectView.levelTilePane.LevelTilePaneController;

/**
 * Klasse für das LevelSelect-Menue
 */
public class LevelSelectView extends BorderPane {
	protected LevelTilePaneController levelTilePaneController;
	protected LevelTilePane levelTilePane;
	private HBox levelBox;

	private Label menuText;
	protected Button backButton;
	protected Button selectButton;

	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;

	/**
	 * Enthält die Level in Form der LevelTilePane, sowie Titel und Spieler-Preview
	 * 
	 * @param levelArray Level-Liste
	 */
	public LevelSelectView(HashMap<String, Level> levelArray) {
		levelTilePaneController = new LevelTilePaneController(levelArray);
		levelTilePane = levelTilePaneController.getRoot();
		levelTilePane.setId("levelTilePane");
		levelBox = new HBox(levelTilePane);
		levelBox.setId("levelBox");

		menuText = new Label("Level Select");
		menuText.getStyleClass().add("menuTitle");
		menuText.setId("menuTitle");

		backButton = new Button("🡸");
		backButton.setId("backButton");

		selectButton = new Button("Select");
		selectButton.setId("levelSelectButton");
		
		Player player = new Player();
		player.setScaleX(4);
		player.setScaleY(4);

		rightBox = new VBox(player, selectButton);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setSpacing(340);
		rightBox.setPrefWidth(350);
		rightBox.setMinWidth(USE_PREF_SIZE);

		leftBox = new VBox();
		leftBox.setPrefWidth(200);
		leftBox.setMinWidth(USE_PREF_SIZE);

		topBox = new HBox();
		topBox.getChildren().addAll(backButton, menuText);
		topBox.setAlignment(Pos.CENTER_LEFT);
		topBox.setPadding(new Insets(0, 0, 0, 100));
		topBox.setSpacing(60);								
		topBox.setPrefHeight(200);
		topBox.setMinHeight(USE_PREF_SIZE);

		bottomBox = new HBox();
		bottomBox.setPrefHeight(100);
		bottomBox.setMinHeight(USE_PREF_SIZE);

		this.setRight(rightBox);
		this.setLeft(leftBox);
		this.setTop(topBox);
		this.setBottom(bottomBox);
		this.setCenter(levelBox);
		
		this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	}
}
