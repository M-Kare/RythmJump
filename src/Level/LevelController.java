package Level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Application.Config;
import Application.Dimensions;
import Player.Player;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class LevelController {

	private Level level;

	private ArrayList<Node> winArea;
	private ArrayList<Node> obstacles;

	private Player player;

	private HashMap<KeyCode, Boolean> levelKeybinds;

	public LevelController(HashMap<KeyCode, Boolean> keybinds, Player player, File level) {
		this.levelKeybinds = keybinds;

		this.level = new Level(level);

		winArea = new ArrayList<>(this.level.getWinArea());
		obstacles = new ArrayList<>(this.level.getObstacles());

		this.player = player;
//		this.level.getChildren().add(player);

		init();
	}

	public LevelController(HashMap<KeyCode, Boolean> keybinds, Player player, char[][] levelArray, String levelName) {
		this.levelKeybinds = keybinds;

		this.level = new Level(levelArray, levelName);

		winArea = new ArrayList<>(this.level.getWinArea());
		obstacles = new ArrayList<>(this.level.getObstacles());

		this.player = player;

		init();
	}

	public Level getRoot() {
		return level;
	}
	
	public HashMap<KeyCode, Boolean> getKeybinds(){
		return levelKeybinds;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void resetPlayer() {
		if(!level.getChildren().contains(player))
			level.getChildren().add(player);
//		level.getChildren().add(player);
		level.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
	}

	public void init() {
		/**
		 * SET_LAYOUT Damit die "Kamera" dem Spieler folgt
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosX = newValue.intValue();
			if (playerPosX > Config.WINDOW_WIDTH / 3
					&& playerPosX < level.getLevelLength() - Config.WINDOW_WIDTH / 100 * 66) {
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
		 * ANIMATION_TIMER Animation-Timer für flüssige Bewegung
		 */
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
//				beat.detect(audioPlayer.mix);
//				if (beat.isOnset()) {
//					player.setFill(Color.GREEN);
//					onBeat = true;
////					System.out.println(onBeat);
////					jump();
//				}
//				if (onBeat) {
//					counter++;
//				}
//				if (counter > 12) {
//					player.setFill(Color.RED);
//					onBeat = false;
//					counter = 0;
//					System.out.println(onBeat);
//				}

				for (Node win : winArea) {
					if (player.getBoundsInParent().intersects(win.getBoundsInParent())) {
						System.out.println(level.getLevelName());
//						level.getScene().setRoot(null);
					}
				}

				/**
				 * JUMPING
				 */
				if (levelKeybinds.get(KeyCode.SPACE) || levelKeybinds.get(KeyCode.W) || levelKeybinds.get(KeyCode.UP)) {
					jump();
				}

				/**
				 * RESPAWN
				 */
				if (levelKeybinds.get(KeyCode.R)) {
					player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
					player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
					level.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (Config.BLOCK_SIZE * 2)));
					level.setLayoutY(
							-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
				}

				/**
				 * MOVING X Seitliches Bewegen
				 */
				if ((levelKeybinds.get(KeyCode.RIGHT) || levelKeybinds.get(KeyCode.D))
				/*
				 * && player.getTranslateX() + Config.PLAYER_SIZE <= level.getLevelLength() - 5
				 */) {
					if (player.getTranslateX() + player.getWidth() >= level.getLevelLength() - Config.BLOCK_SIZE - 1) {
						player.setTranslateX(Config.BLOCK_SIZE + 2);
					}
					movePlayerX(Config.PLAYER_SPEED);
				}
				if ((levelKeybinds.get(KeyCode.LEFT) || levelKeybinds.get(KeyCode.A))/* && player.getTranslateX() >= 5 */) {
					if (player.getTranslateX() <= Config.BLOCK_SIZE + 1) {
						player.setTranslateX(level.getLevelLength() - Config.BLOCK_SIZE - 2 - player.getWidth());
					}
					movePlayerX(-(Config.PLAYER_SPEED));
				}

				/**
				 * GRAVITY Schwerkraft
				 */
				if (player.getVelocity() < 5) {
					player.addVelocity(0.75);
				} else if (player.getVelocity() < Config.MAX_GRAVITY) {
					player.addVelocity(1);
				}
				if (player.getTranslateY() + (Config.PLAYER_SIZE * 2) <= level.getLevelHeight() - 5) {
					movePlayerY((int) player.getVelocity());
				}
			}
		};
		timer.start();
	}

	/**
	 * JUMP Springen Methode
	 */
	public void jump() {
		if (player.getJumpable() /* && onBeat */) {
			player.setVelocity(-Config.JUMP_HEIGHT);
			player.setJumpable(false);
		}
//		} else if (player.getJumpable()) {
//			player.setVelocity(-Config.JUMP_HEIGHT / 5);
//			player.setJumpable(false);
//		}
	}

	/**
	 * MOVE_X Bewegen in X-Richtung. Wenn
	 * 
	 * @param value Wert um den bewegt werden soll
	 */
	public void movePlayerX(int value) {
		boolean movingRight = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Node obstacle : obstacles) {
				if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
					if (movingRight) {
						if (player.getTranslateX() + player.getWidth() == obstacle.getTranslateX()) {
							player.setTranslateX(player.getTranslateX() - 1);
							return;
						}

					} else {
						if (player.getTranslateX() == obstacle.getTranslateX() + Config.BLOCK_SIZE) {
							player.setTranslateX(player.getTranslateX() + 1);
							return;
						}
					}
				}
			}
			player.setTranslateX(player.getTranslateX() + (movingRight ? 1 : -1));
		}
	}

	/**
	 * MOVE_Y Bewegung in Y-Richtung
	 * 
	 * @param value Wert um den bewegt werden soll
	 */
	public void movePlayerY(int value) {
		boolean movingDown = value > 0;
		int counter = 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Node obstacle : obstacles) {
				if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())
				/*
				 * || player.getTranslateY() + player.getHeight() >= level.getLevelHeight() - 5
				 */) {
					if (movingDown) {
						if (player.getTranslateY() + (player.getHeight()) == obstacle.getTranslateY()) {
							player.setTranslateY(player.getTranslateY() - 1);
							player.setVelocity(1.25); // "laggy" wenn unter 1.25 da wert nicht konstant bleibt
							player.setJumpable(true);
							return;
						}
					} else {
						if (player.getTranslateY() == obstacle.getTranslateY() + Config.BLOCK_SIZE) {
							player.setTranslateY(player.getTranslateY() + 1);
							player.addVelocity(2);
							return;
						}
					}
				}
			}
			player.setTranslateY(player.getTranslateY() + (movingDown ? 1 : -1));
			if (counter >= Config.COYOTE_TIME)
				player.setJumpable(false);
			counter++;
		}
	}
}
