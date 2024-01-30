package presentation.settingsView;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import presentation.homeView.HomeScreen;

public class SettingsViewController {
	private SettingsView root;
	private HomeScreen homeScreen;

	private TextField speed;
	private int speedValue;

	private TextField jumpHeight;
	private int jumpHeightValue;

	private TextField coyote;
	private int coyoteValue;
	
	private CheckBox autoJump;
	private boolean autoJumpValue;
	
	private CheckBox rhythmEnabled;
	private boolean rhythmEnabledValue;

	private Button saveButton;
	private Button cancelButton;

	public SettingsViewController(HomeScreen homeScreen) {
		root = new SettingsView();
		this.homeScreen = homeScreen;
		
		speed = root.speed;
		speedValue = Integer.parseInt(speed.getText());

		jumpHeight = root.jumpHeight;
		jumpHeightValue = Integer.parseInt(jumpHeight.getText());

		coyote = root.coyote;
		coyoteValue = Integer.parseInt(coyote.getText());

		autoJump = root.autoJump;
		autoJumpValue = autoJump.isSelected();
		
		rhythmEnabled = root.rhythmEnabled;
		rhythmEnabledValue = rhythmEnabled.isSelected();
		
		saveButton = root.saveButton;
		cancelButton = root.cancelButton;

		init();
	}

	public SettingsView getRoot() {
		return root;
	}

	public int getCoyoteValue() {
		return coyoteValue;
	}

	public int getJumpHeightValue() {
		return jumpHeightValue;
	}

	public int getSpeedValue() {
		return speedValue;
	}
	
	public boolean getAutoJump() {
		return autoJumpValue;
	}
	
	public boolean getRhythmEnabled() {
		return rhythmEnabledValue;
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

		saveButton.setOnMouseClicked(e -> {
			speedValue = Integer.parseInt(speed.getText());
			jumpHeightValue = Integer.parseInt(jumpHeight.getText());
			coyoteValue = Integer.parseInt(coyote.getText());
			autoJumpValue = autoJump.isSelected();
			rhythmEnabledValue = rhythmEnabled.isSelected();
			
			homeScreen.getChildren().remove(root);
		});
		
		cancelButton.setOnMouseClicked(e -> {
			speed.setText(Integer.toString(speedValue));
			jumpHeight.setText(Integer.toString(jumpHeightValue));
			coyote.setText(Integer.toString(coyoteValue));
			autoJump.setSelected(autoJumpValue);
			rhythmEnabled.setSelected(rhythmEnabledValue);
			
			homeScreen.getChildren().remove(root);
		});
	}
}
