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
import javafx.stage.Stage;
import presentation.deathView.DeathViewController;
import presentation.endview.TheEnd;
import presentation.endview.TheEndController;
import presentation.homeView.HomeScreen;
import presentation.levelSelectView.LevelSelectView;
import presentation.playView.PlayView;
import presentation.settingsView.SettingsViewController;

/**
 * Controller für die Level - Setzt die Logik für die Bewegung des Spielers um
 */
public class LevelController {
	private Stage stage;
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
	private SimpleBooleanProperty paused;

	/**
	 * Erzeugt Level basierend auf der Level-Datei und dem SongPath. PlayView wird
	 * benötigt, damit die "Kamera" sich mit bewegen kann
	 * 
	 * @param level    Level-Datei
	 * @param songPath Song-Pfad
	 * @param playView Root des Level
	 */
	public LevelController(File level, String songPath, PlayView playView, Stage stage) {
		this.stage = stage;
		this.playView = playView;

		this.keybinds = new HashMap<>();
		paused = new SimpleBooleanProperty(false);
		this.level = new Level(level, songPath);

		winArea = new ArrayList<>(this.level.getWinArea());
		obstacles = new ArrayList<>(this.level.getObstacles());
		deathArea = new ArrayList<>(this.level.getDeathArea());

		music = new BeatControlls(songPath, this, stage);
		if (Config.getRhythmEnabled()) {
			this.level.getChildren().add(music);
		}

		this.player = new Player();

		won = new SimpleBooleanProperty(false);
		dieded = new SimpleBooleanProperty(false);

		init();
	}

	/**
	 * Alternativer Konstruktor, bei dem ein fertiges Level mitgegeben wird. Es wird
	 * ein neues Level basierend auf der Datei und des hinterlegten Songs des Levels
	 * erzeugt
	 * 
	 * @param level    Fertiges Level
	 * @param playView Root des Levels
	 */
	public LevelController(Level level, PlayView playView, Stage stage) {
		this(level.getFile(), level.getSong(), playView, stage);
	}

	/**
	 * Getter für Level
	 * 
	 * @return Level
	 */
	public Level getRoot() {
		return level;
	}

	/**
	 * Getter für die Anzahl der gemachten Sprünge
	 * 
	 * @return Sprung-Anzahl
	 */
	public int getJumpCount() {
		return jumpCount;
	}

	/**
	 * Getter für die vergangenen Beats
	 * 
	 * @return Beat-Anzahl
	 */
	public int getBeatCount() {
		return music.getBeatCount();
	}

	/**
	 * Getter für die getätigten off-beat Sprünge
	 * 
	 * @return verfehlt Sprünge-Anzahl
	 */
	public int getMissedJumpCount() {
		return missedJumpCount;
	}

	/**
	 * Getter für die Anzahl der Tode im Level-Durchlauf
	 * 
	 * @return Tode-Anzahl
	 */
	public int getDeathCount() {
		return deathCount;
	}

	/**
	 * Getter für die HashMap mit den Zuständen der gedrückten Knöpfe
	 * 
	 * @return Tastatur-Inputs
	 */
	public HashMap<KeyCode, Boolean> getKeybinds() {
		return keybinds;
	}

	/**
	 * Getter für den Paused-Property
	 * 
	 * @return Paused-Property
	 */
	public SimpleBooleanProperty getPaused() {
		return paused;
	}

	/**
	 * Setter für den Paused-Property
	 * 
	 * @param value neuer Zustand
	 */
	public void setPaused(boolean value) {
		paused.set(value);
	}

	/**
	 * Getter für das Gestorben-Property
	 * 
	 * @return Gestorben-Property
	 */
	public SimpleBooleanProperty getDieded() {
		return dieded;
	}

	/**
	 * Getter für das Gewonnen-Property
	 * 
	 * @return Gewonnen-Property
	 */
	public SimpleBooleanProperty getWon() {
		return won;
	}

	/**
	 * Getter für die Spielfigur im Level
	 * 
	 * @return Spielfigur
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter für die Spielfigur im Level
	 * 
	 * @param player Spielfigur
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Setzt den Spieler und das Level-Layout auf die Spawn-Position des Spielers
	 */
	public void resetPlayer() {
		if (!level.getChildren().contains(player))
			level.getChildren().add(player);
		
		if(level.getPlayerSpawn()[Dimensions.Y.getIndex()] < level.getLevelHeight() - (stage.getHeight() / 100 * 50)) {
			playView.setLayoutY(-(level.getPlayerSpawn()[Dimensions.Y.getIndex()] - (stage.getHeight() / 100 * 50)));
		} else {
			playView.setLayoutY(-(level.getLevelHeight()-stage.getHeight() + Config.PLAYER_SIZE));				
		}

		if (level.getPlayerSpawn()[Dimensions.X.getIndex()] > stage.getWidth() / 3) {
			playView.setLayoutX(-(level.getPlayerSpawn()[Dimensions.X.getIndex()] - (stage.getWidth() / 100 * 13)));
		} else {
			playView.setLayoutX(0);
		}
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
		dieded.set(false);
	}

	/**
	 * Setzt die Tastatur-HashMap wieder auf false
	 */
	public void resetKeys() {
		keybinds.replaceAll((k, v) -> false);
	}

	/**
	 * Stopt die Musik und den Animation-Timer
	 */
	public void stopMusic() {
		music.stopMusic();
		timer.stop();
	}

	/**
	 * Startet die Musik
	 */
	public void playMusic() {
		music.playMusic();
	}

	/**
	 * Fügt nötige Listener und Handler hinzu und kümmert sich mithilfe des
	 * Animationtimers um die Eingaben des Users
	 */
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
					paused.set(true);
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
				case R:
					resetPlayer();
					deathCount++;
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
		 * SET_LAYOUT-X Damit die "Kamera" dem Spieler folgt
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get() && !dieded.get()) {
				double playerPosX = newValue.doubleValue();
				if (playerPosX > stage.getWidth() / 3
						&& playerPosX < level.getLevelLength() - stage.getWidth() / 100 * 66) {
					playView.setLayoutX(-(playerPosX - stage.getWidth() / 3));
				}
			}
		});
		stage.widthProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get() && !dieded.get()) {
				double playerPosX = player.getTranslateX();
				if (playerPosX > stage.getWidth() / 3
						&& playerPosX < level.getLevelLength() - newValue.doubleValue() / 100 * 66) {
					playView.setLayoutX(-(playerPosX - newValue.doubleValue() / 3));
				}
			}
		});

		/**
		 * SET_LAYOUT-Y Damit die "Kamera" dem Spieler folgt
		 */
		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get() && !dieded.get()) {
				double playerPosY = newValue.doubleValue();
				if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (stage.getHeight() / 100 * 50) + Config.PLAYER_SIZE) {
					playView.setLayoutY(-(playerPosY - (stage.getHeight() / 2)));
				}
			}
		});
		stage.heightProperty().addListener((obs, oldValue, newValue) -> {
			if (!won.get() && !dieded.get()) {
				double playerPosY = player.getTranslateY();
				if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (newValue.doubleValue() / 100 * 50) + Config.PLAYER_SIZE) {
					playView.setLayoutY(-(playerPosY - (newValue.doubleValue() / 2)));
				}
			}
		});

		/**
		 * BEAT_BORDER Bindet den Beat-Rahmen an das Layout, damit es sich mit bewegt
		 */
		music.translateXProperty().bind(playView.layoutXProperty().multiply(-1));
		music.translateYProperty().bind(playView.layoutYProperty().multiply(-1));

		/**
		 * DIEDED Zählt den Todes-Counter hoch wenn der Spieler beim spielen stirbt
		 */
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
				/**
				 * AUTOJUMP ohne Rhythmus
				 */
				if (!Config.getRhythmEnabled() && Config.getAutoJump()) {
					jump();
				}

				/**
				 * DEATH Kollision mit Tod
				 */
				for (Node death : deathArea) {
					if (player.getBoundsInParent().intersects(death.getBoundsInParent())) {
						dieded.set(true);
					}
				}

				/**
				 * WIN Kollision mit Sieg
				 */
				for (Node win : winArea) {
					if (player.getBoundsInParent().intersects(win.getBoundsInParent())) {
						won.set(true);
					}
				}

//				/**
//				 * JUMPING -> nicht mehr verwendet, damit Sprungtaste nicht gehalten werden kann
//				 */
//				if (keybinds.get(KeyCode.SPACE) || keybinds.get(KeyCode.W) || keybinds.get(KeyCode.UP)) {
//					jump();
//				}

				/**
				 * MOVING X Seitliches Bewegen
				 */
				if ((keybinds.get(KeyCode.RIGHT) || keybinds.get(KeyCode.D))) {
					if (player.getTranslateX() + player.getLayoutBounds().getWidth() >= level.getLevelLength()
							- Config.BLOCK_SIZE - 1 && level.getAllowTeleport()) {
						player.setTranslateX(Config.BLOCK_SIZE + 2);
					}
					movePlayerX(Config.getPlayerSpeed());
				}
				if ((keybinds.get(KeyCode.LEFT) || keybinds.get(KeyCode.A))) {
					if (player.getTranslateX() <= Config.BLOCK_SIZE + 1 && level.getAllowTeleport()) {
						player.setTranslateX(
								level.getLevelLength() - Config.BLOCK_SIZE - 2 - player.getLayoutBounds().getWidth());
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
	 * JUMP Springen Methode -> Wenn Rhythmus aktiv: onBeat = normaler Sprung;
	 * offBeat = 1/5 Sprung; -> Wenn Rhythmus deaktiviert: normaler Sprung
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
				if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent()) || player.getTranslateY()
						+ player.getLayoutBounds().getHeight() >= level.getLevelHeight() - 5) {
					if (movingDown) {
						if (player.getTranslateY() + (player.getLayoutBounds().getHeight()) == obstacle
								.getTranslateY()) {
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
