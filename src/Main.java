import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
//	private Pane pane;
	private Player player;
	private PlayerController userController;
//	private Rectangle wall;

	private Level level;
	
	private HashMap<KeyCode, Boolean> keybindings;

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = new Stage();

		userController = new PlayerController();
		player = userController.getRoot();
		
		level = new Level(findFile("doodleJump.lvl", "."));

		level.getChildren().add(player);
		player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
		player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);

		scene = new Scene(level, 1600, 800);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Wonder-Player 3000");
		primaryStage.setResizable(false);;

		level.setLayoutY(-level.getLevelHeight() + scene.getHeight() + Config.PLAYER_SIZE*2);
		primaryStage.show();

		init(scene);
	}

	public static void main(String[] args) {
		launch(args);
	}

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
		keybindings = new HashMap<>();
		keybindings.put(KeyCode.UP, false);
		keybindings.put(KeyCode.W, false);
		keybindings.put(KeyCode.DOWN, false);
		keybindings.put(KeyCode.S, false);
		keybindings.put(KeyCode.RIGHT, false);
		keybindings.put(KeyCode.D, false);
		keybindings.put(KeyCode.LEFT, false);
		keybindings.put(KeyCode.A, false);
		keybindings.put(KeyCode.SPACE, false);
		keybindings.put(KeyCode.R, false);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				keybindings.put(event.getCode(), true);
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				keybindings.put(event.getCode(), false);
			}
		});

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
//				if (keybindings.get(KeyCode.UP) || keybindings.get(KeyCode.W))
//					player.setY(player.getY() - Config.PLAYER_SPEED);
//				if (keybindings.get(KeyCode.DOWN) || keybindings.get(KeyCode.S))
//					player.setY(player.getY() + Config.PLAYER_SPEED);
				if (keybindings.get(KeyCode.SPACE) || keybindings.get(KeyCode.W) || keybindings.get(KeyCode.UP)) {
					jump();
				}
				
				if (keybindings.get(KeyCode.R)) {
					player.setTranslateX(level.getPlayerSpawn()[Dimensions.X.getIndex()]);
					player.setTranslateY(level.getPlayerSpawn()[Dimensions.Y.getIndex()]);
					level.setLayoutX(-(0));
				}

				if ((keybindings.get(KeyCode.RIGHT) || keybindings.get(KeyCode.D))
						&& player.getTranslateX() + Config.PLAYER_SIZE <= level.getLevelLength() - 5) {
					movePlayerX(Config.PLAYER_SPEED);
				}

				if ((keybindings.get(KeyCode.LEFT) || keybindings.get(KeyCode.A)) && player.getTranslateX() >= 5) {
					movePlayerX(-(Config.PLAYER_SPEED));
				}

				if(player.getVelocity() < 5) {
					player.addVelocity(0.75);
				} else
				if (player.getVelocity() < 15) {
					player.addVelocity(1);
				}

				if (player.getTranslateY() + (Config.PLAYER_SIZE * 2) <= level.getLevelHeight() - 5) {
					movePlayerY((int)player.getVelocity());
				}
			}
		};
		timer.start();

		player.translateXProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosX = newValue.intValue();
			if (playerPosX > scene.getWidth()/3 && playerPosX < level.getLevelLength() - scene.getWidth()/3*2) {
				level.setLayoutX(-(playerPosX - scene.getWidth()/3));
			}
		});

		player.translateYProperty().addListener((obs, oldValue, newValue) -> {
			int playerPosY = newValue.intValue();
			System.out.println(player.getVelocity());
			if (playerPosY > 0 && playerPosY < level.getLevelHeight() - (scene.getHeight() / 5 * 3)) {
				level.setLayoutY(-(playerPosY - (scene.getHeight()/2)));
			}
		});
	}

	public void jump() {
		if (player.getJumpable()) {
			player.setVelocity(- Config.JUMP_HEIGHT);
			player.setJumpable(false);
		}
	}

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

	public void movePlayerY(int value) {
		boolean movingDown = value > 0;
		 
		for (int i = 0; i < Math.abs(value); i++) {
			for (Node obstacle : level.getObstacles()) {
				if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())
						|| player.getTranslateY() + (Config.PLAYER_SIZE * 2) >= level.getLevelHeight() - 5) {
					if (movingDown) {
						if (player.getTranslateY() + (Config.PLAYER_SIZE * 2) == obstacle.getTranslateY()
								) {
							player.setTranslateY(player.getTranslateY() - 1);
							player.setVelocity(0);
							player.setJumpable(true);
							return;
						}
					} else {
						if (player.getTranslateY() == obstacle.getTranslateY() + Config.BLOCK_SIZE) {
							player.setTranslateY(player.getTranslateY() + 1);
							player.addVelocity(1);
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
