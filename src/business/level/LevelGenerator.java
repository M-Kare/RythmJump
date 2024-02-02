package business.level;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import business.Config;

/**
 * Generiert ein zufälliges Level mit den beim Erzeugen angegebenen Maßen
 * (Designt für vertikale Level mit teleport) und speicher es als .lvl-Datei
 */
public class LevelGenerator {
	private final int MAX_IN_ROW = 4;

	private char[][] levelArray;
	private File levelFile;

	/**
	 * Generiert das Level und schreibt es in die "randomLevel.lvl"-Datei
	 * 
	 * @param height Höhe des Levels
	 * @param width  Breite des Levels
	 */
	public LevelGenerator(int height, int width) {
		levelArray = new char[height][width];

		createRandomPlatforms();
		addSideWalls();

		levelFile = writeFile(levelArray);
	}

	/**
	 * Getter für die Level-Datei
	 * 
	 * @return Level-Datei
	 */
	public File getLevelFile() {
		return levelFile;
	}

	/**
	 * Getter für das Level Char-Array
	 * 
	 * @return levelArray
	 */
	public char[][] getLevelArray() {
		return levelArray;
	}

	/**
	 * Fügt Wände an den Rand des Levels hinzu
	 */
	public void addSideWalls() {
		for (int i = 0; i < levelArray.length; i++) {
			levelArray[i][0] = Config.STD_WALL;
			levelArray[i][levelArray[i].length - 1] = Config.STD_WALL;
		}
	}

	/**
	 * Schreibt das Level-Array sowie den Teleport-Flag in die Datei:
	 * randomLevel.lvl
	 * 
	 * @param levelArray
	 * @return Level-Datei
	 */
	public File writeFile(char[][] levelArray) {
		FileWriter writer = null;
		File levelFile = new File("./assets/level/randomLevel.lvl");
		try {
			writer = new FileWriter(levelFile);
			writer.write("#" + Config.LEVEL_TELEPORT + "\n");
			for (int y = 0; y < levelArray.length; y++) {
//				for (int x = 0; x < levelArray[0].length; x++) {
				writer.write(levelArray[y]);
				writer.write("\n");
//				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return levelFile;
	}

	/**
	 * Erzeugt Platformen zufälliger länge an einigermaßen zufälligen Positionen
	 */
	public void createRandomPlatforms() {
		int areaSize, sektor;
		int platformLength = 0;
		int max, min;
		int position = levelArray[0].length / 2;
		for (int i = 0; i < levelArray[0].length; i++) {
			levelArray[levelArray.length - 2][i] = Config.STD_WALL;
		}
		for (int i = 0; i < levelArray[0].length; i++) {
			levelArray[0][i] = Config.WIN;
			levelArray[1][i] = Config.WIN;
		}
		levelArray[levelArray.length - 4][2] = Config.PLAYER;
		areaSize = (levelArray[0].length) / MAX_IN_ROW;

		for (int y = Config.getRandomNumber(levelArray.length - 6, levelArray.length - 5); y >= 0; y -= Config
				.getRandomNumber(3, 4)) {
			// get platform length
			if (platformLength <= 0) {
				platformLength = Config.getRandomNumber(1, areaSize - 1);
			} else {
				if (platformLength == areaSize) {
					max = platformLength;
				} else {
					max = platformLength + 1;
				}
				if (platformLength == 1) {
					min = platformLength;
				} else {
					min = platformLength - 1;
				}
				platformLength = Config.getRandomNumber(min, max);
			}

			// get platform position
			sektor = Config.getRandomNumber(1, MAX_IN_ROW);
			position = Config.getRandomNumber(1, areaSize - platformLength);

			for (int i = 0; i < platformLength; i++) {
				levelArray[y][(areaSize * (sektor - 1)) + position + i] = Config.STD_WALL;
			}
		}
	}
}
