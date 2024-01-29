package presentation.LevelSelectView;

import java.util.ArrayList;

import Level.Level;
import Player.Player;
import Player.PlayerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import presentation.betterSelector.LevelTilePane;
import presentation.betterSelector.LevelTilePaneController;

public class LevelSelectView extends BorderPane {
	protected LevelTilePaneController levelTilePaneController;
	protected LevelTilePane levelTilePane;
	private HBox levelBox;

	private Label menuText;
	private Label designElement_circle;
	protected Button selectButton;
	protected Button test;

	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;

	public LevelSelectView(ArrayList<Level> levelArray) {
		levelTilePaneController = new LevelTilePaneController(levelArray);
		levelTilePane = levelTilePaneController.getRoot();
		levelBox = new HBox(levelTilePane);

		Image menuTextImg = new Image("menu_title_alt@0.75x.png"); // menu title as png file instead of plain text
		ImageView menuTextView = new ImageView(menuTextImg);
		menuTextView.setPreserveRatio(true);
		menuText = new Label();
		menuText.setGraphic(menuTextView);

		menuText.setId("menuTitle");

		Image circle = new Image("circle@0.75x.png"); // added circular design element in front of menu title
		ImageView circleView = new ImageView(circle);
		circleView.setPreserveRatio(true);
		designElement_circle = new Label();
		designElement_circle.setGraphic(circleView);
		// designElement_circle.setPadding(new Insets(10, 0, 0, 0)); //padding circle

		designElement_circle.setId("circle");

		selectButton = new Button("Select");
		/*
		 * Image buttonImg = new Image("button@0.75x.png"); ImageView buttonView = new
		 * ImageView(buttonImg); buttonView.setPreserveRatio(true); selectButton = new
		 * Button(); selectButton.setGraphic(buttonView);
		 */

		selectButton.getStyleClass().add("button"); // general styling for all buttons
		selectButton.setId("levelSelectButton"); // button specific styling

		Player player = new Player();
		player.setHeight(player.getHeight()*5);
		player.setWidth(player.getWidth()*5);
		rightBox = new VBox(player, selectButton);
		rightBox.setAlignment(Pos.BOTTOM_CENTER);
		rightBox.setSpacing(100);
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
