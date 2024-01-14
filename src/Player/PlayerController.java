package Player;

import java.util.ArrayList;
import java.util.HashMap;

import Application.Config;
import Application.Dimensions;
import Level.Level;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class PlayerController {

	private Player player;
	private Node skin;

	private Level level;
	private ArrayList<Node> obstacles;

	private HashMap<KeyCode, Boolean> keybindsPlayer;

	public PlayerController(Level level) {
		player = new Player();
		skin = player.getSkin();

		this.level = level;
		this.obstacles = level.getObstacles();
		keybindsPlayer = new HashMap<>();

		init();
	}
	
//	public PlayerController(ArrayList<Node> obstacles) {
//		player = new Player();
//		skin = player.getSkin();
//
//		this.obstacles = obstacles;
//		keybindsPlayer = new HashMap<>();
//
//		init();
//	}
//	
//	public PlayerController() {
//		this(new ArrayList<>());
//	}

	public void setObstacles(ArrayList<Node> obstacles) {
		this.obstacles = obstacles;
	}

	public void addObstacles(ArrayList<Node> obstacles) {
		this.obstacles.addAll(obstacles);
	}

	public Player getPlayer() {
		return player;
	}

	public HashMap<KeyCode, Boolean> getKeybinds() {
		return keybindsPlayer;
	}

	public void init() {
		/**
		 * KEYBINDS Verwendete Knöpfe
		 */
		keybindsPlayer.put(KeyCode.UP, false);
		keybindsPlayer.put(KeyCode.W, false);
		keybindsPlayer.put(KeyCode.DOWN, false);
		keybindsPlayer.put(KeyCode.S, false);
		keybindsPlayer.put(KeyCode.RIGHT, false);
		keybindsPlayer.put(KeyCode.D, false);
		keybindsPlayer.put(KeyCode.LEFT, false);
		keybindsPlayer.put(KeyCode.A, false);
		keybindsPlayer.put(KeyCode.SPACE, false);
		keybindsPlayer.put(KeyCode.R, false);

		/**
		 * Stick Skin to Player
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			skin.setTranslateX(newValue.doubleValue() - 1);
		});

		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			skin.setTranslateY(newValue.doubleValue() - 1);
		});
		
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
		 * ANIMATION_TIMER Animation-Timer für flüssige Bewegung
		 */
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				/**
				 * JUMPING
				 */
				if (keybindsPlayer.get(KeyCode.SPACE) || keybindsPlayer.get(KeyCode.W)
						|| keybindsPlayer.get(KeyCode.UP)) {
					jump();
				}
				
				/**
				 * RESPAWN
				 */
				if (keybindsPlayer.get(KeyCode.R)) {
					player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
					player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
					level.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (Config.BLOCK_SIZE * 2)));
					level.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
				}

				/**
				 * MOVING X Seitliches Bewegen
				 */
				if ((keybindsPlayer.get(KeyCode.RIGHT) || keybindsPlayer.get(KeyCode.D))
				/*
				 * && player.getTranslateX() + Config.PLAYER_SIZE <= level.getLevelLength() - 5
				 */) {
					if(player.getTranslateX() + Config.PLAYER_SIZE >= level.getLevelLength() - Config.BLOCK_SIZE - 1) {
						player.setTranslateX(Config.BLOCK_SIZE + 2);
					}
					movePlayerX(Config.PLAYER_SPEED);
				}
				if ((keybindsPlayer.get(KeyCode.LEFT)
						|| keybindsPlayer.get(KeyCode.A))/* && player.getTranslateX() >= 5 */) {
					if(player.getTranslateX() <= Config.BLOCK_SIZE+1) {
						player.setTranslateX(level.getLevelLength() - Config.BLOCK_SIZE - 2 - Config.PLAYER_SIZE);
					}
					movePlayerX(-(Config.PLAYER_SPEED));
				}

				/**
				 * GRAVITY Schwerkraft
				 */
				if (player.getVelocity() < 5) {
					player.addVelocity(0.75);
				} else if (player.getVelocity() < 15) {
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
		if (player.getJumpable()) {
			player.setVelocity(-Config.JUMP_HEIGHT);
			player.setJumpable(false);
		}
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
						if (player.getTranslateX() + Config.PLAYER_SIZE == obstacle.getTranslateX()) {
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

		for (int i = 0; i < Math.abs(value); i++) {
			for (Node obstacle : obstacles) {
				if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())
				/*
				 * || player.getTranslateY() + (Config.PLAYER_SIZE * 2) >=
				 * level.getLevelHeight() - 5
				 */) {
					if (movingDown) {
						if (player.getTranslateY() + (Config.PLAYER_SIZE * 2) == obstacle.getTranslateY()) {
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
			player.setJumpable(false);
		}
	}

}
