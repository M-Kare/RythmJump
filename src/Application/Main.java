package Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Level.Level;
import Level.LevelGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.homeView.HomeScreen;
import presentation.homeView.HomeScreenController;

public class Main extends Application {

	private Scene scene;

	private HomeScreen homeScreen;
	private HomeScreenController homeScreenController;

	private ArrayList<Level> levelArray;
	private ArrayList<String> songs;

	boolean onBeat = false;
	int counter = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		levelArray = new ArrayList<>();
		songs = new ArrayList<>();

		initSongs();

		new LevelGenerator(500, 18);
		initLevel();

//		levelSelectViewController = new LevelSelectViewController(levelArray);
//		levelSelectView = levelSelectViewController.getRoot();
		homeScreenController = new HomeScreenController(levelArray);
		homeScreen = homeScreenController.getRoot();

		/**
		 * SCENE + LEVEL Spieler im Level setzten
		 */

		scene = new Scene(homeScreen, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
		homeScreen.requestFocus();
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

		/**
		 * STAGE Stage wird gesetzt
		 */
		primaryStage.setScene(scene);
		primaryStage.setTitle("RythmJump");
		primaryStage.setResizable(false);
		primaryStage.show();

		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});
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
			randomSongIndex = getRandomNumber(0, songs.size() - 1);
			tempLevel = new Level(levelFile, songs.get(randomSongIndex));
			System.out.println(levelFile.getName());
			levelArray.add(tempLevel);
		}
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

	public void initSongs() throws IOException {
		ArrayList<File> songFiles = new ArrayList<>();// findFilesBySuffix(".mp3", ".");
		songFiles.add(findFile("tombtorial.mp3", "."));
		for (File song : songFiles) {
			songs.add(song.getCanonicalPath());
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

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min + 1)) + min);
	}

}
