package business.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import business.Config;
import business.Dimensions;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;

/**
 * Level werden aus txt-Dateien (.lvl) eingelesen und zusammengebaut und
 * verarbeitet
 */
public class Level extends Pane {
	private int[] dimensions;
	private char[][] levelArray;
	private File levelFile;
	private int levelLength;
	private int levelHeight;
	private int[] playerSpawn;
	private String levelName;

	private Image thumbnail;

	private ArrayList<Node> obstacles;
	private ArrayList<Node> winArea;
	private ArrayList<Node> deathArea;

	private String songPath;
	private String background;
	private boolean allowTeleport = false;

	/**
	 * Bekommt eine LevelFile und einen Song und setzt daraus das Level zusammen
	 * 
	 * @param level    Level aus Datei einlesen
	 * @param songPath Pfad zum zugehörigen Song
	 */
	public Level(File level, String songPath) {
		super();
		this.levelFile = level;
		levelName = level.getName().split(".lvl")[0];

		this.obstacles = new ArrayList<>();
		this.winArea = new ArrayList<>();
		this.deathArea = new ArrayList<>();

		this.dimensions = getLevelDimensions(levelFile);
		this.levelArray = readLevelFromFile(levelFile);

		levelLength = dimensions[Dimensions.X.getIndex()] * Config.BLOCK_SIZE;
		levelHeight = dimensions[Dimensions.Y.getIndex()] * Config.BLOCK_SIZE;

		playerSpawn = new int[2];

		try {
			addChildren(levelArray);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (this.songPath == null) {
			this.songPath = songPath;
		}

		this.getChildren().addAll(obstacles);
		this.getChildren().addAll(winArea);
		this.getChildren().addAll(deathArea);
		this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

		takeThumbnail();
	}

	/**
	 * Zweiter Konstruktor ohne extra Song angabe (nutzt Standart-Song)
	 * 
	 * @param level einzulesende Level-Datei
	 */
	public Level(File level) {
		this(level, Config.STD_SONG);
	}

	/**
	 * Getter für den Song-Pfad
	 * 
	 * @return Song-Pfad
	 */
	public String getSong() {
		return songPath;
	}

	/**
	 * Getter für den Background-Pfad wenn dieser in der Level-File angegeben ist
	 * 
	 * @return Background-Pfad
	 */
	public String getBackgroundPath() {
		return background;
	}

	/**
	 * Ob Teleport am Levelrand erlaubt ist
	 * 
	 * @return allowTeleport
	 */
	public boolean getAllowTeleport() {
		return allowTeleport;
	}

	/**
	 * Getter für alle Nodes, die Kollision mit dem Spieler haben
	 * 
	 * @return ArrayList der Kollisions-Blöcke
	 */
	public ArrayList<Node> getObstacles() {
		return obstacles;
	}

	/**
	 * Getter für die Nodes, die als Sieg / Levelende zählen
	 * 
	 * @return ArrayListe aller Sieg-Blöcke
	 */
	public ArrayList<Node> getWinArea() {
		return winArea;
	}

	/**
	 * Getter für die Nodes, die als Todeszone gelten
	 * 
	 * @return ArrayList aller Todes-Blöcke
	 */
	public ArrayList<Node> getDeathArea() {
		return deathArea;
	}

	/**
	 * Getter für die File, die zum Levelausbau genutzt wurde
	 * 
	 * @return LevelFile
	 */
	public File getFile() {
		return levelFile;
	}

	/**
	 * Getter für das Thumbnail des Levels (Level-Vorschau)
	 * 
	 * @return Level-Vorschau
	 */
	public Image getThumbnail() {
		return thumbnail;
	}

	/**
	 * Getter für den Namen des Levels (der Level-Datei)
	 * 
	 * @return Name des Levels
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * Getter für das aus der Level-Datei erzeugte Character-Array
	 * 
	 * @return level char-Array
	 */
	public char[][] getLevelArray() {
		return levelArray;
	}

	/**
	 * Getter für die horizontale Länge des Levels
	 * 
	 * @return horizontale Länge
	 */
	public int getLevelLength() {
		return levelLength;
	}

	/**
	 * Getter für die Höhe des Levels
	 * 
	 * @return Höhe des Levels
	 */
	public int getLevelHeight() {
		return levelHeight;
	}

	/**
	 * Getter für die Spawn-Koordinaten des Spielers
	 * 
	 * @return Spawn-Koordinaten
	 */
	public int[] getPlayerSpawn() {
		return playerSpawn;
	}

	/**
	 * Erzeugt einen Screenshot / Snapshot des Levels für die Level-Vorschau
	 */
	public void takeThumbnail() {
		double vpHeight = 400, vpWidth = 400;
		SnapshotParameters sp = new SnapshotParameters();
		sp.setTransform(Transform.scale(0.31, 0.31));
		sp.setFill(Color.TRANSPARENT);
		if (levelHeight < (400 / 0.31))
			vpHeight = levelHeight * 0.31;
		if (levelLength < (400 / 0.31))
			vpWidth = levelLength * 0.31;

		sp.setViewport(new Rectangle2D(0, 0, vpWidth, vpHeight));
		thumbnail = snapshot(sp, null);
	}

	/**
	 * Geht die Level-Datei Zeile für Zeile durch und erzeugt daraus ein Char-Array
	 * 
	 * @param level Level-Datei
	 * @return Level als Char-Array übersetzt
	 */
	private char[][] readLevelFromFile(File level) {
		String line;
		int lineCounter = 0;
		int[] dimensions = getLevelDimensions(level);
		char[][] levelArray = new char[dimensions[Dimensions.Y.getIndex()]][dimensions[Dimensions.X.getIndex()]];

		try (BufferedReader reader = new BufferedReader(new FileReader(level))) {
			while ((line = reader.readLine()) != null && lineCounter < levelArray.length) {
				if (line.contains(Character.toString(Config.COMMENT))) {
					if (line.contains(Config.LEVEL_TELEPORT)) {
						allowTeleport = true;
					} else if (line.contains(Config.LEVEL_BACKGROUND)) {
						String[] temp = line.split(":");
						background = temp[1];
					} else if (line.contains(Config.LEVEL_MUSIC)) {
						String[] temp = line.split(":");
						songPath = Config.findFile(temp[1], Config.MUSIC_FOLDER).toURI().toURL().toExternalForm();
					}
				} else {
					levelArray[lineCounter++] = toSimpleCharArray(line.split(""));
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return levelArray;
	}

	/**
	 * Prüft die Level-Datei auf die Größe des Levels, um die benötigte Größe für
	 * das LevelArray zu bestimmen
	 * 
	 * @param level Level-Datei
	 * @return Dimensionen des Levels
	 */
	private int[] getLevelDimensions(File level) {
		int[] dimensions = { 0, 0 };
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(level))) {
			if ((line = reader.readLine()) == null)
				return dimensions;

			while (line.contains(Character.toString(Config.COMMENT)))
				line = reader.readLine();

			dimensions[Dimensions.Y.getIndex()]++;

			for (char c : line.toCharArray()) {
				dimensions[Dimensions.X.getIndex()]++;
			}

			while ((line = reader.readLine()) != null) {
				if (!line.contains(Character.toString(Config.COMMENT))) {
					dimensions[Dimensions.Y.getIndex()]++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return dimensions;
	}

	/**
	 * Wandelt einen String-Array in ein Character-Array um
	 * 
	 * @param input String-Array (Wörter-Array)
	 * @return Char-Array der Wörter
	 */
	private char[] toSimpleCharArray(String[] input) {
		char[] output = new char[input.length];
		int i = 0;

		for (String word : input) {
			output[i] = word.toCharArray()[0];
			i++;
		}
		return output;
	}

	/**
	 * Geht das LevelArray durch und fügt, je nach Character einen entsprechenden
	 * Node in die zutreffende Arraylist und setzt deren Position
	 * 
	 * @param levelArray Char-Array des Levels
	 * @throws MalformedURLException
	 */
	private void addChildren(char[][] levelArray) throws MalformedURLException {
		String wallTexture = null, winTexture = null, deathTexture = null;

		for (int y = 0; y < levelArray.length; y++) {
			for (int x = 0; x < levelArray[y].length; x++) {
				switch (levelArray[y][x]) {
				case Config.WALL:
					if (wallTexture == null) {
						wallTexture = Config.findFile("block-blackgold.png", "./assets/textures/blocks").toURI().toURL()
								.toExternalForm();
					}
					Node newWall = new ImageView(new Image(wallTexture));
					newWall.setTranslateX(x * Config.BLOCK_SIZE);
					newWall.setTranslateY(y * Config.BLOCK_SIZE);
					obstacles.add(newWall);
					break;
				case Config.PLAYER:
					playerSpawn[Dimensions.X.getIndex()] = x * Config.BLOCK_SIZE;
					playerSpawn[Dimensions.Y.getIndex()] = (y - 2) * Config.BLOCK_SIZE;
					break;
				case Config.WIN:
					if (winTexture == null) {
						winTexture = Config.findFile("block-win.png", "./assets/textures/blocks")
								.toURI().toURL().toExternalForm();
					}
					Node newWin = new ImageView(new Image(winTexture));
					newWin.setTranslateX(x * Config.BLOCK_SIZE);
					newWin.setTranslateY(y * Config.BLOCK_SIZE);
					winArea.add(newWin);
					break;
				case Config.DEATH:
					if (deathTexture == null) {
						deathTexture = Config.findFile("hazard-spike.png", "./assets/textures/blocks").toURI().toURL()
								.toExternalForm();
					}
					Node newDeath = new ImageView(
							new Image(deathTexture));
					newDeath.setTranslateX(x * Config.BLOCK_SIZE);
					newDeath.setTranslateY(y * Config.BLOCK_SIZE);
					deathArea.add(newDeath);
					break;
				}
			}
		}
	}
}
