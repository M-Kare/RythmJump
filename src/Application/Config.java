package Application;

import java.io.File;
import java.io.IOException;

public class Config {

	public static final char COMMENT = '#';
	public static final char WALL = '*';
	public static final char PLAYER = 'P';
	public static final char WIN = 'W';
	public static final char DEATH = 'D';

	public static final int BLOCK_SIZE = 40;

	public static final int PLAYER_SIZE = 30;
	public static final int PLAYER_SPEED = 8; // 8
	public static final int JUMP_HEIGHT = 19; // 19
	public static final int COYOTE_TIME = 5;
	public static final int MAX_GRAVITY = 15;

	public static final int WINDOW_WIDTH = 1600;
	public static final int WINDOW_HEIGHT = 800;

	public static final String LEVEL_SUFFIX = ".lvl";
	public static final String STD_SONG = "./assets/mp3/tamborin100bpm.mp3";

	public static File findFile(String fileName, String searchDirectory) {
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
					File temp;
					try {
						temp = findFile(fileName, file.getCanonicalPath());
						if (temp != null && temp.getName().equals(fileName)) {
							return temp;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

}
