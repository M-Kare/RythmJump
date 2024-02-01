package presentation.settingsView;

import business.Config;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import presentation.homeView.HomeScreen;

/**
 * Contoller für den SettingsView
 */
public class SettingsViewController {
	private SettingsView root;
	private HomeScreen homeScreen;

	private TextField speed;
	private TextField jumpHeight;
	private TextField coyote;
	private TextField beatFrames;
	private CheckBox autoJump;
	private CheckBox rhythmEnabled;

	private Button saveButton;
	private Button cancelButton;
	private Button resetButton;

	/**
	 * Holt sich die TextBoxen vom SettingsView zum Verwalten
	 * 
	 * @param homeScreen
	 */
	public SettingsViewController(HomeScreen homeScreen) {
		root = new SettingsView();
		this.homeScreen = homeScreen;

		speed = root.speed;
		jumpHeight = root.jumpHeight;
		coyote = root.coyote;
		beatFrames = root.beatFrames;
		autoJump = root.autoJump;
		rhythmEnabled = root.rhythmEnabled;

		saveButton = root.saveButton;
		cancelButton = root.cancelButton;
		resetButton = root.resetButton;

		init();
	}

	/**
	 * Getter für den SettingsViews
	 * 
	 * @return SettingsViews
	 */
	public SettingsView getRoot() {
		return root;
	}

	/**
	 * Schränkt die TextBoxen auf Zahlen ein und fügt den Buttons Handler hinzu
	 */
	public void init() {
		/**
		 * REGERX - Filtert alle nicht-Zahlen aus der TextBox
		 */
		speed.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				speed.setText(newValue.replaceAll("[\\D+]", ""));
			}
		});
		jumpHeight.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				jumpHeight.setText(newValue.replaceAll("[\\D+]", ""));
			}
		});
		coyote.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				coyote.setText(newValue.replaceAll("[\\D+]", ""));
			}
		});
		beatFrames.textProperty().addListener((obs, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				beatFrames.setText(newValue.replaceAll("[\\D+]", ""));
			}
		});

		/**
		 * Speichert die eingegebenen Werte temporär in der Config
		 */
		saveButton.setOnMouseClicked(e -> {
			Config.setPlayerSpeed(Integer.parseInt(speed.getText()));
			Config.setJumpHeight(Integer.parseInt(jumpHeight.getText()));
			Config.setCoyote(Integer.parseInt(coyote.getText()));
			Config.setOnBeatFrames(Integer.parseInt(beatFrames.getText()));
			Config.setAutoJump(autoJump.isSelected());
			Config.setRhythmEnabled(rhythmEnabled.isSelected());

			homeScreen.getChildren().remove(root);
		});

		/**
		 * Setzt die TextBoxen auf die zuletzt verwendeten Werte zurück
		 */
		cancelButton.setOnMouseClicked(e -> {
			speed.setText(Integer.toString(Config.getPlayerSpeed()));
			jumpHeight.setText(Integer.toString(Config.getJumpHeight()));
			coyote.setText(Integer.toString(Config.getCoyote()));
			beatFrames.setText(Integer.toString(Config.getOnBeatFrames()));
			autoJump.setSelected(Config.getAutoJump());
			rhythmEnabled.setSelected(Config.getRhythmEnabled());

			homeScreen.getChildren().remove(root);
		});

		/**
		 * Setzt die TextBoxen auf die Default-Werte zurück
		 */
		resetButton.setOnMouseClicked(e -> {
			Config.setPlayerSpeed(Config.PLAYER_SPEED);
			Config.setJumpHeight(Config.JUMP_HEIGHT);
			Config.setCoyote(Config.COYOTE_TIME);
			Config.setOnBeatFrames(Config.ONBEAT_FRAMES);
			Config.setAutoJump(Config.AUTO_JUMP);
			Config.setRhythmEnabled(Config.RHYTHM_ENABLED);

			speed.setText(Integer.toString(Config.getPlayerSpeed()));
			jumpHeight.setText(Integer.toString(Config.getJumpHeight()));
			coyote.setText(Integer.toString(Config.getCoyote()));
			autoJump.setSelected(Config.getAutoJump());
			rhythmEnabled.setSelected(Config.getRhythmEnabled());
		});
	}
}
