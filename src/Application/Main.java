package Application;
import java.io.File;
import java.io.IOException;

import org.w3c.dom.css.Rect;

import Level.Level;
import Level.LevelController;
import Level.LevelGenerator;
import Player.Player;
import Player.PlayerController;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

	private Scene scene;
	
	private PlayerController playerController;
	private Player player;

//	private LevelController levelController;
	private LevelGenerator levelGen;
	private Level level;
	
	private SimpleMinim minim;
	private AudioPlayer audioPlayer;
	private AudioPlayer audioPlayerSilent;
	private BeatDetect beat;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initBeat();
		
		levelGen = new LevelGenerator(800, 18);
//		levelController = new LevelController(levelGen.getLevelArray(), player);
		level = new Level(levelGen.getLevelArray());
//		level = new Level(findFile("1-1.lvl", "."));
		
		playerController = new PlayerController(level, audioPlayerSilent, beat);
		player = playerController.getPlayer();
//		playerController.setObstacles(level.getObstacles());
		
		/**
		 * SCENE + LEVEL
		 * Spieler im Level setzten
		 */
		scene = new Scene(level, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		level.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
		level.getChildren().addAll(player);
		
		/**
		 * STAGE
		 * Stage wird gesetzt
		 */
		primaryStage.setScene(scene);
		primaryStage.setTitle("Wonder-Player 3000");
		primaryStage.setResizable(false);
		primaryStage.show();


		init(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void initBeat() throws IOException, InterruptedException {
		minim = new SimpleMinim(true);
		audioPlayerSilent = minim.loadFile(findFile("60bpm.mp3", ".").getCanonicalPath());
		audioPlayerSilent.mute();
		audioPlayer = minim.loadFile(findFile("60bpm.mp3", ".").getCanonicalPath());
		audioPlayer.setGain(-20);
		audioPlayerSilent.play();
		Thread.currentThread().sleep(1000/10);
		audioPlayer.play();
		beat = new BeatDetect();
		beat.setSensitivity(50);
	}

	/**
	 * Zum Suchen einer Datei im angegebenen Pfad
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

	public void init(Scene scene) {
		/**
		 * PRESSED
		 * aktualisiert, dass der Knopf gedrückt wird
		 */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				playerController.getKeybinds().put(event.getCode(), true);
//				levelController.getKeybinds().put(event.getCode(), true);
			}
		});

		/**
		 * UNPRESSED
		 * aktualisiert, dass der Knopf nicht mehr gedrückt wird
		 */
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				playerController.getKeybinds().put(event.getCode(), false);
//				levelController.getKeybinds().put(event.getCode(), false);
			}
		});

		/**
		 * SET_LAYOUT
		 * Damit die "Kamera" dem Spieler folgt
		 */
//		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
//			int playerPosX = newValue.intValue();
//			if (playerPosX > scene.getWidth() / 3 && playerPosX < level.getLevelLength() - scene.getWidth() / 100 * 66) {
//				level.setLayoutX(-(playerPosX - scene.getWidth() / 3));
//			}
//		});
//		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
//			int playerPosY = newValue.intValue();
//			if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (scene.getHeight() / 100 * 55)) {
//				level.setLayoutY(-(playerPosY - (scene.getHeight() / 2)));
//			}
//		});
	}
}
