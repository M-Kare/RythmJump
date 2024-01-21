package Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Level.Level;
import Level.LevelGenerator;
import Player.Player;
import Player.PlayerController;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import presentation.LevelSelectView.LevelSelectView;
import presentation.LevelSelectView.LevelSelectViewController;
import presentation.LevelSelect_depricated.LevelListView;
import presentation.LevelSelect_depricated.LevelListViewController;
import presentation.betterSelector.LevelTilePane;
import presentation.betterSelector.LevelTilePaneController;

public class Main extends Application {

	private Scene scene;

	private PlayerController playerController;
//	private Player player;

	private Level level;

	private SimpleMinim minim;
	private AudioPlayer audioPlayer;
	private AudioPlayer audioPlayerSilent;
	private BeatDetect beat;
	
	private LevelSelectViewController levelSelectViewController;
	private LevelSelectView levelSelectView;
	
//	private LevelTilePaneController levelTilePaneController;
//	private LevelTilePane levelTilePane;
//	private HBox levelSelectBox;
	
	private ArrayList<Level> levelArray;

	@Override
	public void start(Stage primaryStage) throws Exception {
		levelArray = new ArrayList<>();
//		initBeat();

		initLevel();
		
		level = levelArray.get(levelArray.size() - 1);

		playerController = new PlayerController(level, audioPlayerSilent, beat);
//		player = playerController.getPlayer();
		
		levelSelectViewController = new LevelSelectViewController(levelArray, playerController);
		levelSelectView = levelSelectViewController.getRoot();
		
//		levelTilePaneController = new LevelTilePaneController(new ArrayList<Level>(levelMap.values()), playerController);
//		levelTilePane = levelTilePaneController.getRoot();
//		levelSelectBox = new HBox(levelTilePane);
		
		/**
		 * SCENE + LEVEL Spieler im Level setzten
		 */
		scene = new Scene(levelSelectView, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
//		level.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
//		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
//		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
//		level.getChildren().add(player);

		/**
		 * STAGE Stage wird gesetzt
		 */
		primaryStage.setScene(scene);
		primaryStage.setTitle("RythmJump");
//		primaryStage.setResizable(false);
		primaryStage.show();

		init(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Sucht alle .lvl Dateien im Projekt und fügt sie einer HashMap hinzu
	 */
	public void initLevel() {
		Level tempLevel;
		for (File levelFile : findFilesBySuffix(Config.LEVEL_SUFFIX, ".")) {
			tempLevel = new Level(levelFile);
			System.out.println(levelFile.getName());
			levelArray.add(tempLevel);
		}
		levelArray.add(new Level(new LevelGenerator(800, 18).getLevelArray(), "Random Level"));
	}

	/**
	 * Testweise Methode für die Musik und Beat-Erkennung Spielt die Musik und eine
	 * lautlose Version ab, die versetzt ist, damit der Beat frühzeitig erkannt
	 * werden kann.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void initBeat() throws IOException, InterruptedException {
		minim = new SimpleMinim(true);
		audioPlayerSilent = minim.loadFile(findFile("60bpm.mp3", ".").getCanonicalPath());
		audioPlayerSilent.mute();
		audioPlayer = minim.loadFile(findFile("60bpm.mp3", ".").getCanonicalPath());
		audioPlayer.setGain(-20);
		audioPlayerSilent.play(1000 / 10);
		audioPlayer.play();
		beat = new BeatDetect();
		beat.setSensitivity(50);
		beat.detect(audioPlayerSilent.mix);
	}

	/**
	 * Sucht die erste Datei, im angegebenen Pfad, die mit dem Namen übereinstimmt
	 * 
	 * @param fileName        Name der zu suchenden Datei
	 * @param searchDirectory Pfad des Verzeichnisses, in dem gesucht werden soll
	 * @return gefundene Datei
	 * @throws IOException
	 */
	public File findFile(String fileName, String searchDirectory) throws IOException {
		File dir = new File(searchDirectory);
		File[] fileList = dir.listFiles();

		for (File file : fileList) {
			System.out.println(file.getName());
			if (!file.getName().startsWith(".")) {

				if (file.isFile()) {
					if (file.getName().equals(fileName)) {
						return file;
					}
				} else if (file.isDirectory()) {
					File temp = findFile(fileName, file.getCanonicalPath());
					if (temp != null && temp.getName().equals(fileName)) {
						return temp;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Sucht alle Dateien im angegebenen Pfad, die mit dem suffix übereinstimmen und
	 * packt sie in eine ArrayList
	 * 
	 * @param suffix
	 * @param dir
	 * @return
	 */
	public ArrayList<File> findFilesBySuffix(String suffix, String dir) {
		ArrayList<File> foundFiles = new ArrayList<>();
		File directory = new File(dir);
		File[] fileList = directory.listFiles();

		for (File file : fileList) {
			if (!file.getName().startsWith(".")) {
				if (file.isFile()) {
					if (file.getName().endsWith(suffix)) {
//						System.out.println(file.getName() + "here");
						foundFiles.add(file);
					}
				} else if (file.isDirectory()) {
					for (File recFile : findFilesBySuffix(suffix, file.getAbsolutePath())) {
//						System.out.println(recFile.getName());
						foundFiles.add(recFile);
					}
				}
			}
		}
		return foundFiles;
	}

	public void init(Scene scene) {
		/**
		 * PRESSED aktualisiert, dass der Knopf gedrückt wird
		 */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				playerController.getKeybinds().put(event.getCode(), true);
				if(event.getCode() == KeyCode.ESCAPE) {
					scene.setRoot(levelSelectView);
				}
			}
		});

		/**
		 * UNPRESSED aktualisiert, dass der Knopf nicht mehr gedrückt wird
		 */
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				playerController.getKeybinds().put(event.getCode(), false);
			}
		});
	}
}
