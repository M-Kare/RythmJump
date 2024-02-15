package presentation.homeView;

import presentation.levelSelectView.LevelSelectView;
import presentation.levelSelectView.LevelSelectViewController;
import presentation.playView.PlayViewController;
import presentation.settingsView.SettingsView;

import java.util.ArrayList;
import java.util.HashMap;

import business.Config;
import business.level.Level;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller für die HomeScreen-Klasse
 */
public class HomeScreenController {
	private Stage stage;

	private HomeScreen root;
	private Button play;
	private Button levelSelect;
	private Button tutorial;
	private LevelSelectViewController levelSelectViewController;
	private LevelSelectView levelSelectView;

	private Button settingsButton;
	private SettingsView settingsView;
	private HashMap<String, Level> levelArray;

	/**
	 * Logik für die Buttons des HomeScreen-Menues
	 * 
	 * @param levelArray Liste der verfügbaren Level zum laden.
	 */
	public HomeScreenController(HashMap<String, Level> levelArray, Stage stage) {
		this.stage = stage;
		root = new HomeScreen();
		play = root.play;
		levelSelect = root.levelSelect;
		tutorial = root.tutorial;
		settingsButton = root.settingButton;

		this.levelArray = levelArray;

		levelSelectViewController = new LevelSelectViewController(levelArray, root, stage);
		levelSelectView = levelSelectViewController.getRoot();

		settingsView = root.settingsView;

		init();
	}

	/**
	 * Setzt Listener und Handler für die Buttons
	 */
	public void init() {

		/**
		 * Sucht sich ein zufälliges Level aus der LevelListe und läd es
		 */
		play.setOnMouseClicked(e -> {
			ArrayList<Level> levelList = new ArrayList<>(levelArray.values());
			int randomIndex = Config.getRandomNumber(0, levelList.size() - 1);

			PlayViewController playViewController = new PlayViewController(levelSelectView, root,
					levelList.get(randomIndex), stage);
			playViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(playViewController.getRoot());
			playViewController.getLevelController().getRoot().requestFocus();
		});

		levelSelect.setOnMouseClicked(e -> {
//			root.getScene().setRoot(levelSelectView);
//			levelSelectView.requestFocus();
			transitionAnimation();
		});

		tutorial.setOnMouseClicked(e -> {
			Level tutorialLevel = levelArray.get("tutorial");

			PlayViewController playViewController = new PlayViewController(levelSelectView, root, tutorialLevel, stage);
			playViewController.getLevelController().resetPlayer();
			root.getScene().setRoot(playViewController.getRoot());
			playViewController.getLevelController().getRoot().requestFocus();
		});

		settingsButton.setOnMouseClicked(e -> {
			root.getChildren().add(settingsView);
		});
	}
	
	public void transitionAnimation() {
		StackPane animtationPane = new StackPane();
		animtationPane.setBackground(new Background(new BackgroundFill(Color.rgb(235, 224, 205), CornerRadii.EMPTY, Insets.EMPTY)));
//		Rectangle bg = new Rectangle(root.getWidth(), root.getHeight(), Color.rgb(235, 224, 205));
		
		animtationPane.getChildren().addAll(levelSelectView, root);
		levelSelectView.setTranslateX((-stage.getWidth())+503);
		stage.getScene().setRoot(animtationPane);
		
		TranslateTransition moveAnimation = new TranslateTransition();
		moveAnimation.setNode(animtationPane);
		moveAnimation.setDuration(Duration.millis(700));
		moveAnimation.setInterpolator(Interpolator.EASE_OUT);
		moveAnimation.setToX(stage.getWidth()-503);
		
		moveAnimation.setOnFinished(e -> {
			animtationPane.getChildren().removeAll(root, levelSelectView);
			stage.getScene().setRoot(levelSelectView);
			levelSelectView.requestFocus();
			
			levelSelectView.setTranslateX(0);
		});
		
		moveAnimation.playFromStart();
	}

	/**
	 * Getter für HomeScreen (Root)
	 * 
	 * @return root (HomesScreen)
	 */
	public HomeScreen getRoot() {
		return root;
	}
}
