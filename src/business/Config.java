package business;

import java.io.File;
import java.io.IOException;

/**
 * Beinhaltet Konstanten und einstellbare Werte, sowie hilfreiche Methoden
 */
public class Config {

	/**
	 * Konstanten zum Level einlesen
	 */
	public static final char COMMENT = '#';
	public static final char PLAYER = 'P';
	public static final char WIN = 'W';
	public static final char DEATH = 'D';
	public static final char DEATH_FLIPPED = 'd';
	public static final char STD_WALL = '*';
	public static final char STONE = 's';
	public static final char RED = 'R';
	public static final char ORANGE = 'O';
	public static final char SCHWARZ = 'S';
	public static final char GOLD = 'G';
	public static final char YELLOW = 'Y';
	public static final char GREEN = 'g';
	public static final char BLUE = 'b';
	public static final char DARKBLUE = 'B';
	public static final char LILA = 'L';
	public static final char BROWN = 'K';
	
	
	
	
	public static final String LEVEL_TELEPORT = "teleport";
	public static final String LEVEL_BACKGROUND = "background:";
	public static final String LEVEL_MUSIC = "music:";
	
	public static final String MUSIC_FOLDER = "assets/mp3";
	public static final String BACKGROUNDS_FOLDER = "./assets/textures/backgrounds/";
	public static final String BLOCKS_FOLDER = "./assets/textures/blocks/";
	public static final String SKINS_FOLDER = "./assets/textures/skins/";
	
	public static final String GOAL_TEXTURE = "block-win.png";
	public static final String DEATH_TEXTURE = "hazard-spike.png";
	public static final String DEATH_FLIPPED_TEXTURE = "hazard-spike-flipped.png";
	public static final String BLOCK_DARKBLUE = "block-darkblue.png";
	public static final String BLOCK_BLUE = "block-blue.png";
	public static final String BLOCK_BLACKGOLD = "block-blackgold.png";
	public static final String BLOCK_BROWN = "block-brown.png";
	public static final String BLOCK_BLACK = "block-black.png";
	public static final String BLOCK_GREEN = "block-green.png";
	public static final String BLOCK_RED = "block-red.png";
	public static final String BLOCK_YELLOW = "block-yellow.png";
	public static final String BLOCK_PURPLE = "block-purple.png";
	public static final String BLOCK_ORANGE = "block-orange.png";
	public static final String BLOCK_STONE = "block-stonebrick.png";
	

	public static final int BLOCK_SIZE = 40;

	public static final int WINDOW_WIDTH = 1600;
	public static final int WINDOW_HEIGHT = 800;

	/**
	 * Level-Dateiendung
	 */
	public static final String LEVEL_SUFFIX = ".lvl";
	public static final String STD_SONG = "/assets/mp3/tombtorial.mp3";
	public static final String STD_BACKGROUND = "background-islandshore_enlarged.png";
	public static final String STD_SKIN = "player-trashcan.png";

	/**
	 * Standartwerte für Spieler und "Physik"
	 */
	public static final int PLAYER_SIZE = 30;
	public static final int PLAYER_SPEED = 8; // 8
	public static final int JUMP_HEIGHT = 19; // 19
	public static final int COYOTE_TIME = 5;
	public static final int MAX_GRAVITY = 20;
	public static final int ONBEAT_FRAMES = 6;

	/**
	 * Standartwerte für Modi
	 */
	public static final boolean AUTO_JUMP = false;
	public static final boolean RHYTHM_ENABLED = true;
	
	/**
	 * Einstellbare Werte für Spieler, "Physik" und Modi
	 */
	private static int playerSpeed = PLAYER_SPEED;
	private static int jumpHeight = JUMP_HEIGHT;
	private static int coyote = COYOTE_TIME;
	private static int onBeatFrames = ONBEAT_FRAMES;
	private static boolean autoJump = AUTO_JUMP;
	private static boolean rhythmEnabled = RHYTHM_ENABLED;

	public static int getPlayerSpeed() {
		return playerSpeed;
	}

	public static int getJumpHeight() {
		return jumpHeight;
	}

	public static int getCoyote() {
		return coyote;
	}
	
	public static int getOnBeatFrames() {
		return onBeatFrames;
	}

	public static boolean getAutoJump() {
		return autoJump;
	}

	public static boolean getRhythmEnabled() {
		return rhythmEnabled;
	}

	public static void setPlayerSpeed(int value) {
		playerSpeed = value;
	}

	public static void setJumpHeight(int value) {
		jumpHeight = value;
	}

	public static void setCoyote(int value) {
		coyote = value;
	}
	
	public static void setOnBeatFrames(int value) {
		onBeatFrames = value;
	}

	public static void setAutoJump(boolean value) {
		autoJump = value;
	}

	public static void setRhythmEnabled(boolean value) {
		rhythmEnabled = value;
	}

	/**
	 * Sucht die erste Datei, im angegebenen Pfad, die mit dem Namen übereinstimmt
	 * 
	 * @param fileName        Name der zu suchenden Datei
	 * @param searchDirectory Pfad des Verzeichnisses, in dem gesucht werden soll
	 * @return gefundene Datei
	 * @throws IOException
	 */
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
					File temp = null;
					try {
						temp = findFile(fileName, file.getCanonicalPath());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (temp != null && temp.getName().equals(fileName)) {
						return temp;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Liefert eine zufällige Zahl im angegebenen Bereich
	 * 
	 * @param min untere Grenze
	 * @param max obere Grenze
	 * @return zufalls Zahl
	 */
	public static int getRandomNumber(int min, int max) {
		return (int) ((Math.random() * (max - min + 1)) + min);
	}
}
