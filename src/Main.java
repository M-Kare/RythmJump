import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

	private Scene scene;
	private Stage primaryStage;
//	private Pane pane;
	private User user;
	private UserController userController;
//	private Rectangle wall;

	private Level level;

	private HashMap<KeyCode, Boolean> keybindings;
	private boolean moveUp, moveDown, moveLeft, moveRight;

	@Override
	public void start(Stage arg0) throws Exception {
		primaryStage = new Stage();

		userController = new UserController();
		user = userController.getRoot();

		level = new Level(findFile("testLevel.lvl", "C:\\Users\\mkare\\eclipse-workspace\\RythmJump"));

		level.getChildren().add(user);

		scene = new Scene(level, level.getWidth(), level.getHeight());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Wonder-Player 3000");

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
		keybindings.put(KeyCode.DOWN, false);
		keybindings.put(KeyCode.RIGHT, false);
		keybindings.put(KeyCode.LEFT, false);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					keybindings.replace(KeyCode.UP, true);
					break;
				case W:
					keybindings.replace(KeyCode.UP, true);
					break;
				case DOWN:
					keybindings.replace(KeyCode.DOWN, true);
					break;
				case S:
					keybindings.replace(KeyCode.DOWN, true);
					break;
				case RIGHT:
					keybindings.replace(KeyCode.RIGHT, true);
					break;
				case D:
					keybindings.replace(KeyCode.RIGHT, true);
					break;
				case LEFT:
					keybindings.replace(KeyCode.LEFT, true);
					break;
				case A:
					keybindings.replace(KeyCode.LEFT, true);
					break;
				default:
					break;
				}
			}
		});

		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case UP:
					keybindings.replace(KeyCode.UP, false);
					break;
				case W:
					keybindings.replace(KeyCode.UP, false);
					break;
				case DOWN:
					keybindings.replace(KeyCode.DOWN, false);
					break;
				case S:
					keybindings.replace(KeyCode.DOWN, false);
					break;
				case RIGHT:
					keybindings.replace(KeyCode.RIGHT, false);
					break;
				case D:
					keybindings.replace(KeyCode.RIGHT, false);
					break;
				case LEFT:
					keybindings.replace(KeyCode.LEFT, false);
					break;
				case A:
					keybindings.replace(KeyCode.LEFT, false);
					break;
				default:
					break;
				}

			}
		});

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (keybindings.get(KeyCode.UP))
					user.setY(user.getY() - 15);
				if (keybindings.get(KeyCode.DOWN))
					user.setY(user.getY() + 15);
				if (keybindings.get(KeyCode.RIGHT))
					user.setX(user.getX() + 15);
				if (keybindings.get(KeyCode.LEFT))
					user.setX(user.getX() - 15);
			}
		};
		timer.start();
	}

}
