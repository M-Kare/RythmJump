package presentation.homeView;

import business.Config;
import business.player.Player;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import presentation.settingsView.SettingsView;
import presentation.settingsView.SettingsViewController;

/**
 * Hauptmenue und Startbildschirm der Applikation
 */
public class HomeScreen extends StackPane {
	protected AnchorPane root;

	protected Label title;

	protected VBox buttons;
	protected Button play;
	protected Button levelSelect;
	protected Button tutorial;
	protected Button settingButton;
	
	protected ImageView playImgView;
	protected ImageView playImgView_hover;
	protected ImageView selectImgView;
	protected ImageView selectImgView_hover;
	protected ImageView tutorialImgView;
	protected ImageView tutorialImgView_hover;
	protected ImageView settingsImgView;
	protected ImageView settingsImgView_hover;

	private HBox playerPreview;
	protected Player player;

	private SettingsViewController settingsController;
	protected SettingsView settingsView;

	/**
	 * Besteht aus Buttons, die die anderen Views verbinden und einem Settings-Menue
	 */
	public HomeScreen() {
		root = new AnchorPane();
		
		title = new Label("Main menu");
		title.getStyleClass().add("menuTitle");
		
		title.setAlignment(Pos.CENTER);
		root.setTopAnchor(title, 40.00);
		root.setLeftAnchor(title, 50.00);
		root.setRightAnchor(title, 50.00);
		
		Image playImg = new Image("/pics/button_newGame.png");
		playImgView = new ImageView(playImg);
		Image playImg_hover = new Image("/pics/button_newGame_onhover.png");
		playImgView_hover = new ImageView(playImg_hover);
		play = new Button();
		play.setGraphic(playImgView);
		play.getStyleClass().add("button_menu");
		
		Image selectLevelImg = new Image("/pics/button_selectLevel.png");
		selectImgView = new ImageView(selectLevelImg);
		Image selectLevelImg_hover = new Image("/pics/button_selectLevel_onhover.png");
		selectImgView_hover = new ImageView(selectLevelImg_hover);
		levelSelect = new Button();
		levelSelect.setGraphic(selectImgView);
		levelSelect.getStyleClass().add("button_menu");
		
		Image tutorialImg = new Image("/pics/button_tutorial.png");
		tutorialImgView = new ImageView(tutorialImg);
		Image tutorialImg_hover = new Image("/pics/button_tutorial_onhover.png");
		tutorialImgView_hover = new ImageView(tutorialImg_hover);
		tutorial = new Button();
		tutorial.setGraphic(tutorialImgView);
		tutorial.getStyleClass().add("button_menu");

		Image settingsImg = new Image("/pics/button_settings.png");
		settingsImgView = new ImageView(settingsImg);
		Image settingsImg_hover = new Image("pics/button_settings_onhover.png");
		settingsImgView_hover = new ImageView(settingsImg_hover);
		settingButton = new Button();
		settingButton.setGraphic(settingsImgView);
		settingButton.getStyleClass().add("button_menu");
		
		buttons = new VBox(play, levelSelect, tutorial, settingButton);
		buttons.setAlignment(Pos.CENTER_LEFT);
		buttons.setSpacing(5);
		
		root.setBottomAnchor(buttons, 100.00);
		root.setLeftAnchor(buttons, 650.00);
		root.setRightAnchor(buttons, 50.00);

		player = new Player();
		player.setScaleX(4);
		player.setScaleY(4);

		playerPreview = new HBox(player);
		playerPreview.setAlignment(Pos.BOTTOM_CENTER);
		root.setLeftAnchor(playerPreview, 300.00);
		root.setBottomAnchor(playerPreview, 300.00);
		root.setTopAnchor(playerPreview, 50.00);

		root.getChildren().addAll(title, buttons, playerPreview);

		this.getChildren().addAll(root);
		this.setAlignment(Pos.CENTER);
		this.setId("homeView");
		
		settingsController = new SettingsViewController(this);
		settingsView = settingsController.getRoot();
		
		this.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	}
}
