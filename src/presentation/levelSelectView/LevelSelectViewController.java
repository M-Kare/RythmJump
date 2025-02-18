package presentation.levelSelectView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import business.level.Level;
import business.level.LevelController;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.levelTilePane.LevelTilePane;
import presentation.levelSelectView.levelTilePane.LevelTilePaneController;
import presentation.playView.PlayView;
import presentation.playView.PlayViewController;

/**
 * Controller für die LevelSelectView
 */
public class LevelSelectViewController {
	private Stage stage;
	private LevelSelectView root;
	private HomeScreen homeScreen;

	private LevelTilePaneController levelTilePaneController;
	private LevelTilePane levelTilePane;

	private Button selectButton;
	private Button circle;
	
	private SimpleBooleanProperty transStarted;

	/**
	 * Navigation und Level-Selektierung
	 * 
	 * @param levelArray Level-Liste
	 * @param homeScreen HomeScreen zum Navigieren
	 */
	public LevelSelectViewController(HashMap<String, Level> levelArray, HomeScreen homeScreen, Stage stage) {
		this.stage = stage;
		root = new LevelSelectView(levelArray);
		this.homeScreen = homeScreen;

		levelTilePaneController = root.levelTilePaneController;
		levelTilePane = root.levelTilePane;

		selectButton = root.selectButton;
		circle = root.backButton;
		
		transStarted = new SimpleBooleanProperty(false);

		init();
	}

	/**
	 * Getter für die LevelSelectView
	 * 
	 * @return LevelSelectView
	 */
	public LevelSelectView getRoot() {
		return root;
	}
	
	public SimpleBooleanProperty getTransStarted() {
		return transStarted;
	}

	/**
	 * Lädt das ausgewählte Level, indem es die PlayView mit dem Level erzeugt
	 */
	public void loadLevel() {
		Level selectedLevel = levelTilePaneController.getSelected().getLevel();
		PlayViewController playViewController = new PlayViewController(root, homeScreen, selectedLevel, stage);

		root.getScene().setRoot(playViewController.getRoot());
		playViewController.getLevelController().getRoot().requestFocus();
	}

	/**
	 * Fügt Listener und Handler für die Buttons und Panes hinzu
	 */
	public void init() {
		/**
		 * Navigation über die Tastatur
		 */
		root.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case LEFT:
				levelTilePaneController.prevSelected();
				break;
			case A:
				levelTilePaneController.prevSelected();
				break;
			case UP:
				levelTilePaneController.prevSelected();
				break;
			case W:
				levelTilePaneController.prevSelected();
				break;
			case RIGHT:
				levelTilePaneController.nextSelected();
				break;
			case D:
				levelTilePaneController.nextSelected();
				break;
			case DOWN:
				levelTilePaneController.nextSelected();
				break;
			case S:
				levelTilePaneController.nextSelected();
				break;
			case ENTER:
				loadLevel();
				break;
			case SPACE:
				loadLevel();
				break;
			case ESCAPE:
//				root.getScene().setRoot(homeScreen);
//				homeScreen.requestFocus();
				transStarted.set(true);
				break;
			}
		});
		
		transStarted.addListener(e -> {
			if(transStarted.get()) {
				transitionAnimation();
			}
		});

		/**
		 * Select-Button -> Lädt Level
		 */
		selectButton.setOnMouseClicked(e -> {
			if (levelTilePaneController.getSelected() == null)
				return;

			loadLevel();
		});

		/**
		 * Back-button -> Navigiert zum HomeScreen
		 */
		circle.setOnMouseClicked(e -> {
//			root.getScene().setRoot(homeScreen);
//			homeScreen.requestFocus();
			transStarted.set(true);
		});

		/**
		 * Prüft ob Drag'n Drop Datei kopierbar ist und die richtige Datei-Endung
		 * enthält
		 */
		levelTilePane.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
				boolean dropSupported = true;
				boolean copySupported = true;
				Dragboard dragboard;
				Set<TransferMode> modes;

				dragboard = event.getDragboard();
				if (!dragboard.hasFiles())
					dropSupported = false;

				modes = dragboard.getTransferModes();
				for (TransferMode mode : modes) {
					copySupported = copySupported || TransferMode.COPY == mode;
				}

				for (File file : dragboard.getFiles()) {
					if (!file.getName().contains(".lvl"))
						dropSupported = false;
				}

				if (copySupported && dropSupported)
					event.acceptTransferModes(TransferMode.COPY);
				event.consume();
			}
		});

		/**
		 * Fügt eine neue TileNode für das neue Level hinzu
		 */
		levelTilePane.setOnDragDropped(new EventHandler<DragEvent>() {

			public void handle(DragEvent event) {
				Dragboard dragboard = event.getDragboard();

				List<File> files = dragboard.getFiles();
				for (File file : files) {
					levelTilePaneController.addTileNode(new Level(file));
				}
				event.setDropCompleted(true);
				event.consume();
			}

		});
	}
	
	public void transitionAnimation() {
		StackPane animtationPane = new StackPane();
		Rectangle bg = new Rectangle(root.getWidth(), root.getHeight(), Color.rgb(235, 224, 205));
		
		animtationPane.getChildren().addAll(root, bg, homeScreen);
		homeScreen.setTranslateX(stage.getWidth()-503);
		bg.setTranslateX(stage.getWidth()-503);
		stage.getScene().setRoot(animtationPane);
		
		TranslateTransition moveAnimation = new TranslateTransition();
		moveAnimation.setNode(animtationPane);
		moveAnimation.setDuration(Duration.millis(700));
		moveAnimation.setInterpolator(Interpolator.EASE_OUT);
		moveAnimation.setToX((-stage.getWidth())+503);
		
		moveAnimation.setOnFinished(e -> {
			animtationPane.getChildren().removeAll(homeScreen, root, bg);
			stage.getScene().setRoot(homeScreen);
			homeScreen.requestFocus();
			
			homeScreen.setTranslateX(0);
			bg.setTranslateX(0);
			transStarted.set(false);
		});
		
		moveAnimation.playFromStart();
	}
}
