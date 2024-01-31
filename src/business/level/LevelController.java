package business.level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import business.Config;
import business.Dimensions;
import business.player.Player;
import ddf.minim.AudioPlayer;
import ddf.minim.analysis.BeatDetect;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import presentation.deathView.DeathViewController;
import presentation.endview.TheEnd;
import presentation.endview.TheEndController;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;
import presentation.playView.PlayView;
import presentation.settingsView.SettingsViewController;

public class LevelController {
	private Level level;
	private PlayView playView;

	private ArrayList<Node> winArea;
	private ArrayList<Node> obstacles;
	private ArrayList<Node> deathArea;

	private BeatControlls music;

	private int jumpCount = 0;
	private int missedJumpCount = 0;
	private int deathCount = 0;

	private Player player;

	private SimpleBooleanProperty won;
	private SimpleBooleanProperty dieded;

	private AnimationTimer timer;

	private HashMap<KeyCode, Boolean> keybinds;

	public LevelController(File level, String songPath, PlayView playView) {
		this.playView = playView;

		this.keybinds = new HashMap<>();
		this.level = new Level(level, songPath);

		winArea = new ArrayList<>(this.level.getWinArea());
		obstacles = new ArrayList<>(this.level.getObstacles());
		deathArea = new ArrayList<>(this.level.getDeathArea());

//		speed = settingsController.getSpeedValue();
//		coyote = settingsController.getCoyoteValue();
//		jumpHeight = settingsController.getJumpHeightValue();
//		autoJumpEnabled = settingsController.getAutoJump();
//		rhythmEnabled = settingsController.getRhythmEnabled();

		music = new BeatControlls(songPath, this);
		if(Config.getRhythmEnabled()) {
			this.level.getChildren().add(music);			
		}

		this.player = new Player();

		won = new SimpleBooleanProperty(false);
		dieded = new SimpleBooleanProperty(false);

		init();
	}

	public LevelController(Level level, PlayView playView) {
		this(level.getFile(), level.getSong(), playView);
	}

	public Level getRoot() {
		return level;
	}

	public int getJumpCount() {
		return jumpCount;
	}

	public int getBeatCount() {
		return music.getBeatCount();
	}

	public int getMissedJumpCount() {
		return missedJumpCount;
	}

	public int getDeathCount() {
		return deathCount;
	}

	public HashMap<KeyCode, Boolean> getKeybinds() {
		return keybinds;
	}

	public SimpleBooleanProperty getDieded() {
		return dieded;
	}

	public SimpleBooleanProperty getWon() {
		return won;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void resetPlayer() {
		if (!level.getChildren().contains(player))
			level.getChildren().add(player);
		playView.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));

		if (level.getPlayerSpawn()[Dimensions.X.getIndex()] > Config.WINDOW_WIDTH / 3) {
			playView.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (Config.WINDOW_WIDTH / 100 * 13)));
		} else {
			playView.setLayoutX(0);
		}
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
		dieded.set(false);
	}

	public void resetKeys() {
		keybinds.forEach((key, value) -> {
			value = false;
		});
	}

	public void stopMusic() {
		music.stopMusic();
		timer.stop();
	}

	public void playMusic() {
		music.playMusic();
	}

	public void init() {
		/**
		 * KEYBINDS Verwendete Knöpfe
		 */
		keybinds.put(KeyCode.UP, false);
		keybinds.put(KeyCode.W, false);
		keybinds.put(KeyCode.DOWN, false);
		keybinds.put(KeyCode.S, false);
		keybinds.put(KeyCode.RIGHT, false);
		keybinds.put(KeyCode.D, false);
		keybinds.put(KeyCode.LEFT, false);
		keybinds.put(KeyCode.A, false);
		keybinds.put(KeyCode.SPACE, false);
		keybinds.put(KeyCode.R, false);

		/**
		 * PRESSED aktualisiert, dass der Knopf gedrückt wird
		 */
		level.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keybinds.put(event.getCode(), true);

				switch (event.getCode()) {
//				case ESCAPE:
//					level.getScene().setRoot(levelSelectView);
//					levelSelectView.requestFocus();
//					stopMusic();
//					break;
				case W:
					jump();
					break;
				case SPACE:
					jump();
					break;
				case UP:
					jump();
					break;
				}
			}
		});

		/**
		 * UNPRESSED aktualisiert, dass der Knopf nicht mehr gedrückt wird
		 */
		level.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keybinds.put(event.getCode(), false);
			}
		});
		
		/**
		 * SET_LAYOUT Damit die "Kamera" dem Spieler folgt
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get() && !dieded.get()) {
				int playerPosX = newValue.intValue();
				if (playerPosX > Config.WINDOW_WIDTH / 3
						&& playerPosX < level.getLevelLength() - Config.WINDOW_WIDTH / 100 * 66) {
					playView.setLayoutX(-(playerPosX - Config.WINDOW_WIDTH / 3));
				}
			}
		});
		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get() && !dieded.get()) {
				int playerPosY = newValue.intValue();
				if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (Config.WINDOW_HEIGHT / 100 * 55)) {
					playView.setLayoutY(-(playerPosY - (Config.WINDOW_HEIGHT / 2)));
				}
			}
		});
		
		music.translateXProperty().bind(playView.layoutXProperty().multiply(-1));
		music.translateYProperty().bind(playView.layoutYProperty().multiply(-1));

		dieded.addListener(e -> {
			if (dieded.get() && !won.get()) {
				deathCount++;
			}
		});

		/**
		 * ANIMATION_TIMER Animation-Timer für flüssige Bewegung
		 */
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (!Config.getRhythmEnabled() && Config.getAutoJump()) {
					jump();
				}

				for (Node death : deathArea) {
					if (player.getBoundsInParent().intersects(death.getBoundsInParent())) {
						dieded.set(true);
					}
				}

				for (Node win : winArea) {
					if (player.getBoundsInParent().intersects(win.getBoundsInParent())) {
						won.set(true);
					}
				}

//				/**
//				 * JUMPING
//				 */
//				if (keybinds.get(KeyCode.SPACE) || keybinds.get(KeyCode.W) || keybinds.get(KeyCode.UP)) {
//					jump();
//				}

				/**
				 * RESPAWN
				 */
				if (keybinds.get(KeyCode.R)) {
					player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
					player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
					level.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (Config.BLOCK_SIZE * 2)));
					level.setLayoutY(
							-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));
				}

				/**
				 * MOVING X Seitliches Bewegen
				 */
				if ((keybinds.get(KeyCode.RIGHT) || keybinds.get(KeyCode.D))
				/*
				 * && player.getTranslateX() + Config.PLAYER_SIZE <= level.getLevelLength() - 5
				 */) {
					if (player.getTranslateX() + player.getLayoutBounds().getWidth() >= level.getLevelLength() - Config.BLOCK_SIZE - 1) {
						player.setTranslateX(Config.BLOCK_SIZE + 2);
					}
					movePlayerX(Config.getPlayerSpeed());
				}
				if ((keybinds.get(KeyCode.LEFT) || keybinds.get(KeyCode.A))/* && player.getTranslateX() >= 5 */) {
					if (player.getTranslateX() <= Config.BLOCK_SIZE + 1) {
						player.setTranslateX(level.getLevelLength() - Config.BLOCK_SIZE - 2 - player.getLayoutBounds().getWidth());
					}
					movePlayerX(-(Config.getPlayerSpeed()));
				}

				/**
				 * GRAVITY Schwerkraft
				 */
				if (player.getVelocity() < 5) {
					player.addVelocity(0.75);
				} else if (player.getVelocity() < Config.MAX_GRAVITY) {
					player.addVelocity(1);
				}
				movePlayerY((int) player.getVelocity());
			}
		};
		timer.start();
	}

	/**
	 * JUMP Springen Methode
	 */
	public void jump() {
		if (Config.getRhythmEnabled()) {
			if (player.getJumpable() && music.getOnBeat()) {
				player.setVelocity(-Config.getJumpHeight());
				player.setJumpable(false);
				jumpCount++;
			} else if (player.getJumpable()) {
				player.setVelocity(-Config.getJumpHeight() / 5);
				player.setJumpable(false);
				missedJumpCount++;
			}
		} else {
			if (player.getJumpable()) {
				player.setVelocity(-Config.getJumpHeight());
				player.setJumpable(false);
				jumpCount++;
			}
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
						if (player.getTranslateX() + player.getLayoutBounds().getWidth() == obstacle.getTranslateX()) {
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
						|| player.getTranslateY() + player.getLayoutBounds().getHeight() >= level.getLevelHeight() - 5) {
					if (movingDown) {
						if (player.getTranslateY() + (player.getLayoutBounds().getHeight()) == obstacle.getTranslateY()) {
							player.setTranslateY(player.getTranslateY() - 1);
							player.setVelocity(1.25); // "laggy" wenn unter 1.25 da wert nicht konstant bleibt
							if (!keybinds.get(KeyCode.SPACE) && !keybinds.get(KeyCode.W) && !keybinds.get(KeyCode.UP))
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
			if (counter >= Config.getCoyote())
				player.setJumpable(false);
			counter++;
		}
	}

}
