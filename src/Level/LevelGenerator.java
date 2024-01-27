package Level;

import Application.Config;

public class LevelGenerator {
	private final int MAX_IN_ROW = 4;

	private char[][] levelArray;

	public LevelGenerator(int height, int width) {
		levelArray = new char[height][width];

		createRandomPlatforms();
		addSideWalls();
	}

	public char[][] getLevelArray() {
		return levelArray;
	}

	public void addSideWalls() {
		for (int i = 0; i < levelArray.length; i++) {
			levelArray[i][0] = Config.WALL;
			levelArray[i][levelArray[i].length - 1] = Config.WALL;
		}
	}

	public void createRandomPlatforms() {
		int areaSize, sektor, oldPos;
		int platformLength = 0;
		int max, min;
		int position = levelArray[0].length / 2;
		for (int i = 0; i < levelArray[0].length; i++) {
			levelArray[levelArray.length - 2][i] = Config.WALL;
		}
		for (int i = 0; i < levelArray[0].length; i++) {
			levelArray[0][i] = Config.WIN;
			levelArray[1][i] = Config.WIN;
		}
		levelArray[levelArray.length - 4][2] = Config.PLAYER;
		areaSize = (levelArray[0].length) / MAX_IN_ROW;

		for (int y = getRandomNumber(levelArray.length - 6, levelArray.length - 5); y >= 0; y -= getRandomNumber(3,
				4)) {
			// get platform number in row
//			wallsInLine = getRandomNumber(1, MAX_IN_ROW);

//			for(int p = 1; p <= wallsInLine; p++) {
			// get platform length
			if (platformLength <= 0) {
				platformLength = getRandomNumber(1, areaSize - 1); // (int)(((levelArray[y].length-1-4) / wallsInLine))
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
				platformLength = getRandomNumber(min, max);
			}

			// get platform position
			sektor = getRandomNumber(1, MAX_IN_ROW);
			position = getRandomNumber(1, areaSize - platformLength);

			for (int i = 0; i < platformLength; i++) {
				levelArray[y][(areaSize * (sektor - 1)) + position + i] = Config.WALL;
			}
//			}
		}
	}

	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min + 1)) + min);
	}

}
