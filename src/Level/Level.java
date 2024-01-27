package Level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Application.Config;
import Application.Dimensions;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Transform;

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

	public Level(File level) {
		super();
		this.levelFile = level;
		this.obstacles = new ArrayList<>();
		this.winArea = new ArrayList<>();
		levelName = level.getName().split(".lvl")[0];

		this.dimensions = getLevelDimensions(levelFile);
		this.levelArray = readLevelFromFile(levelFile);

		levelLength = dimensions[Dimensions.X.getIndex()] * Config.BLOCK_SIZE;
		levelHeight = dimensions[Dimensions.Y.getIndex()] * Config.BLOCK_SIZE;

		playerSpawn = new int[2];

		addChildren(levelArray);
		this.getChildren().addAll(obstacles);
		this.getChildren().addAll(winArea);

		takeThumbnail();
	}

	public Level(char[][] levelArray, String levelName) {
		super();
		this.dimensions = new int[2];
		this.obstacles = new ArrayList<>();
		this.winArea = new ArrayList<>();
		this.levelName = levelName;

		this.dimensions[Dimensions.X.getIndex()] = levelArray[0].length;
		this.dimensions[Dimensions.Y.getIndex()] = levelArray.length;

		this.levelArray = levelArray;

		levelLength = dimensions[Dimensions.X.getIndex()] * Config.BLOCK_SIZE;
		levelHeight = dimensions[Dimensions.Y.getIndex()] * Config.BLOCK_SIZE;

		playerSpawn = new int[2];

		addChildren(this.levelArray);
		this.getChildren().addAll(obstacles);
		this.getChildren().addAll(winArea);

		takeThumbnail();
	}
	
	public ArrayList<Node> getWinArea(){
		return winArea;
	}
	
	public File getFile() {
		return levelFile;
	}

	public Image getThumbnail() {
		return thumbnail;
	}

	public String getLevelName() {
		return levelName;
	}

	public ArrayList<Node> getObstacles() {
		return obstacles;
	}

	public char[][] getLevelArray() {
		return levelArray;
	}

	public int getLevelLength() {
		return levelLength;
	}

	public int getLevelHeight() {
		return levelHeight;
	}

	public int[] getPlayerSpawn() {
		return playerSpawn;
	}

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
		thumbnail = this.snapshot(sp, null);
	}

	private char[][] readLevelFromFile(File level) {
		String line;
		int lineCounter = 0;
		int[] dimensions = getLevelDimensions(level);
		char[][] levelArray = new char[dimensions[Dimensions.Y.getIndex()]][dimensions[Dimensions.X.getIndex()]];

		try (BufferedReader reader = new BufferedReader(new FileReader(level))) {
			while ((line = reader.readLine()) != null && lineCounter < levelArray.length) {
				if (!line.contains(Character.toString(Config.COMMENT))) {
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
	 * 
	 * @param level
	 * @return
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

	private char[] toSimpleCharArray(String[] input) {
		char[] output = new char[input.length];
		int i = 0;

		for (String word : input) {
			output[i] = word.toCharArray()[0];
			i++;
		}
		return output;
	}

	private void addChildren(char[][] levelArray) {
		for (int y = 0; y < levelArray.length; y++) {
			for (int x = 0; x < levelArray[y].length; x++) {
				switch (levelArray[y][x]) {
				case Config.WALL:
					Node newWall = new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.BLACK);
					newWall.setTranslateX(x * Config.BLOCK_SIZE);
					newWall.setTranslateY(y * Config.BLOCK_SIZE);
					obstacles.add(newWall);
					break;
				case Config.PLAYER:
					playerSpawn[Dimensions.X.getIndex()] = x * Config.BLOCK_SIZE;
					playerSpawn[Dimensions.Y.getIndex()] = (y - 2) * Config.BLOCK_SIZE;
					break;
				case Config.WIN:
					Node newWin = new Rectangle(Config.BLOCK_SIZE, Config.BLOCK_SIZE, Color.GOLD);
					newWin.setTranslateX(x * Config.BLOCK_SIZE);
					newWin.setTranslateY(y * Config.BLOCK_SIZE);
					winArea.add(newWin);
					break;
				}
			}
		}
	}

	private void removeChildren() {
		this.getChildren().clear();
	}

	public int determineBlockSize(char[][] levelArray, int currentLine, int currentChar, char symbol) {
		int counter = 0;
		while (levelArray[currentLine][currentChar + counter] == symbol) {
			counter++;
		}
		return counter;
	}

}
