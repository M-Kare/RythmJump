package presentation.settingsView;

import business.Config;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import presentation.homeView.HomeScreen;

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

	public SettingsView getRoot() {
		return root;
	}

	public void init() {
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

		saveButton.setOnMouseClicked(e -> {
			Config.setPlayerSpeed(Integer.parseInt(speed.getText()));
			Config.setJumpHeight(Integer.parseInt(jumpHeight.getText()));
			Config.setCoyote(Integer.parseInt(coyote.getText()));
			Config.setOnBeatFrames(Integer.parseInt(beatFrames.getText()));
			Config.setAutoJump(autoJump.isSelected());
			Config.setRhythmEnabled(rhythmEnabled.isSelected());
			
			homeScreen.getChildren().remove(root);
		});
		
		cancelButton.setOnMouseClicked(e -> {
			speed.setText(Integer.toString(Config.getPlayerSpeed()));
			jumpHeight.setText(Integer.toString(Config.getJumpHeight()));
			coyote.setText(Integer.toString(Config.getCoyote()));
			beatFrames.setText(Integer.toString(Config.getOnBeatFrames()));
			autoJump.setSelected(Config.getAutoJump());
			rhythmEnabled.setSelected(Config.getRhythmEnabled());
			
			homeScreen.getChildren().remove(root);
		});
		
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
