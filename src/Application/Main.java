package Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Level.Level;
import Level.LevelGenerator;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import presentation.LevelSelectView.LevelSelectView;
import presentation.LevelSelectView.LevelSelectViewController;

public class Main extends Application {

	private Scene scene;

	private SimpleMinim minim;
	private AudioPlayer audioPlayer;
	private AudioPlayer audioPlayerSilent;
	private BeatDetect beat;

	private LevelSelectViewController levelSelectViewController;
	private LevelSelectView levelSelectView;

	private ArrayList<Level> levelArray;

	@Override
	public void start(Stage primaryStage) throws Exception {
		levelArray = new ArrayList<>();
//		initBeat();

		new LevelGenerator(20, 18);
		initLevel();

		levelSelectViewController = new LevelSelectViewController(levelArray);
		levelSelectView = levelSelectViewController.getRoot();

		/**
		 * SCENE + LEVEL Spieler im Level setzten
		 */
		scene = new Scene(levelSelectView, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		levelSelectView.requestFocus();
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		/**
		 * STAGE Stage wird gesetzt
		 */
		primaryStage.setScene(scene);
		primaryStage.setTitle("RythmJump");
//		primaryStage.setResizable(false);
		primaryStage.show();
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
}
