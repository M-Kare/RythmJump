package presentation.LevelSelectView;

import java.util.ArrayList;

import Level.Level;
import Player.PlayerController;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import presentation.betterSelector.LevelTilePane;
import presentation.betterSelector.LevelTilePaneController;

public class LevelSelectView extends BorderPane{
	protected LevelTilePaneController levelTilePaneController;
	private LevelTilePane levelTilePane;
	private HBox levelBox;
	
	private Label menuText;
	protected Button selectButton;
	private Node playerSkin;
	
	private VBox leftBox;
	private VBox rightBox;
	private HBox topBox;
	private HBox bottomBox;
	
	
	public LevelSelectView(ArrayList<Level> levelArray , PlayerController playerController) {
		levelTilePaneController = new LevelTilePaneController(levelArray, playerController);
		levelTilePane = levelTilePaneController.getRoot();
		levelBox = new HBox(levelTilePane);
		playerSkin =  clonePlayerSkin(playerController.getPlayer());
		
		menuText = new Label("Level Select");
		menuText.getStyleClass().add("menuTitle");
		
		selectButton = new Button("Select");
		selectButton.setId("levelSelectButton");
		
		rightBox = new VBox();
		rightBox.getChildren().addAll(playerSkin, selectButton);
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
	
	public Rectangle clonePlayerSkin(Rectangle playerSkin) {
		Rectangle rect = new Rectangle(playerSkin.getWidth() * 5, playerSkin.getHeight() * 5, playerSkin.getFill());
		return rect;
	}
}
