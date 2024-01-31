package business;

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
	public static final int MAX_GRAVITY = 20;
	
	public static final boolean AUTO_JUMP = false;
	public static final boolean RHYTHM_ENABLED = true;

	public static final int WINDOW_WIDTH = 1600;
	public static final int WINDOW_HEIGHT = 800;

	public static final String LEVEL_SUFFIX = ".lvl";
	public static final String STD_SONG = "./assets/mp3/tamborin100bpm.mp3";
	
	private static int playerSpeed = PLAYER_SPEED;
	private static int jumpHeight = JUMP_HEIGHT;
	private static int coyote = COYOTE_TIME;
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
	
	public static void setAutoJump(boolean value) {
		autoJump = value;
	}
	
	public static void setRhythmEnabled(boolean value) {
		rhythmEnabled = value;
	}
}
