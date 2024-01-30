package Level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Application.Config;
import Application.Dimensions;
import Player.Player;
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
import presentation.LevelSelectView.LevelSelectView;
import presentation.deathView.DeathViewController;
import presentation.endview.TheEnd;
import presentation.endview.TheEndController;
import presentation.homeView.HomeScreen;
import presentation.settingsView.SettingsViewController;

public class LevelController {
	private Level level;
	private LevelSelectView levelSelectView;
	private HomeScreen homeScreen;
	private SettingsViewController settingsController;

	private ArrayList<Node> winArea;
	private ArrayList<Node> obstacles;
	private ArrayList<Node> deathArea;

	private int jumpHeight;
	private int coyote;
	private int speed;

	private boolean autoJumpEnabled;
	private boolean rhythmEnabled;

	private Player player;

	private HashMap<KeyCode, Boolean> keybinds;

	private TheEndController theEndController;
	private TheEnd theEndScreen;

	private SimpleBooleanProperty won;
	private SimpleBooleanProperty dieded;

	private SimpleMinim minim;
	private AudioPlayer audioPlayer;
	private BeatDetect beat;
	private int frameCounter;
	private boolean onBeat;
	AnimationTimer detect;
	private Thread beatThread;

	private AnimationTimer timer;
	private long time;
	private boolean firstBeat;

	private HBox beatBorder;
	private final Border ON_BEAT_BORDER = new Border(new BorderStroke(Color.LIGHTGREEN, BorderStrokeStyle.SOLID,
			CornerRadii.EMPTY, new BorderWidths(23, 0, 0, 0)));
	private final Border OFF_BEAT_BORDER = new Border(
			new BorderStroke(Color.PINK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(20, 0, 0, 0)));

	public LevelController(File level, String songPath, LevelSelectView levelSelectView, HomeScreen homeScreen) {
		this.levelSelectView = levelSelectView;
		this.homeScreen = homeScreen;
		settingsController = homeScreen.getSettingsController();

		this.keybinds = new HashMap<>();
		this.level = new Level(level, songPath);

		winArea = new ArrayList<>(this.level.getWinArea());
		obstacles = new ArrayList<>(this.level.getObstacles());
		deathArea = new ArrayList<>(this.level.getDeathArea());

		speed = settingsController.getSpeedValue();
		coyote = settingsController.getCoyoteValue();
		jumpHeight = settingsController.getJumpHeightValue();
		autoJumpEnabled = settingsController.getAutoJump();
		rhythmEnabled = settingsController.getRhythmEnabled();

		beatBorder = this.level.beatBorder;
		if (rhythmEnabled) {
			beatBorder.setBorder(OFF_BEAT_BORDER);
		}

		this.player = new Player();

		theEndController = new TheEndController(levelSelectView, this, this.homeScreen);
		theEndScreen = theEndController.getRoot();

		won = new SimpleBooleanProperty(false);
		dieded = new SimpleBooleanProperty(false);

		onBeat = false;
		firstBeat = true;

		minim = new SimpleMinim(false);
		audioPlayer = minim.loadFile(songPath);
		audioPlayer.setGain(-20);
		beat = new BeatDetect();
		beat.setSensitivity(500);

		frameCounter = 0;

		initMusic();
		init();
	}

	public LevelController(Level level, LevelSelectView levelSelectView, HomeScreen homeScreen) {
		this(level.getFile(), level.getSong(), levelSelectView, homeScreen);
	}

	public Level getRoot() {
		return level;
	}

	public HashMap<KeyCode, Boolean> getKeybinds() {
		return keybinds;
	}

	public SimpleBooleanProperty getDieded() {
		return dieded;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void resetPlayer() {
		if (!level.getLevelRoot().getChildren().contains(player))
			level.getLevelRoot().getChildren().add(player);
//		level.getChildren().add(player);
		level.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (Config.WINDOW_HEIGHT / 100 * 75)));

		if (level.getPlayerSpawn()[Dimensions.X.getIndex()] > Config.WINDOW_WIDTH / 3) {
			level.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (Config.WINDOW_WIDTH / 100 * 13)));
		} else {
			level.setLayoutX(0);
		}
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
		dieded.set(false);
	}

	public void stopMusic() {
		if (audioPlayer == null)
			return;
		if (rhythmEnabled) {
			beatThread.interrupt();
			detect.stop();
		}
		audioPlayer.pause();
		timer.stop();

		if (minim == null)
			return;

		minim.stop();
	}

	public void playMusic() {
		if (audioPlayer == null)
			return;
		audioPlayer.play();
		if (rhythmEnabled) {
			detect.start();
		}
	}

	public void initMusic() {
		

		detect = new AnimationTimer() {
			@Override
			public void handle(long now) {
				time = now;
				
				beat.detect(audioPlayer.mix);
				if (beat.isOnset()) {
					System.out.println(now + " - Beat");
					if (firstBeat) {
						beatThread = new Thread() {
							public void run() {
								try {
									sleep(499);  //499 for 100bpm
								} catch (InterruptedException e) {
									this.interrupt();
								}
								while (!isInterrupted()) {
									System.out.println(time + " - Thread");
									onBeat = true;
									frameCounter = 0;
									beatBorder.setBorder(ON_BEAT_BORDER);
									if (autoJumpEnabled) {
										jump();
									}
									try {
										sleep(599); //599 for 100bpm
									} catch (InterruptedException e) {
										this.interrupt();
									}

								}
							}
						};
						beatThread.start();
						firstBeat = false;
					}
				}
				frameCounter++;

				if (frameCounter > 12) {
					beatBorder.setBorder(OFF_BEAT_BORDER);
					onBeat = false;
				}
				if(!audioPlayer.isPlaying()) {
					beatThread.interrupt();
					audioPlayer.play(0);
					firstBeat = true;
					
				}
			}
		};
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
				case ESCAPE:
					level.getScene().setRoot(levelSelectView);
					levelSelectView.requestFocus();
					stopMusic();
					break;
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

		beatBorder.translateXProperty().bind(level.layoutXProperty().multiply(-1));
		beatBorder.translateYProperty().bind(level.layoutYProperty().multiply(-1));

		/**
		 * SET_LAYOUT Damit die "Kamera" dem Spieler folgt
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get()) {
				int playerPosX = newValue.intValue();
				if (playerPosX > Config.WINDOW_WIDTH / 3
						&& playerPosX < level.getLevelLength() - Config.WINDOW_WIDTH / 100 * 66) {
					level.setLayoutX(-(playerPosX - Config.WINDOW_WIDTH / 3));
				}
			}
		});
		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get()) {
				int playerPosY = newValue.intValue();
				if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (Config.WINDOW_HEIGHT / 100 * 55)) {
					level.setLayoutY(-(playerPosY - (Config.WINDOW_HEIGHT / 2)));
				}
			}
		});

		won.addListener(e -> {
			if (won.get()) {
				Scene scene = level.getScene();
				theEndScreen.setJumps(level.getJumpCount());
				theEndScreen.setDeaths(level.getDeathCount());
				theEndScreen.setMissedJumps(level.getMissedJumpCount());
				theEndScreen.setBeats(level.getBeatCount());

				StackPane stack = new StackPane(level, theEndScreen);
				scene.setRoot(stack);
				theEndScreen.requestFocus();
			}
		});

		dieded.addListener(e -> {
			if (dieded.get() && !won.get()) {
				DeathViewController deathViewController = new DeathViewController(this, levelSelectView);
				level.getScene().setRoot(deathViewController.getRoot());
				deathViewController.getRoot().requestFocus();
				level.addDeathCount(1);
			}
		});

		/**
		 * ANIMATION_TIMER Animation-Timer für flüssige Bewegung
		 */
		timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (!rhythmEnabled && autoJumpEnabled) {
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
					if (player.getTranslateX() + player.getWidth() >= level.getLevelLength() - Config.BLOCK_SIZE - 1) {
						player.setTranslateX(Config.BLOCK_SIZE + 2);
					}
					movePlayerX(speed);
				}
				if ((keybinds.get(KeyCode.LEFT) || keybinds.get(KeyCode.A))/* && player.getTranslateX() >= 5 */) {
					if (player.getTranslateX() <= Config.BLOCK_SIZE + 1) {
						player.setTranslateX(level.getLevelLength() - Config.BLOCK_SIZE - 2 - player.getWidth());
					}
					movePlayerX(-(speed));
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
		if (rhythmEnabled) {
			if (player.getJumpable() && onBeat) {
				player.setVelocity(-jumpHeight);
				player.setJumpable(false);
				level.addJumpCount(1);
			} else if (player.getJumpable()) {
				player.setVelocity(-jumpHeight / 5);
				player.setJumpable(false);
				level.addMissedJumpCount(1);
			}
		} else {
			if (player.getJumpable()) {
				player.setVelocity(-jumpHeight);
				player.setJumpable(false);
				level.addJumpCount(1);
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
						|| player.getTranslateY() + player.getHeight() >= level.getLevelHeight() - 5) {
					if (movingDown) {
						if (player.getTranslateY() + (player.getHeight()) == obstacle.getTranslateY()) {
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
			if (counter >= coyote)
				player.setJumpable(false);
			counter++;
		}
	}

}
