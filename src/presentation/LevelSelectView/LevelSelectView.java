package presentation.LevelSelectView;

import java.util.ArrayList;

import Level.Level;
import Player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import presentation.betterSelector.LevelTilePane;
import presentation.betterSelector.LevelTilePaneController;

public class LevelSelectView extends BorderPane {
	protected LevelTilePaneController levelTilePaneController;
	protected LevelTilePane levelTilePane;
	private HBox levelBox;

	private Label menuText;
	protected Button selectButton;

	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;

	public LevelSelectView(ArrayList<Level> levelArray) {
		levelTilePaneController = new LevelTilePaneController(levelArray);
		levelTilePane = levelTilePaneController.getRoot();
		levelBox = new HBox(levelTilePane);

		menuText = new Label("Level Select");
		menuText.getStyleClass().add("menuTitle");

		selectButton = new Button("Select");
		selectButton.setId("levelSelectButton");
		selectButton.setFocusTraversable(false);

		Player player = new Player();
		player.setHeight(player.getHeight()*5);
		player.setWidth(player.getWidth()*5);
		
		rightBox = new VBox();
		rightBox.getChildren().addAll(player, selectButton);
		rightBox.setAlignment(Pos.BOTTOM_LEFT);
		rightBox.setSpacing(100);
		rightBox.setPrefWidth(400);
		rightBox.setMinWidth(USE_PREF_SIZE);

		leftBox = new VBox();
		leftBox.setPrefWidth(200);
		leftBox.setMinWidth(USE_PREF_SIZE);

		topBox = new HBox();
		topBox.getChildren().addAll(menuText);
		topBox.setAlignment(Pos.CENTER);
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
	}
}
