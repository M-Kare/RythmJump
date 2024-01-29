package presentation.homeView;

import Application.Config;
import Player.Player;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class HomeScreen extends VBox{
	
	protected Label header;
	protected HBox buttons;
	protected HBox playerPreview;
	protected Button play;
	protected Button levelSelect;
	protected Button tutorial;
	protected Player player;
	
	public HomeScreen() {
		
		header = new Label("Main Menu");
		header.getStyleClass().add("menuTitle");
		header.setAlignment(Pos.TOP_CENTER);
		
		play = new Button("New Game");
		play.setId("playButton");
		levelSelect = new Button("Select Level");
		levelSelect.setId("levelSelectButton");
		tutorial = new Button("Tutorial");
		tutorial.setId("tutorialButton");
		
		player = new Player();
		player.setHeight(player.getHeight()*5);
		player.setWidth(player.getWidth()*5);
		
		buttons = new HBox(play, levelSelect, tutorial);
		buttons.setAlignment(Pos.CENTER_RIGHT);
		playerPreview = new HBox(player);
		playerPreview.setAlignment(Pos.CENTER_LEFT);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().addAll(header, playerPreview, buttons);
		this.setMinSize(Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		this.setId("HomeView");
	}
}
