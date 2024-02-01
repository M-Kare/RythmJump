package presentation.settingsView;

import business.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class SettingsView extends GridPane {

	private Label speedText;
	protected TextField speed;

	private Label jumpHeightText;
	protected TextField jumpHeight;

	private Label coyoteText;
	protected TextField coyote;

	private Label beatFramesText;
	protected TextField beatFrames;

	protected CheckBox autoJump;
	protected CheckBox rhythmEnabled;

	protected Button cancelButton;
	protected Button saveButton;
	protected Button resetButton;
	private HBox buttonsBox;

	public SettingsView() {
		speedText = new Label("Player Speed:");
		this.add(speedText, 0, 1);
		speed = new TextField(Integer.toString(Config.PLAYER_SPEED));
		this.add(speed, 1, 1);

		jumpHeightText = new Label("Jump Height:");
		this.add(jumpHeightText, 0, 2);
		jumpHeight = new TextField(Integer.toString(Config.JUMP_HEIGHT));
		this.add(jumpHeight, 1, 2);

		coyoteText = new Label("Coyote-Time:");
		this.add(coyoteText, 0, 3);
		coyote = new TextField(Integer.toString(Config.COYOTE_TIME));
		this.add(coyote, 1, 3);
		
		beatFramesText = new Label("OnBeat-Frames:");
		this.add(beatFramesText, 0, 4);
		beatFrames = new TextField(Integer.toString(Config.ONBEAT_FRAMES));
		this.add(beatFrames, 1, 4);

		rhythmEnabled = new CheckBox("Rhythm Enabled:");
		rhythmEnabled.setSelected(Config.RHYTHM_ENABLED);
		this.add(rhythmEnabled, 1, 5);

		autoJump = new CheckBox("Auto-jumping:");
		autoJump.setSelected(Config.AUTO_JUMP);
		this.add(autoJump, 1, 6);

		saveButton = new Button("Save");
		cancelButton = new Button("Cancel");
		resetButton = new Button("Default");
		saveButton.setMinWidth(60);
		buttonsBox = new HBox(saveButton, resetButton, cancelButton);
		this.add(buttonsBox, 1, 9);

		this.setVgap(5);
		this.setHgap(5);
		this.setAlignment(Pos.CENTER);
		this.setBackground(new Background(new BackgroundFill(new Color(0.1, 0.1, 0.1, 0.8), null, null)));
	}

}
