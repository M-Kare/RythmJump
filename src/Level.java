import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Level extends Pane {
	public final char BOUNDARY = '#';
	public final char WALL = '*';

	public final int SIZE = 25;

	private int[] dimensions;
	private char[][] levelArray;
	private File level;

	private ArrayList<Rectangle> obstacles;

	public Level(File level) {
		super();
		this.level = level;
		this.obstacles = new ArrayList<>();

		this.dimensions = getLevelDimensions(this.level);
		this.levelArray = readLevelFromFile(this.level);

		this.setHeight(dimensions[Dimensions.HEIGHT.getIndex()] * SIZE);
		this.setWidth(dimensions[Dimensions.WIDTH.getIndex()] * SIZE);

		addChildren(levelArray);
		this.getChildren().addAll(obstacles);
	}
	
	public ArrayList<Rectangle> getObstacles(){
		return obstacles;
	}

	private char[][] readLevelFromFile(File level) {
		String line;
		int lineCounter = 0;
		int[] dimensions = getLevelDimensions(level);
		char[][] levelArray = new char[dimensions[Dimensions.HEIGHT.getIndex()]][dimensions[Dimensions.WIDTH
				.getIndex()]];

		try (BufferedReader reader = new BufferedReader(new FileReader(level))) {
			while ((line = reader.readLine()) != null && lineCounter < levelArray.length) {
				if (line.startsWith(Character.toString(BOUNDARY))) {
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

	private int[] getLevelDimensions(File level) {
		int[] dimensions = { 0, 0 };
		String line;
		boolean started = false;

		try (BufferedReader reader = new BufferedReader(new FileReader(level))) {
			line = reader.readLine();
			dimensions[Dimensions.HEIGHT.getIndex()]++;

			for (char c : line.toCharArray()) {
				if (c == BOUNDARY) {
					started = true;
					dimensions[Dimensions.WIDTH.getIndex()]++;
				}
				if (started && c != BOUNDARY) {
					break;
				}
			}

			while ((line = reader.readLine()) != null) {
				dimensions[Dimensions.HEIGHT.getIndex()]++;
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
				case WALL:
					if (levelArray[y][x - 1] == WALL) {
						break;
					}
					Rectangle newRect = new Rectangle(x * SIZE, y * SIZE,
							determineBlockSize(levelArray, y, x, WALL) * SIZE, SIZE);
					newRect.setFill(Color.BLACK);
					obstacles.add(newRect);
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
