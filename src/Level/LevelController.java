package Level;

import java.io.File;
import java.util.HashMap;

import Application.Config;
import Application.Dimensions;
import Player.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.input.KeyCode;

public class LevelController {
	private Level level;
	private char[][] levelArray;
	private int levelLength;
	private int levelHeight;
	private int[] playerSpawn;
	private Player player;

	private HashMap<KeyCode, Boolean> keybindsLevel;

	public LevelController(Level level, Player player) {
		this.level = level;
		this.levelArray = this.level.getLevelArray();
		this.levelLength = this.level.getLevelLength();
		this.levelHeight = this.level.getLevelHeight();
		this.playerSpawn = this.level.getPlayerSpawn();

		this.player = player;
		keybindsLevel = new HashMap<>();
		

		init();
	}

	public LevelController(File level, Player player) {
		this(new Level(level), player);
	}

	public LevelController(char[][] levelArray, Player player) {
		this(new Level(levelArray), player);
	}
	
	public HashMap<KeyCode, Boolean> getKeybinds(){
		return keybindsLevel;
	}
	
	public Level getLevel() {
		return level;
	}

	public void init() {
		/**
		 * KEYBINDS
		 */
		keybindsLevel.put(KeyCode.R, false);
		
		/**
		 * SET_LAYOUT
		 * Damit die "Kamera" dem Spieler folgt
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosX = newValue.intValue();
			if (playerPosX > Config.WINDOW_WIDTH / 3 && playerPosX < level.getLevelLength() - Config.WINDOW_WIDTH / 100 * 66) {
				level.setLayoutX(-(playerPosX - Config.WINDOW_WIDTH / 3));
			}
		});
		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosY = newValue.intValue();
			if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (Config.WINDOW_HEIGHT / 100 * 55)) {
				level.setLayoutY(-(playerPosY - (Config.WINDOW_HEIGHT / 2)));
			}
		});
		
		/**
		 * TIMER
		 * Timer zum abhören der Keybinds
		 */
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (keybindsLevel.get(KeyCode.R)) {
					player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
					player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
					level.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (Config.BLOCK_SIZE * 2)));
					level.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
				}
			}
		}; timer.start();
	}
}
