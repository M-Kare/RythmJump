package presentation.levelSelectView;

import java.util.ArrayList;
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

public class LevelSelectView extends BorderPane {
	protected LevelTilePaneController levelTilePaneController;
	protected LevelTilePane levelTilePane;
	private HBox levelBox;

	private Label menuText;
	protected Button designElement_circle;
	protected Button selectButton;

	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;

	public LevelSelectView(HashMap<String, Level> levelArray) {
		levelTilePaneController = new LevelTilePaneController(levelArray);
		levelTilePane = levelTilePaneController.getRoot();
		levelBox = new HBox(levelTilePane);

		Image menuTextImg = new Image("/pics/menu_title_alt@0.75x.png"); // menu title as png file instead of plain text
		ImageView menuTextView = new ImageView(menuTextImg);
		menuTextView.setPreserveRatio(true);
		menuText = new Label();
		menuText.setGraphic(menuTextView);

		menuText.setId("menuTitle");

		designElement_circle = new Button("←");
		designElement_circle.getStyleClass().add("button");

		designElement_circle.setId("circle");

		selectButton = new Button("Select");

		selectButton.getStyleClass().add("button"); // general styling for all buttons
		selectButton.setId("levelSelectButton"); // button specific styling

		Player player = new Player();
		player.setScaleX(4);
		player.setScaleY(4);
		
		rightBox = new VBox(player, selectButton);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setSpacing(200);
		rightBox.setPrefWidth(400);
		rightBox.setMinWidth(USE_PREF_SIZE);

		leftBox = new VBox();
		leftBox.setPrefWidth(200);
		leftBox.setMinWidth(USE_COMPUTED_SIZE);

		topBox = new HBox();
		topBox.getChildren().addAll(designElement_circle, menuText);
		topBox.setSpacing(50); // added spacing btw top box elements

		topBox.setPadding(new Insets(40, 5, 10, 105)); // padding for top box, title is in line with level box
														// horizontally 155
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
