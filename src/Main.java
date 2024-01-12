import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

	private Scene scene;
	private Stage primaryStage;
	private Player player;

	private Level level;
	private LevelGenerator levelGen;

	private HashMap<KeyCode, Boolean> keybinds;

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = new Stage();
		player = new Player();
//		level = new Level(findFile("doodleJump.lvl", "."));
		
		levelGen = new LevelGenerator(800, 18);
		level = new Level(levelGen.getLevelArray());
		
		/**
		 * SCENE + LEVEL
		 * Spieler im Level setzten
		 */
		scene = new Scene(level, 1600, 800);
		level.getChildren().addAll(player, player.getHitbox());
		level.setLayoutY(-level.getLevelHeight() + scene.getHeight() + Config.PLAYER_SIZE * 2);
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);

		/**
		 * STAGE
		 * Stage wird gesetzt
		 */
		primaryStage.setScene(scene);
		primaryStage.setTitle("Wonder-Player 3000");
		primaryStage.setResizable(false);
		primaryStage.show();


		init(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Zum Suchen einer Datei im angegebenen Pfad
	 * 
	 * @param fileName        Name der zu suchenden Datei
	 * @param searchDirectory Pfad des Verzeichnisses, in dem gesucht werden soll
	 * @return gefundene Datei
	 * @throws IOException
	 */
	public File findFile(String fileName, String searchDirectory) throws IOException {
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
					File temp = findFile(fileName, file.getCanonicalPath());
					if (temp != null && temp.getName().equals(fileName)) {
						return temp;
					}
				}
			}
		}

		return null;
	}

	public void init(Scene scene) {
		/**
		 * KEYBINDS
		 * Verwendete Knöpfe
		 */
		keybinds = new HashMap<>();
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
		 * PRESSED
		 * aktualisiert, dass der Knopf gedrückt wird
		 */
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keybinds.put(event.getCode(), true);
			}
		});

		/**
		 * UNPRESSED
		 * aktualisiert, dass der Knopf nicht mehr gedrückt wird
		 */
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keybinds.put(event.getCode(), false);
			}
		});

		/**
		 * ANIMATION_TIMER
		 * Animation-Timer für flüssige Bewegung
		 */
		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				/**
				 * JUMPING
				 */
				if (keybinds.get(KeyCode.SPACE) || keybinds.get(KeyCode.W) || keybinds.get(KeyCode.UP)) {
					jump();
				}

				/**
				 * RESPAWN
				 */
				if (keybinds.get(KeyCode.R)) {
					player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
					player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
					level.setLayoutX(-(0));
				}

				/**
				 * MOVING X
				 * Seitliches Bewegen
				 */
				if ((keybinds.get(KeyCode.RIGHT) || keybinds.get(KeyCode.D))
						/*&& player.getTranslateX() + Config.PLAYER_SIZE <= level.getLevelLength() - 5*/) {
					if(player.getTranslateX() + Config.PLAYER_SIZE >= level.getLevelLength() - Config.BLOCK_SIZE - 1) {
						player.setTranslateX(Config.BLOCK_SIZE + 2);
					}
					movePlayerX(Config.PLAYER_SPEED);
				}
				if ((keybinds.get(KeyCode.LEFT) || keybinds.get(KeyCode.A))/* && player.getTranslateX() >= 5*/) {
					if(player.getTranslateX() <= Config.BLOCK_SIZE+1) {
						player.setTranslateX(level.getLevelLength() - Config.BLOCK_SIZE - 2 - Config.PLAYER_SIZE);
					}
					movePlayerX(-(Config.PLAYER_SPEED));
				}

				/**
				 * GRAVITY
				 * Schwerkraft
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

		/**
		 * SET_LAYOUT
		 * Damit die "Kamera" dem Spieler folgt
		 */
		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosX = newValue.intValue();
			if (playerPosX > scene.getWidth() / 3 && playerPosX < level.getLevelLength() - scene.getWidth() / 3 * 2) {
				level.setLayoutX(-(playerPosX - scene.getWidth() / 3));
			}
		});
		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosY = newValue.intValue();
			System.out.println(player.getVelocity());
			if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (scene.getHeight() / 5 * 3)) {
				level.setLayoutY(-(playerPosY - (scene.getHeight() / 2)));
			}
		});
	}

	/**
	 * JUMP
	 * Springen Methode
	 */
	public void jump() {
		if (player.getJumpable()) {
			player.setVelocity(-Config.JUMP_HEIGHT);
			player.setJumpable(false);
		}
	}

	/**
	 * MOVE_X
	 * Bewegen in X-Richtung. Wenn 
	 * @param value
	 */
	public void movePlayerX(int value) {
		boolean movingRight = value > 0;
		
		for (int i = 0; i < Math.abs(value); i++) {
			for (Node obstacle : level.getObstacles()) {
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
	 * MOVE_Y
	 * @param value
	 */
	public void movePlayerY(int value) {
		boolean movingDown = value > 0;

		for (int i = 0; i < Math.abs(value); i++) {
			for (Node obstacle : level.getObstacles()) {
				if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())
						|| player.getTranslateY() + (Config.PLAYER_SIZE * 2) >= level.getLevelHeight() - 5) {
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
