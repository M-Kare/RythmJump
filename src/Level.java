import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Level extends Pane {
	private int[] dimensions;
	private char[][] levelArray;
	private File level;
	private int levelLength;
	private int levelHeight;
	private int[] playerSpawn;

	private ArrayList<Node> obstacles;

	public Level(File level) {
		super();
		this.level = level;
		this.obstacles = new ArrayList<>();

		this.dimensions = getLevelDimensions(this.level);
		this.levelArray = readLevelFromFile(this.level);

		levelLength = dimensions[Dimensions.X.getIndex()] * Config.BLOCK_SIZE;
		levelHeight = dimensions[Dimensions.Y.getIndex()] * Config.BLOCK_SIZE;

		playerSpawn = new int[2];
		
		addChildren(levelArray);
		this.getChildren().addAll(obstacles);
	}

	public ArrayList<Node> getObstacles() {
		return obstacles;
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

	private char[][] readLevelFromFile(File level) {
		String line;
		int lineCounter = 0;
		int[] dimensions = getLevelDimensions(level);
		char[][] levelArray = new char[dimensions[Dimensions.Y.getIndex()]][dimensions[Dimensions.X
				.getIndex()]];

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
			line = reader.readLine();
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
					playerSpawn[Dimensions.Y.getIndex()] = (y-2) * Config.BLOCK_SIZE;
					break;
				}
			}
		}
	}

	public int determineBlockSize(char[][] levelArray, int currentLine, int currentChar, char symbol) {
		int counter = 0;
		while (levelArray[currentLine][currentChar + counter] == symbol) {
			counter++;
		}
		return counter;
	}

}
