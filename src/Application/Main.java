package Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import business.Config;
import business.level.Level;
import business.level.LevelGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.homeView.HomeScreen;
import presentation.homeView.HomeScreenController;

public class Main extends Application {

	private Scene scene;

	private HomeScreen homeScreen;
	private HomeScreenController homeScreenController;

	private HashMap<String, Level> levelArray;
	private ArrayList<String> songs;

	boolean onBeat = false;
	int counter = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		levelArray = new HashMap<>();
		songs = new ArrayList<>();

		initSongs();

		new LevelGenerator(500, 21);//500, 18
		initLevel();

		homeScreenController = new HomeScreenController(levelArray, primaryStage);
		homeScreen = homeScreenController.getRoot();

		scene = new Scene(homeScreen, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		homeScreen.requestFocus();

		primaryStage.setScene(scene);
		primaryStage.setTitle("RythmJump");
		primaryStage.setMaximized(true);
//		primaryStage.setResizable(false);
		primaryStage.show();

		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Sucht alle .lvl Dateien im Projekt und fügt sie einer HashMap hinzu
	 */
	public void initLevel() {
		Level tempLevel;
		int randomSongIndex;
		for (File levelFile : findFilesBySuffix(Config.LEVEL_SUFFIX, ".")) {
			randomSongIndex = Config.getRandomNumber(0, songs.size() - 1);
			tempLevel = new Level(levelFile, songs.get(randomSongIndex));
			System.out.println(levelFile.getName());
			levelArray.put(tempLevel.getLevelName(), tempLevel);
		}
	}

	/**
	 * sucht die .mp3-Dateien aus dem mp3 Ordner und fügt den Pfad in eine ArrayList
	 * hinzu
	 */
	public void initSongs() {
		ArrayList<File> songFiles = findFilesBySuffix(".mp3", Config.MUSIC_FOLDER);
//		songFiles.add(findFile("loop.wav", "."));
		for (File song : songFiles) {
			try {
				songs.add(song.getCanonicalPath());
			} catch (IOException e) {
				System.err.print("Song could not be added!");
			}
		}
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
						foundFiles.add(file);
					}
				} else if (file.isDirectory()) {
					for (File recFile : findFilesBySuffix(suffix, file.getAbsolutePath())) {
						foundFiles.add(recFile);
					}
				}
			}
		}
		return foundFiles;
	}
}
